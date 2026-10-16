# 022 · Faculdade

**Categoria:** Educação
**Lombok:** não · **Camada Service:** não

Cadastro de cursos superiores e suas disciplinas.

## Entidades

### Curso (tabela `cursos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `duracaoSemestres` | `Integer` |
| `mensalidade` | `BigDecimal` |

### Disciplina (tabela `disciplinas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cargaHoraria` | `Integer` |
| `obrigatoria` | `Boolean` |
| `curso` | `Curso` (ManyToOne, coluna `curso_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/cursos` | Lista todos |
| GET | `/cursos/{id}` | Busca por id (404 se não existir) |
| POST | `/cursos` | Cria (201) |
| PUT | `/cursos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cursos/{id}` | Exclui (204) |
| GET | `/disciplinas` | Lista todos |
| GET | `/disciplinas?cursoId=1` | Lista filtrando por Curso |
| GET | `/disciplinas/{id}` | Busca por id (404 se não existir) |
| POST | `/disciplinas` | Cria (201) |
| PUT | `/disciplinas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/disciplinas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/cursos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Curso → Disciplina.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/cursos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "duracaoSemestres": 10, "mensalidade": 99.90 }'
```

```bash
# Curso
curl -X POST http://localhost:8080/cursos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "duracaoSemestres": 10, "mensalidade": 99.90 }'
curl http://localhost:8080/cursos
curl http://localhost:8080/cursos/1
curl -X PUT http://localhost:8080/cursos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "duracaoSemestres": 10, "mensalidade": 99.90 }'
curl -X DELETE http://localhost:8080/cursos/1

# Disciplina
curl -X POST http://localhost:8080/disciplinas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cargaHoraria": 10, "obrigatoria": true, "curso": { "id": 1 } }'
curl http://localhost:8080/disciplinas
curl http://localhost:8080/disciplinas/1
curl -X PUT http://localhost:8080/disciplinas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cargaHoraria": 10, "obrigatoria": true, "curso": { "id": 1 } }'
curl -X DELETE http://localhost:8080/disciplinas/1
```

## Estrutura

```
src/main/java/com/exemplo/faculdade/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
