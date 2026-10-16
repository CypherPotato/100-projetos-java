# 074 · Chaves de API

**Categoria:** Tecnologia
**Lombok:** não · **Camada Service:** não

Emissão de chaves de API por cliente e registro do consumo de cada chave.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `plano` | `String` |

### ApiKey (tabela `api_keys`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `chave` | `String` |
| `criadaEm` | `LocalDateTime` |
| `expiraEm` | `LocalDate` |
| `ativa` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

### Consumo (tabela `consumos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `endpoint` | `String` |
| `quantidade` | `Integer` |
| `dataConsumo` | `LocalDate` |
| `apiKey` | `ApiKey` (ManyToOne, coluna `api_key_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/api-keys` | Lista todos |
| GET | `/api-keys?clienteId=1` | Lista filtrando por Cliente |
| GET | `/api-keys/{id}` | Busca por id (404 se não existir) |
| POST | `/api-keys` | Cria (201) |
| PUT | `/api-keys/{id}` | Atualiza (404 se não existir) |
| DELETE | `/api-keys/{id}` | Exclui (204) |
| GET | `/consumos` | Lista todos |
| GET | `/consumos?apiKeyId=1` | Lista filtrando por ApiKey |
| GET | `/consumos/{id}` | Busca por id (404 se não existir) |
| POST | `/consumos` | Cria (201) |
| PUT | `/consumos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/consumos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → ApiKey → Consumo.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "plano": "Plano exemplo" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "plano": "Plano exemplo" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "plano": "Plano exemplo" }'
curl -X DELETE http://localhost:8080/clientes/1

# ApiKey
curl -X POST http://localhost:8080/api-keys -H "Content-Type: application/json" -d '{ "chave": "Chave exemplo", "criadaEm": "2026-03-15T10:30:00", "expiraEm": "2026-03-15", "ativa": true, "cliente": { "id": 1 } }'
curl http://localhost:8080/api-keys
curl http://localhost:8080/api-keys/1
curl -X PUT http://localhost:8080/api-keys/1 -H "Content-Type: application/json" -d '{ "chave": "Chave exemplo", "criadaEm": "2026-03-15T10:30:00", "expiraEm": "2026-03-15", "ativa": true, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/api-keys/1

# Consumo
curl -X POST http://localhost:8080/consumos -H "Content-Type: application/json" -d '{ "endpoint": "Endpoint exemplo", "quantidade": 10, "dataConsumo": "2026-03-15", "apiKey": { "id": 1 } }'
curl http://localhost:8080/consumos
curl http://localhost:8080/consumos/1
curl -X PUT http://localhost:8080/consumos/1 -H "Content-Type: application/json" -d '{ "endpoint": "Endpoint exemplo", "quantidade": 10, "dataConsumo": "2026-03-15", "apiKey": { "id": 1 } }'
curl -X DELETE http://localhost:8080/consumos/1
```

## Estrutura

```
src/main/java/com/exemplo/apikeys/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
