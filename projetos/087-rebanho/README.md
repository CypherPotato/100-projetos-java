# 087 · Rebanho

**Categoria:** Agro e Logística
**Lombok:** não · **Camada Service:** sim

Gestão de lotes e animais de um rebanho bovino.

## Entidades

### Lote (tabela `lotes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `pasto` | `String` |
| `capacidade` | `Integer` |

### Animal (tabela `animais`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `brinco` | `String` |
| `raca` | `String` |
| `pesoKg` | `Double` |
| `dataNascimento` | `LocalDate` |
| `lote` | `Lote` (ManyToOne, coluna `lote_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/lotes` | Lista todos |
| GET | `/lotes/{id}` | Busca por id (404 se não existir) |
| POST | `/lotes` | Cria (201) |
| PUT | `/lotes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/lotes/{id}` | Exclui (204) |
| GET | `/animais` | Lista todos |
| GET | `/animais?loteId=1` | Lista filtrando por Lote |
| GET | `/animais/{id}` | Busca por id (404 se não existir) |
| POST | `/animais` | Cria (201) |
| PUT | `/animais/{id}` | Atualiza (404 se não existir) |
| DELETE | `/animais/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/lotes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Lote → Animal.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/lotes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "pasto": "Pasto exemplo", "capacidade": 10 }'
```

```bash
# Lote
curl -X POST http://localhost:8080/lotes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pasto": "Pasto exemplo", "capacidade": 10 }'
curl http://localhost:8080/lotes
curl http://localhost:8080/lotes/1
curl -X PUT http://localhost:8080/lotes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pasto": "Pasto exemplo", "capacidade": 10 }'
curl -X DELETE http://localhost:8080/lotes/1

# Animal
curl -X POST http://localhost:8080/animais -H "Content-Type: application/json" -d '{ "brinco": "Brinco exemplo", "raca": "Raca exemplo", "pesoKg": 12.5, "dataNascimento": "2026-03-15", "lote": { "id": 1 } }'
curl http://localhost:8080/animais
curl http://localhost:8080/animais/1
curl -X PUT http://localhost:8080/animais/1 -H "Content-Type: application/json" -d '{ "brinco": "Brinco exemplo", "raca": "Raca exemplo", "pesoKg": 12.5, "dataNascimento": "2026-03-15", "lote": { "id": 1 } }'
curl -X DELETE http://localhost:8080/animais/1
```

## Estrutura

```
src/main/java/com/exemplo/rebanho/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
