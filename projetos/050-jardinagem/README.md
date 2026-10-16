# 050 · Jardinagem

**Categoria:** Serviços
**Lombok:** sim · **Camada Service:** não

Controle de clientes e serviços de jardinagem prestados.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `endereco` | `String` |

### ServicoJardim (tabela `servicos_jardim`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `areaM2` | `Double` |
| `valor` | `BigDecimal` |
| `dataServico` | `LocalDate` |
| `recorrente` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/servicos-jardim` | Lista todos |
| GET | `/servicos-jardim?clienteId=1` | Lista filtrando por Cliente |
| GET | `/servicos-jardim/{id}` | Busca por id (404 se não existir) |
| POST | `/servicos-jardim` | Cria (201) |
| PUT | `/servicos-jardim/{id}` | Atualiza (404 se não existir) |
| DELETE | `/servicos-jardim/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → ServicoJardim.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
curl -X DELETE http://localhost:8080/clientes/1

# ServicoJardim
curl -X POST http://localhost:8080/servicos-jardim -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "areaM2": 12.5, "valor": 99.90, "dataServico": "2026-03-15", "recorrente": true, "cliente": { "id": 1 } }'
curl http://localhost:8080/servicos-jardim
curl http://localhost:8080/servicos-jardim/1
curl -X PUT http://localhost:8080/servicos-jardim/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "areaM2": 12.5, "valor": 99.90, "dataServico": "2026-03-15", "recorrente": true, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/servicos-jardim/1
```

## Estrutura

```
src/main/java/com/exemplo/jardinagem/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
