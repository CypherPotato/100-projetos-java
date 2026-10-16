# 059 · Restaurante

**Categoria:** Alimentação
**Lombok:** sim · **Camada Service:** não

Controle de mesas e dos pedidos feitos em um restaurante.

## Entidades

### Mesa (tabela `mesas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numero` | `Integer` |
| `capacidade` | `Integer` |
| `ocupada` | `Boolean` |

### Pedido (tabela `pedidos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `dataHora` | `LocalDateTime` |
| `valorTotal` | `BigDecimal` |
| `pago` | `Boolean` |
| `mesa` | `Mesa` (ManyToOne, coluna `mesa_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/mesas` | Lista todos |
| GET | `/mesas/{id}` | Busca por id (404 se não existir) |
| POST | `/mesas` | Cria (201) |
| PUT | `/mesas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/mesas/{id}` | Exclui (204) |
| GET | `/pedidos` | Lista todos |
| GET | `/pedidos?mesaId=1` | Lista filtrando por Mesa |
| GET | `/pedidos/{id}` | Busca por id (404 se não existir) |
| POST | `/pedidos` | Cria (201) |
| PUT | `/pedidos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pedidos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/mesas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Mesa → Pedido.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/mesas -ContentType "application/json" -Body '{ "numero": 10, "capacidade": 10, "ocupada": true }'
```

```bash
# Mesa
curl -X POST http://localhost:8080/mesas -H "Content-Type: application/json" -d '{ "numero": 10, "capacidade": 10, "ocupada": true }'
curl http://localhost:8080/mesas
curl http://localhost:8080/mesas/1
curl -X PUT http://localhost:8080/mesas/1 -H "Content-Type: application/json" -d '{ "numero": 10, "capacidade": 10, "ocupada": true }'
curl -X DELETE http://localhost:8080/mesas/1

# Pedido
curl -X POST http://localhost:8080/pedidos -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "dataHora": "2026-03-15T10:30:00", "valorTotal": 99.90, "pago": true, "mesa": { "id": 1 } }'
curl http://localhost:8080/pedidos
curl http://localhost:8080/pedidos/1
curl -X PUT http://localhost:8080/pedidos/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "dataHora": "2026-03-15T10:30:00", "valorTotal": 99.90, "pago": true, "mesa": { "id": 1 } }'
curl -X DELETE http://localhost:8080/pedidos/1
```

## Estrutura

```
src/main/java/com/exemplo/restaurante/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
