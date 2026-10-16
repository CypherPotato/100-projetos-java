# 092 · Apicultura

**Categoria:** Agro e Logística
**Lombok:** sim · **Camada Service:** não

Acompanhamento de apiários, colmeias e colheitas de mel.

## Entidades

### Apiario (tabela `apiarios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `localizacao` | `String` |
| `floracao` | `String` |

### Colmeia (tabela `colmeias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `codigo` | `String` |
| `especie` | `String` |
| `dataInstalacao` | `LocalDate` |
| `apiario` | `Apiario` (ManyToOne, coluna `apiario_id`) |

### Colheita (tabela `colheitas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `quantidadeKg` | `Double` |
| `dataColheita` | `LocalDate` |
| `valorEstimado` | `BigDecimal` |
| `colmeia` | `Colmeia` (ManyToOne, coluna `colmeia_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/apiarios` | Lista todos |
| GET | `/apiarios/{id}` | Busca por id (404 se não existir) |
| POST | `/apiarios` | Cria (201) |
| PUT | `/apiarios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/apiarios/{id}` | Exclui (204) |
| GET | `/colmeias` | Lista todos |
| GET | `/colmeias?apiarioId=1` | Lista filtrando por Apiario |
| GET | `/colmeias/{id}` | Busca por id (404 se não existir) |
| POST | `/colmeias` | Cria (201) |
| PUT | `/colmeias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/colmeias/{id}` | Exclui (204) |
| GET | `/colheitas` | Lista todos |
| GET | `/colheitas?colmeiaId=1` | Lista filtrando por Colmeia |
| GET | `/colheitas/{id}` | Busca por id (404 se não existir) |
| POST | `/colheitas` | Cria (201) |
| PUT | `/colheitas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/colheitas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/apiarios

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Apiario → Colmeia → Colheita.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/apiarios -ContentType "application/json" -Body '{ "nome": "Maria Silva", "localizacao": "Localizacao exemplo", "floracao": "Floracao exemplo" }'
```

```bash
# Apiario
curl -X POST http://localhost:8080/apiarios -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "localizacao": "Localizacao exemplo", "floracao": "Floracao exemplo" }'
curl http://localhost:8080/apiarios
curl http://localhost:8080/apiarios/1
curl -X PUT http://localhost:8080/apiarios/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "localizacao": "Localizacao exemplo", "floracao": "Floracao exemplo" }'
curl -X DELETE http://localhost:8080/apiarios/1

# Colmeia
curl -X POST http://localhost:8080/colmeias -H "Content-Type: application/json" -d '{ "codigo": "COD-001", "especie": "Especie exemplo", "dataInstalacao": "2026-03-15", "apiario": { "id": 1 } }'
curl http://localhost:8080/colmeias
curl http://localhost:8080/colmeias/1
curl -X PUT http://localhost:8080/colmeias/1 -H "Content-Type: application/json" -d '{ "codigo": "COD-001", "especie": "Especie exemplo", "dataInstalacao": "2026-03-15", "apiario": { "id": 1 } }'
curl -X DELETE http://localhost:8080/colmeias/1

# Colheita
curl -X POST http://localhost:8080/colheitas -H "Content-Type: application/json" -d '{ "quantidadeKg": 12.5, "dataColheita": "2026-03-15", "valorEstimado": 99.90, "colmeia": { "id": 1 } }'
curl http://localhost:8080/colheitas
curl http://localhost:8080/colheitas/1
curl -X PUT http://localhost:8080/colheitas/1 -H "Content-Type: application/json" -d '{ "quantidadeKg": 12.5, "dataColheita": "2026-03-15", "valorEstimado": 99.90, "colmeia": { "id": 1 } }'
curl -X DELETE http://localhost:8080/colheitas/1
```

## Estrutura

```
src/main/java/com/exemplo/apicultura/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
