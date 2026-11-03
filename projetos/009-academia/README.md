# 009 · Academia

**Categoria:** Saúde
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** sim

Gestão de planos e alunos de uma academia de ginástica.

## Entidades

### Plano (tabela `planos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `mensalidade` | `BigDecimal` |
| `duracaoMeses` | `Integer` |

### Aluno (tabela `alunos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataInicio` | `LocalDate` |
| `ativo` | `Boolean` |
| `plano` | `Plano` (ManyToOne, coluna `plano_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/planos` | Lista todos |
| GET | `/planos/{id}` | Busca por id (404 se não existir) |
| POST | `/planos` | Cria (201) |
| PUT | `/planos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/planos/{id}` | Exclui (204) |
| GET | `/alunos` | Lista todos |
| GET | `/alunos?planoId=1` | Lista filtrando por Plano |
| GET | `/alunos/{id}` | Busca por id (404 se não existir) |
| POST | `/alunos` | Cria (201) |
| PUT | `/alunos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/alunos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/planos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Plano → Aluno.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/planos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "mensalidade": 99.90, "duracaoMeses": 10 }'
```

```bash
# Plano
curl -X POST http://localhost:8080/planos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "mensalidade": 99.90, "duracaoMeses": 10 }'
curl http://localhost:8080/planos
curl http://localhost:8080/planos/1
curl -X PUT http://localhost:8080/planos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "mensalidade": 99.90, "duracaoMeses": 10 }'
curl -X DELETE http://localhost:8080/planos/1

# Aluno
curl -X POST http://localhost:8080/alunos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataInicio": "2026-03-15", "ativo": true, "plano": { "id": 1 } }'
curl http://localhost:8080/alunos
curl http://localhost:8080/alunos/1
curl -X PUT http://localhost:8080/alunos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataInicio": "2026-03-15", "ativo": true, "plano": { "id": 1 } }'
curl -X DELETE http://localhost:8080/alunos/1
```

## Estrutura

```
src/main/java/com/exemplo/academia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
