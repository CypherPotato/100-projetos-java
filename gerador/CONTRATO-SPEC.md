# Contrato da especificação dos projetos

O arquivo `spec.json` contém um array JSON de projetos, lido por `gerar.ps1`.

```jsonc
[
  {
    numero: 1,                       // inteiro único, 1..100 (define a ordem e o prefixo da pasta)
    slug: "biblioteca",              // único, apenas [a-z0-9-], vira nome da pasta e do pacote (hífens removidos)
    nome: "Biblioteca",              // nome amigável (pode ter acentos)
    categoria: "Educação",           // uma das categorias da lista abaixo
    descricao: "Controle de autores e livros de uma biblioteca.", // uma frase, com acentos corretos
    lombok: true,                    // true em ~metade dos projetos
    service: false,                  // true em ~um terço dos projetos: gera camada Service entre Controller e Repository
    entidades: [
      {
        nome: "Autor",               // PascalCase ASCII, sem acento, singular
        tabela: "autores",           // snake_case ASCII, plural
        campos: [
          { nome: "nome", tipo: "String" },
          { nome: "nacionalidade", tipo: "String" }
        ]
      },
      {
        nome: "Livro",
        tabela: "livros",
        campos: [
          { nome: "titulo", tipo: "String" },
          { nome: "anoPublicacao", tipo: "Integer" },
          { nome: "disponivel", tipo: "Boolean" }
        ],
        relacoes: [
          { campo: "autor", entidade: "Autor" }   // ManyToOne: Livro.autor -> Autor
        ]
      }
    ]
  }
]
```

## Regras

- 2 ou 3 entidades por projeto. Pelo menos uma relação `ManyToOne` por projeto. Em projetos com 3 entidades, pode haver 2 relações (ex.: `Pedido -> Cliente`, `ItemPedido -> Pedido`).
- A entidade referenciada em `relacoes[].entidade` precisa vir ANTES no array `entidades`.
- Toda entidade recebe automaticamente `id` (Long, identity). Não declare `id`.
- `campos[].nome`: camelCase ASCII, sem acento, sem palavras reservadas do Java (`class`, `default`, `package`, `private`, `public`, `static`, `final`, `new`, `int`, `long`, `double`, `boolean`, `char`, `byte`, `short`, `float`, `void`, `return`, `switch`, `case`, `if`, `else`, `for`, `while`, `do`, `break`, `continue`, `this`, `super`, `null`, `true`, `false`, `import`, `enum`, `interface`, `abstract`, `native`, `synchronized`, `transient`, `volatile`, `throws`, `throw`, `try`, `catch`, `finally`, `instanceof`, `extends`, `implements`, `goto`, `const`, `assert`, `strictfp`). Também não use `id`.
- `campos[].tipo`: somente `String`, `Integer`, `Long`, `Double`, `BigDecimal`, `Boolean`, `LocalDate`, `LocalDateTime`.
- 2 a 5 campos por entidade (além do `id` e das relações).
- Nomes de campo de relação (`relacoes[].campo`) seguem a mesma regra de `campos[].nome` e não podem colidir com nomes de campos.
- `tabela` em snake_case plural ASCII; nomes de entidade únicos dentro do projeto. Evite nomes de tabela que são palavras reservadas do SQL Server (`user`, `order`, `group`, `plan`, `key`, `file`, `index`, `table`, `view`, `function`, `procedure`, `transaction`, `schema`, `public`, `role`, `rule`, `check`, `column`, `database`, `default`, `open`, `close`, `read`, `write`, `print`, `exit`, `return`, `select`, `from`, `where`, `end`, `option`, `set`, `update`, `delete`, `insert`, `into`, `values`, `by`, `to`, `on`, `off`, `in`, `is`, `of`, `or`, `and`, `not`, `null`, `top`, `with`, `over`, `percent`, `status`, `level`, `text`, `image`, `date`, `time`, `year`, `month`, `day`). Como as tabelas são plurais (`usuarios`, `pedidos`), isso raramente ocorre.
- Nomes de campo também não devem ser palavras reservadas do SQL Server (listadas acima); ex.: use `situacao` em vez de `status`, `nivel` em vez de `level`, `texto` em vez de `text`, `data` é permitido.
- Mantenha tudo simples e didático: nada de herança, enums, embeddables ou coleções.

## Categorias válidas

`Comércio`, `Educação`, `Saúde`, `Serviços`, `Gestão`, `Finanças`, `Lazer e Cultura`, `Alimentação`, `Tecnologia`, `Comunidade`, `Agro e Logística`
