# 025 · Floricultura

**Categoria:** Comércio
**Lombok:** sim · **Camada Service:** não

Registro de clientes e encomendas de arranjos de flores.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Encomenda (tabela `encomendas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `dataEntrega` | `LocalDate` |
| `valor` | `BigDecimal` |
| `entregue` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/encomendas` | Lista todos |
| GET | `/encomendas?clienteId=1` | Lista filtrando por Cliente |
| GET | `/encomendas/{id}` | Busca por id (404 se não existir) |
| POST | `/encomendas` | Cria (201) |
| PUT | `/encomendas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/encomendas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Encomenda.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/clientes/1

# Encomenda
curl -X POST http://localhost:8080/encomendas -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "dataEntrega": "2026-03-15", "valor": 99.90, "entregue": true, "cliente": { "id": 1 } }'
curl http://localhost:8080/encomendas
curl http://localhost:8080/encomendas/1
curl -X PUT http://localhost:8080/encomendas/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "dataEntrega": "2026-03-15", "valor": 99.90, "entregue": true, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/encomendas/1
```

## Estrutura

```
src/main/java/com/exemplo/floricultura/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
