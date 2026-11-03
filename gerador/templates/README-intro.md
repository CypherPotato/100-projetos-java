# {{TOTAL}} exemplos de CRUD com Java + Spring Boot + SQL Server ou H2

Coleção de {{TOTAL}} sistemas pequenos e didáticos, todos com a mesma receita:

- Java 17+ e Spring Boot {{SPRING_BOOT}} (Spring Web + Spring Data JPA);
- {{SQLSERVER}} projetos usam **SQL Server** (001–100) e {{H2}} usam **H2** em arquivo (101 em diante), sempre com as tabelas criadas automaticamente pelo Hibernate;
- 2 ou 3 entidades relacionadas por `@ManyToOne`;
- CRUD REST completo por entidade (`GET`, `GET /{id}`, `POST`, `PUT`, `DELETE`);
- {{LOMBOK}} projetos usam Lombok e {{SERVICE}} têm uma camada `Service` entre o controller e o repositório (os demais chamam o repositório direto no controller).

Cada projeto é independente: tem o próprio `pom.xml`, `run.ps1`/`run.bat` e `README.md` com as entidades, endpoints e exemplos de `curl`.

## Pré-requisitos

Somente dois programas precisam estar instalados:

1. **JDK 17 ou superior** (precisa ser o JDK, com `javac`; ex.: [Temurin](https://adoptium.net/temurin/releases/)).
2. **SQL Server** (Express serve; o script de setup foi testado com `SQLEXPRESS`), apenas para os projetos 001–100.

Os projetos H2 não precisam de banco instalado: o H2 é uma biblioteca Java baixada como dependência, e cada projeto grava os dados na própria pasta `data\`.

O Maven, o Tomcat 10.1 e todas as dependências são baixados automaticamente pelo `run.ps1` na primeira execução (cache em `%LOCALAPPDATA%\spring-crud-exemplos` e `%USERPROFILE%\.m2`). Cada projeto é empacotado como WAR (`ROOT.war`) e implantado em um Tomcat externo; a porta é configurável por `-Porta`.

## Passo a passo

1. **Prepare o SQL Server uma única vez** (só para os projetos SQL Server), em um PowerShell **como Administrador**:

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

   Nos projetos H2 isso não é necessário: cada um tem o próprio arquivo de banco. Para zerar, pare o projeto e apague a pasta `data`. O console do H2 fica em http://localhost:8080/h2-console.

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

- [docs/01-orientacao-a-objetos-java.md](docs/01-orientacao-a-objetos-java.md) — classes, interfaces, herança, tipos, generics, lambdas, `Optional`, Lombok.
- [docs/02-arquitetura-mvc-spring.md](docs/02-arquitetura-mvc-spring.md) — separação em pacotes, entidades, repositórios, controllers, fluxo de uma requisição.
- [docs/03-sql-server-guia-rapido.md](docs/03-sql-server-guia-rapido.md) — conexão, sintaxe T-SQL, `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `JOIN`, erros comuns.
- [docs/04-requisicoes-respostas-e-injecao.md](docs/04-requisicoes-respostas-e-injecao.md) — parâmetros e corpo das requisições, status e JSON das respostas, tratamento de erros, service ↔ banco, transações, injeção de dependência.
- [docs/05-configuracao-properties-sqlserver-tomcat.md](docs/05-configuracao-properties-sqlserver-tomcat.md) — `application.properties`, variáveis de ambiente e perfis, configuração do SQL Server e do H2, WAR e Tomcat externo.

## Gerador

Os projetos são gerados a partir de `gerador/spec.json` por `gerador\gerar.ps1` (PowerShell 5.1+, sem dependências extras). Para regenerar tudo: `.\gerador\gerar.ps1`; para regenerar só alguns: `.\gerador\gerar.ps1 12 35`. O contrato da especificação está em `gerador/CONTRATO-SPEC.md`.
