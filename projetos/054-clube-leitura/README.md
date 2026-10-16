# 054 · Clube de Leitura

**Categoria:** Lazer e Cultura
**Lombok:** não · **Camada Service:** não

Acompanhamento dos livros lidos pelos membros de um clube de leitura.

## Entidades

### Livro (tabela `livros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `autor` | `String` |
| `paginas` | `Integer` |

### Membro (tabela `membros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `dataEntrada` | `LocalDate` |

### Leitura (tabela `leituras`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataInicio` | `LocalDate` |
| `paginasLidas` | `Integer` |
| `concluida` | `Boolean` |
| `livro` | `Livro` (ManyToOne, coluna `livro_id`) |
| `membro` | `Membro` (ManyToOne, coluna `membro_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/livros` | Lista todos |
| GET | `/livros/{id}` | Busca por id (404 se não existir) |
| POST | `/livros` | Cria (201) |
| PUT | `/livros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/livros/{id}` | Exclui (204) |
| GET | `/membros` | Lista todos |
| GET | `/membros/{id}` | Busca por id (404 se não existir) |
| POST | `/membros` | Cria (201) |
| PUT | `/membros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/membros/{id}` | Exclui (204) |
| GET | `/leituras` | Lista todos |
| GET | `/leituras?livroId=1` | Lista filtrando por Livro |
| GET | `/leituras?membroId=1` | Lista filtrando por Membro |
| GET | `/leituras/{id}` | Busca por id (404 se não existir) |
| POST | `/leituras` | Cria (201) |
| PUT | `/leituras/{id}` | Atualiza (404 se não existir) |
| DELETE | `/leituras/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/livros

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Livro → Membro → Leitura.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/livros -ContentType "application/json" -Body '{ "titulo": "Exemplo", "autor": "Autor exemplo", "paginas": 10 }'
```

```bash
# Livro
curl -X POST http://localhost:8080/livros -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "autor": "Autor exemplo", "paginas": 10 }'
curl http://localhost:8080/livros
curl http://localhost:8080/livros/1
curl -X PUT http://localhost:8080/livros/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "autor": "Autor exemplo", "paginas": 10 }'
curl -X DELETE http://localhost:8080/livros/1

# Membro
curl -X POST http://localhost:8080/membros -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "dataEntrada": "2026-03-15" }'
curl http://localhost:8080/membros
curl http://localhost:8080/membros/1
curl -X PUT http://localhost:8080/membros/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "dataEntrada": "2026-03-15" }'
curl -X DELETE http://localhost:8080/membros/1

# Leitura
curl -X POST http://localhost:8080/leituras -H "Content-Type: application/json" -d '{ "dataInicio": "2026-03-15", "paginasLidas": 10, "concluida": true, "livro": { "id": 1 }, "membro": { "id": 1 } }'
curl http://localhost:8080/leituras
curl http://localhost:8080/leituras/1
curl -X PUT http://localhost:8080/leituras/1 -H "Content-Type: application/json" -d '{ "dataInicio": "2026-03-15", "paginasLidas": 10, "concluida": true, "livro": { "id": 1 }, "membro": { "id": 1 } }'
curl -X DELETE http://localhost:8080/leituras/1
```

## Estrutura

```
src/main/java/com/exemplo/clubeleitura/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
