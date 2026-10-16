# 004 · Escola

**Categoria:** Educação
**Lombok:** não · **Camada Service:** não

Gerenciamento de turmas e alunos matriculados em uma escola.

## Entidades

### Turma (tabela `turmas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `anoLetivo` | `Integer` |
| `turno` | `String` |

### Aluno (tabela `alunos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `matricula` | `String` |
| `dataNascimento` | `LocalDate` |
| `mensalidade` | `BigDecimal` |
| `turma` | `Turma` (ManyToOne, coluna `turma_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/turmas` | Lista todos |
| GET | `/turmas/{id}` | Busca por id (404 se não existir) |
| POST | `/turmas` | Cria (201) |
| PUT | `/turmas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/turmas/{id}` | Exclui (204) |
| GET | `/alunos` | Lista todos |
| GET | `/alunos?turmaId=1` | Lista filtrando por Turma |
| GET | `/alunos/{id}` | Busca por id (404 se não existir) |
| POST | `/alunos` | Cria (201) |
| PUT | `/alunos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/alunos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/turmas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Turma → Aluno.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/turmas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "anoLetivo": 10, "turno": "Turno exemplo" }'
```

```bash
# Turma
curl -X POST http://localhost:8080/turmas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "anoLetivo": 10, "turno": "Turno exemplo" }'
curl http://localhost:8080/turmas
curl http://localhost:8080/turmas/1
curl -X PUT http://localhost:8080/turmas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "anoLetivo": 10, "turno": "Turno exemplo" }'
curl -X DELETE http://localhost:8080/turmas/1

# Aluno
curl -X POST http://localhost:8080/alunos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "matricula": "Matricula exemplo", "dataNascimento": "2026-03-15", "mensalidade": 99.90, "turma": { "id": 1 } }'
curl http://localhost:8080/alunos
curl http://localhost:8080/alunos/1
curl -X PUT http://localhost:8080/alunos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "matricula": "Matricula exemplo", "dataNascimento": "2026-03-15", "mensalidade": 99.90, "turma": { "id": 1 } }'
curl -X DELETE http://localhost:8080/alunos/1
```

## Estrutura

```
src/main/java/com/exemplo/escola/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
