# {{TOTAL}} exemplos de CRUD com Java + Spring Boot + SQL Server

Coleção de {{TOTAL}} sistemas pequenos e didáticos, todos com a mesma receita:

- Java 17+ e Spring Boot {{SPRING_BOOT}} (Spring Web + Spring Data JPA);
- SQL Server como banco, com as tabelas criadas automaticamente pelo Hibernate;
- 2 ou 3 entidades relacionadas por `@ManyToOne`;
- CRUD REST completo por entidade (`GET`, `GET /{id}`, `POST`, `PUT`, `DELETE`);
- {{LOMBOK}} projetos usam Lombok e {{SERVICE}} têm uma camada `Service` entre o controller e o repositório (os demais chamam o repositório direto no controller).

Cada projeto é independente: tem o próprio `pom.xml`, `run.ps1`/`run.bat` e `README.md` com as entidades, endpoints e exemplos de `curl`.

## Pré-requisitos

Somente dois programas precisam estar instalados:

1. **JDK 17 ou superior** (precisa ser o JDK, com `javac`; ex.: [Temurin](https://adoptium.net/temurin/releases/)).
2. **SQL Server** (Express serve; o script de setup foi testado com `SQLEXPRESS`).

O Maven e todas as dependências são baixados automaticamente pelo `run.ps1` na primeira execução (cache em `%LOCALAPPDATA%\spring-crud-exemplos` e `%USERPROFILE%\.m2`).

## Passo a passo

1. **Prepare o SQL Server uma única vez**, em um PowerShell **como Administrador**:

   ```powershell
   .\setup-sqlserver.ps1
   ```

   O script habilita o TCP/IP na porta 1433, liga a autenticação mista, cria o login `projeto` (senha `Projeto@123`), recria o banco `projeto` vazio e, no final, imprime o passo a passo para apontar o projeto Java para esse banco.

   Parâmetros opcionais: `-Instancia SQLEXPRESS`, `-Banco projeto`, `-Usuario projeto`, `-Senha 'Projeto@123'`, `-Porta 1433`.

2. **Rode um projeto** (duplo clique em `run.bat` ou, no terminal, `.\run.ps1` dentro da pasta do projeto):

   ```powershell
   cd projetos\001-biblioteca
   .\run.ps1
   ```

   A API sobe em http://localhost:8080. Todos os projetos usam o banco `projeto` e a porta 8080, então rode um de cada vez. **Ao trocar de projeto, rode o `setup-sqlserver.ps1` de novo** para zerar o banco: vários projetos usam tabelas com o mesmo nome (`produtos`, `clientes`...) com colunas diferentes, e o `ddl-auto=update` não remove nem altera colunas que já existem.

3. **Teste os endpoints** com `curl`, navegador ou qualquer cliente HTTP. Cada `README.md` de projeto traz os comandos prontos.

## Estrutura de um projeto

```
NNN-slug/
  pom.xml
  run.ps1 / run.bat
  README.md
  src/main/resources/application.properties
  src/main/java/com/exemplo/<slug>/
    Application.java
    model/        entidades JPA (@Entity)
    repository/   interfaces JpaRepository
    service/      (opcional) regras entre controller e repositório
    controller/   endpoints REST (@RestController)
```

## Documentação de apoio

- [docs/01-orientacao-a-objetos-java.md](docs/01-orientacao-a-objetos-java.md) — classes, interfaces, herança, tipos, generics, Lombok.
- [docs/02-arquitetura-mvc-spring.md](docs/02-arquitetura-mvc-spring.md) — separação em pacotes, entidades, repositórios, controllers, fluxo de uma requisição.
- [docs/03-sql-server-guia-rapido.md](docs/03-sql-server-guia-rapido.md) — conexão, sintaxe T-SQL, `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `JOIN`, erros comuns.

## Gerador

Os projetos são gerados a partir de `gerador/spec.json` por `gerador\gerar.ps1` (PowerShell 5.1+, sem dependências extras). Para regenerar tudo: `.\gerador\gerar.ps1`; para regenerar só alguns: `.\gerador\gerar.ps1 12 35`. O contrato da especificação está em `gerador/CONTRATO-SPEC.md`.
