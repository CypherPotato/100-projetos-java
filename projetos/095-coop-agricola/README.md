# 095 · Cooperativa Agrícola

**Categoria:** Agro e Logística
**Lombok:** não · **Camada Service:** não

Registro de agricultores e dos recebimentos de produção da cooperativa.

## Entidades

### Agricultor (tabela `agricultores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cpf` | `String` |
| `propriedade` | `String` |
| `areaHectares` | `Double` |

### Recebimento (tabela `recebimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `produto` | `String` |
| `quantidadeKg` | `Double` |
| `precoKg` | `BigDecimal` |
| `dataRecebimento` | `LocalDate` |
| `agricultor` | `Agricultor` (ManyToOne, coluna `agricultor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/agricultores` | Lista todos |
| GET | `/agricultores/{id}` | Busca por id (404 se não existir) |
| POST | `/agricultores` | Cria (201) |
| PUT | `/agricultores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/agricultores/{id}` | Exclui (204) |
| GET | `/recebimentos` | Lista todos |
| GET | `/recebimentos?agricultorId=1` | Lista filtrando por Agricultor |
| GET | `/recebimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/recebimentos` | Cria (201) |
| PUT | `/recebimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/recebimentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/agricultores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Agricultor → Recebimento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/agricultores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "propriedade": "Propriedade exemplo", "areaHectares": 12.5 }'
```

```bash
# Agricultor
curl -X POST http://localhost:8080/agricultores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "propriedade": "Propriedade exemplo", "areaHectares": 12.5 }'
curl http://localhost:8080/agricultores
curl http://localhost:8080/agricultores/1
curl -X PUT http://localhost:8080/agricultores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "propriedade": "Propriedade exemplo", "areaHectares": 12.5 }'
curl -X DELETE http://localhost:8080/agricultores/1

# Recebimento
curl -X POST http://localhost:8080/recebimentos -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "quantidadeKg": 12.5, "precoKg": 99.90, "dataRecebimento": "2026-03-15", "agricultor": { "id": 1 } }'
curl http://localhost:8080/recebimentos
curl http://localhost:8080/recebimentos/1
curl -X PUT http://localhost:8080/recebimentos/1 -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "quantidadeKg": 12.5, "precoKg": 99.90, "dataRecebimento": "2026-03-15", "agricultor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/recebimentos/1
```

## Estrutura

```
src/main/java/com/exemplo/coopagricola/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
