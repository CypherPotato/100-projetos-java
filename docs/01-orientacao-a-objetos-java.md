# Orientação a objetos em Java 17+ — guia rápido

Este guia cobre a parte de **linguagem** que aparece nos projetos CRUD (Java 17+ + Spring Boot 3.5 + SQL Server): entidades com campos privados, interfaces de repositório, controllers e Lombok. Spring não é explicado aqui; o foco é o Java.

Os exemplos usam `Cliente`, `Pedido` e `Produto`. Trechos soltos (sem `class`) supõem que estão dentro de um método `main` e omitem os `import` de `java.util.*`, `java.math.*` e `java.time.*`. Os que usam `jakarta.persistence`, Lombok ou Spring Data dependem das bibliotecas que os projetos já trazem.

## 1. Tipos

### Primitivos vs. wrappers

Todo primitivo tem um **wrapper**, uma classe que "embrulha" o valor:

| Primitivo | Wrapper   | Padrão em campo (primitivo) | Aceita `null`? |
|-----------|-----------|-----------------------------|----------------|
| `int`     | `Integer` | `0`     | só o wrapper |
| `long`    | `Long`    | `0L`    | só o wrapper |
| `boolean` | `Boolean` | `false` | só o wrapper |

Entidades usam `Long`, `Integer` e `Boolean` porque:

- **`null` significa "ainda não tem valor".** O `id` de um `Cliente` novo é `null` até o banco gerar a chave. Com `long id` ele valeria `0`, e você não distinguiria "não salvo" de "id zero".
- **Colunas aceitam `NULL`.** Um `Integer` lê `NULL` sem problema; um `int` falharia.
- **Generics só aceitam wrappers.** `List<Integer>` é válido; `List<int>` não compila.

O custo é o **unboxing**: converter um wrapper `null` em primitivo lança `NullPointerException`. Compare wrappers com `equals`, nunca com `==`.

```java
Integer estoque = null;
// int total = estoque;           // NullPointerException

Integer a = 1000, b = 1000;
System.out.println(a == b);       // false: objetos distintos
System.out.println(a.equals(b));  // true
```

### String

`String` é **imutável**: `nome.toUpperCase()` devolve uma nova `String` e não altera a original. Compare conteúdo sempre com `equals`.

```java
String nome = "Ana";
String maiusculo = nome.toUpperCase();
boolean igual = "Ana".equals(nome);
String frase = "Olá, %s!".formatted(nome);
```

### BigDecimal para dinheiro

`double` guarda frações binárias aproximadas (`0.1 + 0.2` dá `0.30000000000000004`). Para dinheiro use `BigDecimal`, criado **a partir de `String`**. Ele é imutável: `add`, `subtract` e `multiply` devolvem um novo objeto.

```java
BigDecimal preco = new BigDecimal("19.90");
BigDecimal total = preco.multiply(BigDecimal.valueOf(3)).setScale(2, RoundingMode.HALF_UP);

System.out.println(total);                                                    // 59.70
System.out.println(new BigDecimal("2.0").equals(new BigDecimal("2.00")));     // false (escala)
System.out.println(new BigDecimal("2.0").compareTo(new BigDecimal("2.00")));  // 0 (mesmo valor)
```

### LocalDate e LocalDateTime

Do pacote `java.time`, imutáveis. Não use `java.util.Date` em código novo. No SQL Server, `LocalDate` costuma virar `date` e `LocalDateTime` vira `datetime2`.

```java
LocalDate vencimento = LocalDate.now().plusDays(30);
LocalDateTime criadoEm = LocalDateTime.now();
```

### var

`var` deixa o compilador **inferir** o tipo de uma variável local. O tipo continua fixo e checado em compilação; não é tipagem dinâmica. Só vale para variáveis locais, nunca para campos ou parâmetros. Use quando o tipo é óbvio pela direita; evite em `var x = servico.processar();`.

```java
var nomes = new ArrayList<String>();   // ArrayList<String>
```

### null e Optional em 5 linhas

```java
String apelido = null;
Optional<String> talvez = Optional.ofNullable(apelido);
String exibido = talvez.orElse("sem apelido");
talvez.ifPresent(a -> System.out.println(a));
Optional<String> vazio = Optional.empty();
```

`null` quer dizer "nenhum objeto"; chamar um método nele lança `NullPointerException`. `Optional<T>` é uma caixa que pode estar vazia e obriga quem recebe a tratar a ausência. Use-o como **retorno** de métodos (como `findById`), não como campo de entidade nem como parâmetro. A seção 8 detalha os métodos do `Optional`.

## 2. Classes e objetos

Uma **classe** é o molde: define campos (dados) e métodos (comportamento). Um **objeto** é uma instância criada com `new`.

```java
public class Cliente {
    private Long id;
    private String nome;
    private String email;

    public Cliente() {
    }

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        this.nome = nome;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + "'}";
    }
}
```

```java
Cliente ana = new Cliente("Ana", "ana@exemplo.com");
ana.setId(1L);
System.out.println(ana);   // Cliente{id=1, nome='Ana'}
```

- **Campos** (`id`, `nome`, `email`) guardam o estado de cada objeto.
- **Construtor** tem o nome da classe e nenhum tipo de retorno; roda no `new`. Se você não declara nenhum, o Java cria um sem argumentos; **ao declarar qualquer construtor, o padrão deixa de existir**. Por isso a classe declara `Cliente()` explicitamente.
- **`this`** é a referência ao próprio objeto. Em `this.nome = nome`, o lado esquerdo é o campo e o direito é o parâmetro.
- **`new`** aloca o objeto e chama o construtor.

### Getters e setters: encapsulamento

Os campos são `private`: só o código da própria classe os toca. Quem está fora passa por `getX()` e `setX()`. Parece burocracia, mas dá controle: o `setNome` acima **valida** antes de aceitar o valor, e você pode trocar a representação interna sem quebrar quem usa a classe. Isso é **encapsulamento**. JPA e Jackson (JSON) também usam esses métodos para ler e preencher os objetos.

### toString, equals e hashCode

`toString()` devolve a representação textual do objeto, usada por `println` e logs; sem sobrescrever, você vê algo como `Cliente@1b2c3d`. `==` compara **referências** (se é o mesmo objeto); `equals` compara **conteúdo**, mas só se você sobrescrevê-lo. Quem sobrescreve `equals` deve sobrescrever `hashCode` com os mesmos campos, porque `HashMap` e `HashSet` usam os dois: objetos iguais precisam ter o mesmo hash. Em entidades, o critério costuma ser o `id` ou um identificador de negócio, e o `toString` não deve incluir relacionamentos que apontem de volta (recursão).

```java
@Override
public boolean equals(Object o) {
    return o instanceof Cliente outro && Objects.equals(id, outro.id);
}

@Override
public int hashCode() {
    return Objects.hash(id);
}
```

## 3. Pacotes e modificadores de acesso

Um **pacote** agrupa classes relacionadas e evita conflito de nomes. A convenção é o domínio invertido em minúsculas (`br.com.exemplo.crud`), e a estrutura de pastas espelha o nome do pacote.

- `package` na primeira linha do arquivo declara a que pacote a classe pertence.
- `import` traz classes de outros pacotes para usar o nome curto. Classes de `java.lang` (`String`, `Integer`, `Math`) são importadas automaticamente.
- `import static` traz membros estáticos, como `import static java.lang.Math.max;`.

```java
package br.com.exemplo.crud.cliente;

import java.util.List;
```

| Modificador            | Mesma classe | Mesmo pacote | Subclasse em outro pacote | Qualquer lugar |
|------------------------|:---:|:---:|:---:|:---:|
| `public`               | sim | sim | sim | sim |
| `protected`            | sim | sim | sim | não |
| *(nenhum — "default")* | sim | sim | não | não |
| `private`              | sim | não | não | não |

Regra prática: comece com `private` e abra só o necessário. Campos de entidade são `private`; métodos que o resto da aplicação chama são `public`.

## 4. Interfaces

Uma **interface** é um contrato: lista o que uma classe sabe fazer, sem dizer como. Quem declara `implements` é obrigado a fornecer todos os métodos abstratos. Uma classe herda de **uma** classe, mas pode implementar **várias** interfaces.

```java
public interface Repositorio<T> {
    T salvar(T item);
    List<T> listar();

    default int contar() {
        return listar().size();
    }
}
```

- `T` é um tipo genérico (seção 6): o mesmo contrato serve para `Cliente`, `Pedido`, `Produto`.
- Métodos sem corpo são abstratos e `public` por padrão.
- Um método **`default`** traz uma implementação pronta, que as classes podem usar ou sobrescrever. Aqui, `contar()` funciona para qualquer implementação.

Implementando e usando o contrato:

```java
public class ClienteRepositorioEmMemoria implements Repositorio<Cliente> {
    private final List<Cliente> clientes = new ArrayList<>();

    @Override
    public Cliente salvar(Cliente cliente) { clientes.add(cliente); return cliente; }

    @Override
    public List<Cliente> listar() { return List.copyOf(clientes); }
}
```

```java
Repositorio<Cliente> repo = new ClienteRepositorioEmMemoria();
repo.salvar(new Cliente("Ana", "ana@exemplo.com"));
System.out.println(repo.contar());   // 1
```

Declarar a variável com o tipo da interface é "programar para a interface": trocar a implementação (memória, banco, mock de teste) não exige mexer em quem consome `Repositorio<Cliente>`.

### Por que JpaRepository "ganha" implementação de graça

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContaining(String trecho);
}
```

Você só escreveu uma interface: sem `implements` e sem corpo de método. Na inicialização, o Spring Data **gera em tempo de execução uma classe que implementa essa interface** e a entrega a quem pedir um `ClienteRepository`. Os métodos herdados (`save`, `findById`, `findAll`, `deleteById`...) já vêm prontos, e para `findByNomeContaining` o framework lê o nome do método e monta o SQL. Entre interfaces a herança usa `extends`; `implements` é para classes.

## 5. Herança e polimorfismo

`extends` faz uma classe herdar campos e métodos de outra (a **superclasse**). A subclasse é "um tipo de" superclasse.

```java
abstract class Pessoa {
    private final String nome;

    protected Pessoa(String nome) { this.nome = nome; }

    public String getNome() { return nome; }
    public abstract String descricao();
}

class PessoaFisica extends Pessoa {
    PessoaFisica(String nome) { super(nome); }

    @Override
    public String descricao() { return getNome() + " (pessoa física)"; }
}

class PessoaJuridica extends Pessoa {
    PessoaJuridica(String nome) { super(nome); }

    @Override
    public String descricao() { return getNome() + " (pessoa jurídica)"; }
}
```

- **`super(...)`** chama o construtor da superclasse e precisa ser a primeira instrução do construtor. `super.metodo()` chama a versão da superclasse.
- **`@Override`** avisa o compilador que você pretende sobrescrever um método. Se errar o nome ou a assinatura, a compilação falha em vez de criar um método novo por engano.
- **Classe abstrata** (`abstract`) não pode ser instanciada e pode ter métodos abstratos que as subclasses são obrigadas a implementar. Diferente da interface, pode guardar campos e estado.
- **`final`** em uma classe impede que ela seja estendida.

### Polimorfismo

Uma variável do tipo da superclasse (ou interface) pode apontar para qualquer subclasse, e o método chamado é o do objeto **real**:

```java
List<Pessoa> pessoas = List.of(new PessoaFisica("Ana"), new PessoaJuridica("Padaria Pão Quente"));

for (Pessoa pessoa : pessoas) {
    System.out.println(pessoa.descricao());
}
```

O mesmo laço imprime duas descrições diferentes, e acrescentar um novo tipo de pessoa não exige mexer nele.

### Composição em vez de herança (regra de bolso)

Pergunte: **"X é um Y"** ou **"X tem um Y"**? `PessoaFisica` **é uma** `Pessoa`: herança faz sentido. Um `Pedido` **tem um** `Cliente` e **tem** produtos: use composição, isto é, campos.

```java
record Produto(String nome, BigDecimal preco) {
}

public class Pedido {
    private final Cliente cliente;
    private final List<Produto> itens = new ArrayList<>();

    public Pedido(Cliente cliente) { this.cliente = cliente; }

    public void adicionar(Produto produto) { itens.add(produto); }

    public BigDecimal total() {
        return itens.stream().map(Produto::preco).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
```

(`Produto` aqui é um `record`, explicado na seção 9.) Na dúvida, prefira composição. A herança acopla a subclasse à superclasse e só se justifica quando a relação "é um" é real e estável.

## 6. Generics

Generics deixam uma classe ou interface trabalhar com um tipo **escolhido na hora do uso**, com checagem em compilação. Sem eles, uma lista guardaria `Object` e você precisaria de cast (conversão manual de tipo) a cada leitura.

```java
List<Cliente> clientes = new ArrayList<>();
Optional<Cliente> achado = Optional.empty();
Map<String, Integer> estoquePorSku = new HashMap<>();
```

- `List<Cliente>`: lista só de `Cliente`. `clientes.add("texto")` nem compila.
- `Optional<Cliente>`: caixa que pode conter um `Cliente`.
- `Map<String, Integer>`: chaves `String`, valores `Integer` (dois parâmetros de tipo).
- O `<>` (diamante) na criação pede ao compilador que repita os tipos da esquerda.
- Só tipos de referência entram: `List<Integer>`, nunca `List<int>`.

Para declarar o seu, coloque os parâmetros entre `<>` depois do nome. A interface da seção 4 ganha um segundo parâmetro, o tipo da chave:

```java
public interface RepositorioComId<T, ID> {
    T salvar(T item);
    Optional<T> buscarPorId(ID id);
    List<T> listar();
    void remover(ID id);
}
```

`RepositorioComId<Cliente, Long>` quer dizer "repositório de `Cliente` cuja chave é `Long`". É a mesma leitura de `JpaRepository<Cliente, Long>`: primeiro a entidade (`T`), depois o tipo do `@Id` (`ID`).

## 7. Coleções essenciais

| Interface  | Implementação comum | Característica |
|------------|---------------------|----------------|
| `List<E>`  | `ArrayList` | ordenada, aceita repetidos, acesso por índice |
| `Set<E>`   | `HashSet`   | sem repetidos, sem ordem garantida |
| `Map<K,V>` | `HashMap`   | pares chave → valor, chaves únicas |

Declare com a interface e crie com a implementação: `List<Cliente> lista = new ArrayList<>();`.

```java
List<String> nomes = new ArrayList<>();
nomes.add("Ana");
nomes.add("Bruno");
nomes.add("Ana");
System.out.println(nomes.size());   // 3

Set<String> unicos = new HashSet<>(nomes);
System.out.println(unicos.size());  // 2

Map<String, Integer> estoque = new HashMap<>();
estoque.put("caneta", 50);
int canetas = estoque.getOrDefault("caneta", 0);
boolean temLapis = estoque.containsKey("lapis");
```

`List.of(...)`, `Set.of(...)` e `Map.of(...)` criam coleções **imutáveis**: `add` e `remove` lançam `UnsupportedOperationException`. Para uma cópia editável, use `new ArrayList<>(List.of(...))`.

O **for-each** percorre qualquer coleção. Não remova elementos de uma lista dentro dele (lança `ConcurrentModificationException`); use `lista.removeIf(...)`.

```java
for (String nome : nomes) {
    System.out.println(nome);
}
```

### Streams: filter, map, collect

Um **stream** descreve uma cadeia de transformações sobre uma coleção, sem alterá-la. O `->` define uma **lambda** (função anônima curta); `Cliente::getNome` é uma referência a método. Os dois são explicados na seção 8.

```java
List<Cliente> clientes = List.of(
        new Cliente("Ana", "ana@exemplo.com"),
        new Cliente("Bruno", "bruno@teste.com"));

List<Cliente> doDominio = clientes.stream()
        .filter(c -> c.getEmail().endsWith("@exemplo.com"))
        .toList();

List<String> nomes = clientes.stream()
        .map(Cliente::getNome)
        .toList();

Map<String, Cliente> porNome = clientes.stream()
        .collect(Collectors.toMap(Cliente::getNome, c -> c));
```

- **`filter`** mantém só os elementos que satisfazem a condição (1 cliente em `doDominio`).
- **`map`** transforma cada elemento em outra coisa (`Cliente` → `String`).
- **`collect`** junta o resultado numa estrutura final: um `Map`, uma `String` (`Collectors.joining(", ")`), um agrupamento (`Collectors.groupingBy`). Em listas simples, `.toList()` (Java 16+) é o atalho.

(Este trecho precisa de `import java.util.stream.Collectors;`.)

## 8. Membros funcionais: lambdas, referências a métodos e Optional

Desde o Java 8, um **comportamento** pode ser guardado em variável e passado como argumento, do mesmo jeito que um número ou uma `String`. Três peças fazem isso funcionar: **interfaces funcionais** (o tipo), **lambdas** e **referências a métodos** (o valor). O `Optional` e os streams são construídos em cima delas.

Os trechos desta seção precisam de `import java.util.function.*;`.

### Interfaces funcionais

Uma **interface funcional** tem exatamente **um método abstrato**. Qualquer lambda compatível com esse método pode ser atribuída a ela. O pacote `java.util.function` já traz as mais usadas:

| Interface | Método | Recebe → devolve | Onde aparece |
|---|---|---|---|
| `Function<T, R>` | `apply` | `T` → `R` | `Optional.map`, `stream().map` |
| `Predicate<T>` | `test` | `T` → `boolean` | `Optional.filter`, `stream().filter`, `removeIf` |
| `Consumer<T>` | `accept` | `T` → nada | `Optional.ifPresent`, `forEach` |
| `Supplier<T>` | `get` | nada → `T` | `Optional.orElseGet`, `orElseThrow` |
| `BiFunction<T, U, R>` | `apply` | `T`, `U` → `R` | `Map.merge`, `Map.compute` |
| `UnaryOperator<T>` | `apply` | `T` → `T` | `List.replaceAll` |
| `Runnable` | `run` | nada → nada | `Optional.ifPresentOrElse`, threads |

```java
Function<String, Integer> tamanho = texto -> texto.length();
Predicate<String> emBranco = texto -> texto.isBlank();
Consumer<String> imprimir = texto -> System.out.println(texto);
Supplier<LocalDate> hoje = () -> LocalDate.now();

System.out.println(tamanho.apply("Ana"));   // 3
System.out.println(emBranco.test("  "));     // true
imprimir.accept("Olá");                     // Olá
```

Você também pode declarar a sua. `@FunctionalInterface` é opcional, mas faz o compilador recusar um segundo método abstrato:

```java
@FunctionalInterface
interface Desconto {
    BigDecimal aplicar(BigDecimal valor);
}

Desconto dezPorCento = valor -> valor.multiply(new BigDecimal("0.90"));
System.out.println(dezPorCento.aplicar(new BigDecimal("200.00")));   // 180.0000
```

### Sintaxe da lambda

| Forma | Exemplo |
|---|---|
| Um parâmetro | `x -> x * 2` |
| Vários parâmetros | `(a, b) -> a + b` |
| Sem parâmetros | `() -> "padrão"` |
| Com tipos explícitos | `(String a, String b) -> a.compareTo(b)` |
| Com bloco | `x -> { int dobro = x * 2; return dobro + 1; }` |

Na forma curta, o valor da expressão é o retorno. Com chaves, use `return`.

Uma lambda pode **ler** variáveis locais de fora dela, desde que elas nunca mudem depois de atribuídas (*efetivamente finais*):

```java
int limite = 10;
Predicate<Integer> acimaDoLimite = n -> n > limite;
// limite = 20;   // não compila: a lambda usa limite, que precisa ser efetivamente final

System.out.println(acimaDoLimite.test(15));   // true
```

### Referências a métodos

Quando a lambda só repassa os parâmetros para um método que já existe, dá para escrever `Classe::metodo`:

| Tipo | Referência | Lambda equivalente |
|---|---|---|
| Método estático | `Integer::parseInt` | `texto -> Integer.parseInt(texto)` |
| Método de um objeto específico | `System.out::println` | `x -> System.out.println(x)` |
| Método do próprio parâmetro | `Cliente::getNome` | `cliente -> cliente.getNome()` |
| Construtor | `ArrayList::new` | `() -> new ArrayList<>()` |

Nos controllers dos projetos, `.map(ResponseEntity::ok)` é o mesmo que `.map(livro -> ResponseEntity.ok(livro))`.

### Optional em detalhe

Três formas de criar:

- `Optional.of(valor)`: o valor **não pode** ser `null` (lança `NullPointerException`).
- `Optional.ofNullable(valor)`: vazio se o valor for `null`.
- `Optional.empty()`: vazio.

| Método | O que faz |
|---|---|
| `isPresent()` / `isEmpty()` | Diz se há valor. |
| `orElse(padrao)` | Devolve o valor ou o padrão. |
| `orElseGet(() -> ...)` | Igual, mas só calcula o padrão se estiver vazio. |
| `orElseThrow()` | Devolve o valor ou lança `NoSuchElementException`. |
| `orElseThrow(() -> new X())` | Devolve o valor ou lança a exceção indicada. |
| `map(f)` | Transforma o valor; se `f` devolver `null`, o resultado fica vazio. |
| `flatMap(f)` | Como `map`, para `f` que já devolve `Optional`. |
| `filter(condicao)` | Mantém o valor só se a condição for verdadeira. |
| `ifPresent(acao)` | Executa a ação se houver valor. |
| `ifPresentOrElse(acao, senao)` | Executa uma das duas. |
| `or(() -> outroOptional)` | Usa outro `Optional` quando este está vazio. |
| `get()` | Devolve o valor ou lança exceção. Prefira `orElseThrow()`, que deixa a intenção explícita. |

Usando o `Cliente` da seção 2:

```java
Map<Long, Cliente> clientes = Map.of(1L, new Cliente("Ana", "ana@exemplo.com"));

Optional<Cliente> achado = Optional.ofNullable(clientes.get(1L));
Optional<Cliente> ausente = Optional.ofNullable(clientes.get(99L));

String email = achado.map(Cliente::getEmail).orElse("sem e-mail");
String nome = ausente.map(Cliente::getNome).orElse("desconhecido");

System.out.println(email);   // ana@exemplo.com
System.out.println(nome);    // desconhecido

achado.filter(c -> c.getEmail().endsWith("@exemplo.com"))
        .ifPresent(c -> System.out.println("Domínio da empresa: " + c.getNome()));

ausente.ifPresentOrElse(
        c -> System.out.println("Achou " + c.getNome()),
        () -> System.out.println("Não achou"));
```

Saída das duas últimas instruções: `Domínio da empresa: Ana` e `Não achou`.

**`orElse` x `orElseGet`.** O argumento de `orElse` é sempre avaliado, mesmo quando há valor. Se o padrão for caro (uma consulta, um objeto grande), use `orElseGet`:

```java
Supplier<String> padraoCaro = () -> {
    System.out.println("calculando padrão...");
    return "padrão";
};

Optional<String> cheio = Optional.of("valor");
cheio.orElse(padraoCaro.get());   // imprime "calculando padrão..." sem necessidade
cheio.orElseGet(padraoCaro);      // não imprime nada
```

**`map` x `flatMap`.** Se a função já devolve um `Optional`, `map` cria uma caixa dentro de outra; `flatMap` evita isso:

```java
Function<String, Optional<String>> dominio = texto -> {
    int arroba = texto.indexOf('@');
    return arroba < 0 ? Optional.empty() : Optional.of(texto.substring(arroba + 1));
};

Optional<Optional<String>> aninhado = achado.map(Cliente::getEmail).map(dominio);
Optional<String> plano = achado.map(Cliente::getEmail).flatMap(dominio);

System.out.println(plano);   // Optional[exemplo.com]
```

### Optional nos projetos

O `findById` do repository devolve `Optional<T>`. Os controllers transformam essa caixa direto em resposta HTTP:

```java
return repository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
```

1. `findById(id)` devolve `Optional<Livro>`: cheio se o registro existe, vazio se não.
2. `map(ResponseEntity::ok)` troca o `Livro` por um `ResponseEntity<Livro>` com status `200`. Com a caixa vazia, nada acontece.
3. `orElse(...)` entrega o `404` quando a caixa está vazia.

Nos projetos com service, o `atualizar` devolve `Optional.empty()` quando o `id` não existe e `Optional.of(salvo)` quando existe; o controller aplica o mesmo `map`/`orElse`.

Boas práticas:

- Use `Optional` como **retorno**. Como campo de entidade, o JPA não sabe mapeá-lo; como parâmetro, ele só complica a chamada.
- Nunca devolva `null` em um método cujo tipo é `Optional`; devolva `Optional.empty()`.
- Para coleções, devolva uma **lista vazia** em vez de `Optional<List<T>>`.
- Para primitivos, existem `OptionalInt`, `OptionalLong` e `OptionalDouble`, que evitam o boxing.

## 9. Records e Lombok

Os dois existem para evitar o código repetitivo de campos, construtores, getters, `equals`, `hashCode` e `toString`.

### Records

Um `record` é uma classe **imutável** de dados, com tudo isso gerado pelo compilador. É a escolha natural para DTOs (objetos que só transportam dados, como a resposta de uma API).

```java
public record ClienteDTO(Long id, String nome) {
}
```

O código "na mão" equivalente:

```java
public final class ClienteDTO {
    private final Long id;
    private final String nome;

    public ClienteDTO(Long id, String nome) { this.id = id; this.nome = nome; }

    public Long id() { return id; }
    public String nome() { return nome; }

    @Override
    public boolean equals(Object o) {
        return o instanceof ClienteDTO d && Objects.equals(id, d.id) && Objects.equals(nome, d.nome);
    }

    @Override
    public int hashCode() { return Objects.hash(id, nome); }

    @Override
    public String toString() { return "ClienteDTO[id=" + id + ", nome=" + nome + "]"; }
}
```

Diferenças: os acessores se chamam `id()` e `nome()` (sem `get`), não há setters e os campos são `final`. Para validar, use um **construtor compacto** dentro do record: `public ClienteDTO { if (nome == null) throw new IllegalArgumentException("Nome é obrigatório"); }`. Records **não servem como `@Entity`**: o JPA precisa de construtor sem argumentos e de campos mutáveis.

### Lombok

Lombok gera esse código por anotações e atua **em tempo de compilação**: um processador de anotações altera a árvore do código antes de virar `.class`. O `.java` continua curto, mas o `.class` contém os getters e setters (confira com `javap -p Cliente.class`). Por isso a IDE precisa do plugin do Lombok e do processamento de anotações ativado; senão `getNome()` aparece como "método inexistente".

Com Lombok:

```java
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private Long id;
    private String nome;
    private String email;
}
```

Código "na mão" equivalente:

```java
public class Cliente {
    private Long id;
    private String nome;
    private String email;

    public Cliente() { }

    public Cliente(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

| Anotação              | O que gera |
|-----------------------|------------|
| `@Getter`             | um `getX()` por campo (`isX()` para `boolean` primitivo) |
| `@Setter`             | um `setX(...)` por campo não `final` |
| `@NoArgsConstructor`  | construtor sem argumentos |
| `@AllArgsConstructor` | construtor com todos os campos, na ordem de declaração |
| `@Data`               | `@Getter` + `@Setter` + `@ToString` + `@EqualsAndHashCode` + `@RequiredArgsConstructor` |

`@Data` é conveniente para classes simples, mas em **entidades** prefira `@Getter @Setter`: o `equals`, `hashCode` e `toString` gerados incluem todos os campos, inclusive relacionamentos, o que pode causar recursão infinita (`StackOverflowError`) e carregamentos inesperados do banco.

## 10. Anotações

Uma **anotação** é um metadado: uma etiqueta colocada em classe, campo, método ou parâmetro. Sozinha, ela **não executa nada**; quem a lê é outra ferramenta.

- `@Override`: lida pelo **compilador**, que confere se o método realmente sobrescreve algo.
- `@Deprecated`: o compilador avisa quem usar o elemento marcado como obsoleto.
- `@Entity`, `@Id`, `@Column`: lidas pelo **JPA em tempo de execução**, via reflexão (o código inspecionando as próprias classes).
- `@Getter`, `@Setter`: lidas pelo **Lombok em tempo de compilação**.

```java
import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    protected Cliente() {
    }
}
```

Leitura do exemplo: "esta classe é uma tabela (`@Entity`), este campo é a chave primária (`@Id`) gerada pelo banco, esta coluna é obrigatória e tem até 120 caracteres". O framework varre as classes, lê essas etiquetas e monta SQL e mapeamento. Por isso Spring e JPA usam tantas anotações: configuram o comportamento **sem você escrever o código que o liga**.

## 11. Exceções

Uma exceção interrompe o fluxo normal quando algo dá errado e sobe pela pilha de chamadas até alguém tratá-la.

|                          | Checked | Unchecked |
|--------------------------|---------|-----------|
| Herda de                 | `Exception` (exceto `RuntimeException`) | `RuntimeException` |
| Compilador exige tratar? | **sim**: `try/catch` ou `throws` | não |
| Exemplos                 | `IOException`, `SQLException` | `NullPointerException`, `IllegalArgumentException` |
| Uso típico               | falha externa recuperável (arquivo, rede) | erro de programação ou argumento inválido |

Em CRUDs você lança sobretudo exceções **unchecked**.

```java
static int dividir(int a, int b) {
    if (b == 0) {
        throw new IllegalArgumentException("Divisor não pode ser zero");
    }
    return a / b;
}

try {
    System.out.println(dividir(10, 0));
} catch (IllegalArgumentException e) {
    System.out.println("Entrada inválida: " + e.getMessage());
} finally {
    System.out.println("Sempre executa");
}
```

- `catch` captura o tipo indicado (e suas subclasses); coloque os tipos mais específicos primeiro.
- `finally` roda sempre, com ou sem erro. Serve para liberar recursos; para arquivos e conexões, prefira `try-with-resources`.
- Não engula erros com `catch (Exception e) { }` vazio: você perde a causa do problema.

Uma exceção própria combina bem com `Optional.orElseThrow`:

```java
class ClienteNaoEncontradoException extends RuntimeException {
    ClienteNaoEncontradoException(Long id) { super("Cliente não encontrado: " + id); }
}

Cliente achado = clientes.stream()
        .filter(c -> id.equals(c.getId()))
        .findFirst()
        .orElseThrow(() -> new ClienteNaoEncontradoException(id));
```

Uma exceção que ninguém trata derruba a requisição com erro 500; por isso lance exceções com mensagens claras e trate-as num ponto central.

## 12. Erros comuns de iniciante

| Erro | Causa | Correção |
|------|-------|----------|
| `NullPointerException` | Chamar método ou ler campo de uma referência `null`, ou fazer unboxing de um `Integer`/`Long` `null`. | Cheque `null`, devolva `Optional`, inicialize coleções (`new ArrayList<>()`), escreva `"abc".equals(x)` em vez de `x.equals("abc")`. |
| Comparar `String` com `==` | `==` compara referências, não conteúdo; o resultado varia conforme a origem da `String`. | Use `a.equals(b)` ou `equalsIgnoreCase`. |
| Comparar `Integer`/`Long` com `==` | Acima de 127 são objetos distintos e dá `false`. | Use `equals` ou `Objects.equals(a, b)`. |
| Esquecer o construtor vazio na entidade | Ao declarar um construtor com argumentos, o Java deixa de gerar o padrão; o JPA exige construtor sem argumentos (`public` ou `protected`). | Declare `protected Cliente() {}` ou use `@NoArgsConstructor`. |
| Campo `static` por engano | `static` pertence à classe, é compartilhado por todos os objetos, e o JPA não persiste campos estáticos. | Remova o `static` dos campos de dados da entidade. |
| `double` para dinheiro | Representação binária imprecisa (`0.1 + 0.2 != 0.3`). | Use `BigDecimal` criado com `new BigDecimal("0.10")`. |
| Setter que não altera nada | Faltou o `this`: `nome = nome;` atribui o parâmetro a ele mesmo. | Escreva `this.nome = nome;`. |
| Método não sobrescreve nada | Assinatura errada, como `equals(Cliente o)` em vez de `equals(Object o)`. | Use sempre `@Override`: o compilador acusa o erro. |
| `UnsupportedOperationException` / `ConcurrentModificationException` | Alterar uma lista imutável (`List.of`, `.toList()`) ou remover elemento durante um for-each. | Copie com `new ArrayList<>(...)` e use `lista.removeIf(...)`. |
| `NoSuchElementException` | Chamar `Optional.get()` com o `Optional` vazio. | Use `orElse`, `orElseThrow` ou `ifPresent`. |
| `getNome()` "não existe" mesmo com `@Getter` | O Lombok não está ativo: falta o plugin da IDE ou o processamento de anotações. | Instale o plugin, ative o processamento e recompile. |
| `StackOverflowError` com `@Data` na entidade | `toString`/`hashCode` gerados percorrem relacionamentos que apontam um para o outro. | Use `@Getter @Setter` na entidade e escreva `equals`/`hashCode` só com o `id`. |
