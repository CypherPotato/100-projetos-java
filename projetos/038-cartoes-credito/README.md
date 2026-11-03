# 038 · Cartões de Crédito

**Categoria:** Finanças
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Controle de cartões de crédito, faturas e compras realizadas.

## Entidades

### Cartao (tabela `cartoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `bandeira` | `String` |
| `titular` | `String` |
| `limite` | `BigDecimal` |
| `diaVencimento` | `Integer` |

### Fatura (tabela `faturas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `competencia` | `String` |
| `valorTotal` | `BigDecimal` |
| `vencimento` | `LocalDate` |
| `paga` | `Boolean` |
| `cartao` | `Cartao` (ManyToOne, coluna `cartao_id`) |

### Compra (tabela `compras`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `valor` | `BigDecimal` |
| `dataCompra` | `LocalDate` |
| `fatura` | `Fatura` (ManyToOne, coluna `fatura_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/cartoes` | Lista todos |
| GET | `/cartoes/{id}` | Busca por id (404 se não existir) |
| POST | `/cartoes` | Cria (201) |
| PUT | `/cartoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cartoes/{id}` | Exclui (204) |
| GET | `/faturas` | Lista todos |
| GET | `/faturas?cartaoId=1` | Lista filtrando por Cartao |
| GET | `/faturas/{id}` | Busca por id (404 se não existir) |
| POST | `/faturas` | Cria (201) |
| PUT | `/faturas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/faturas/{id}` | Exclui (204) |
| GET | `/compras` | Lista todos |
| GET | `/compras?faturaId=1` | Lista filtrando por Fatura |
| GET | `/compras/{id}` | Busca por id (404 se não existir) |
| POST | `/compras` | Cria (201) |
| PUT | `/compras/{id}` | Atualiza (404 se não existir) |
| DELETE | `/compras/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/cartoes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cartao → Fatura → Compra.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/cartoes -ContentType "application/json" -Body '{ "bandeira": "Bandeira exemplo", "titular": "Titular exemplo", "limite": 99.90, "diaVencimento": 10 }'
```

```bash
# Cartao
curl -X POST http://localhost:8080/cartoes -H "Content-Type: application/json" -d '{ "bandeira": "Bandeira exemplo", "titular": "Titular exemplo", "limite": 99.90, "diaVencimento": 10 }'
curl http://localhost:8080/cartoes
curl http://localhost:8080/cartoes/1
curl -X PUT http://localhost:8080/cartoes/1 -H "Content-Type: application/json" -d '{ "bandeira": "Bandeira exemplo", "titular": "Titular exemplo", "limite": 99.90, "diaVencimento": 10 }'
curl -X DELETE http://localhost:8080/cartoes/1

# Fatura
curl -X POST http://localhost:8080/faturas -H "Content-Type: application/json" -d '{ "competencia": "Competencia exemplo", "valorTotal": 99.90, "vencimento": "2026-03-15", "paga": true, "cartao": { "id": 1 } }'
curl http://localhost:8080/faturas
curl http://localhost:8080/faturas/1
curl -X PUT http://localhost:8080/faturas/1 -H "Content-Type: application/json" -d '{ "competencia": "Competencia exemplo", "valorTotal": 99.90, "vencimento": "2026-03-15", "paga": true, "cartao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/faturas/1

# Compra
curl -X POST http://localhost:8080/compras -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "dataCompra": "2026-03-15", "fatura": { "id": 1 } }'
curl http://localhost:8080/compras
curl http://localhost:8080/compras/1
curl -X PUT http://localhost:8080/compras/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "dataCompra": "2026-03-15", "fatura": { "id": 1 } }'
curl -X DELETE http://localhost:8080/compras/1
```

## Estrutura

```
src/main/java/com/exemplo/cartoescredito/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
