# 049 · Contas Bancárias

**Categoria:** Finanças
**Lombok:** não · **Camada Service:** não

Controle de bancos, contas bancárias e suas transações.

## Entidades

### Banco (tabela `bancos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `codigo` | `Integer` |

### ContaBancaria (tabela `contas_bancarias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `agencia` | `String` |
| `numero` | `String` |
| `titular` | `String` |
| `saldo` | `BigDecimal` |
| `banco` | `Banco` (ManyToOne, coluna `banco_id`) |

### Transacao (tabela `transacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `valor` | `BigDecimal` |
| `dataTransacao` | `LocalDateTime` |
| `credito` | `Boolean` |
| `conta` | `ContaBancaria` (ManyToOne, coluna `conta_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/bancos` | Lista todos |
| GET | `/bancos/{id}` | Busca por id (404 se não existir) |
| POST | `/bancos` | Cria (201) |
| PUT | `/bancos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/bancos/{id}` | Exclui (204) |
| GET | `/contas-bancarias` | Lista todos |
| GET | `/contas-bancarias?bancoId=1` | Lista filtrando por Banco |
| GET | `/contas-bancarias/{id}` | Busca por id (404 se não existir) |
| POST | `/contas-bancarias` | Cria (201) |
| PUT | `/contas-bancarias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/contas-bancarias/{id}` | Exclui (204) |
| GET | `/transacoes` | Lista todos |
| GET | `/transacoes?contaId=1` | Lista filtrando por ContaBancaria |
| GET | `/transacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/transacoes` | Cria (201) |
| PUT | `/transacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/transacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/bancos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Banco → ContaBancaria → Transacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/bancos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "codigo": 10 }'
```

```bash
# Banco
curl -X POST http://localhost:8080/bancos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "codigo": 10 }'
curl http://localhost:8080/bancos
curl http://localhost:8080/bancos/1
curl -X PUT http://localhost:8080/bancos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "codigo": 10 }'
curl -X DELETE http://localhost:8080/bancos/1

# ContaBancaria
curl -X POST http://localhost:8080/contas-bancarias -H "Content-Type: application/json" -d '{ "agencia": "Agencia exemplo", "numero": "Numero exemplo", "titular": "Titular exemplo", "saldo": 99.90, "banco": { "id": 1 } }'
curl http://localhost:8080/contas-bancarias
curl http://localhost:8080/contas-bancarias/1
curl -X PUT http://localhost:8080/contas-bancarias/1 -H "Content-Type: application/json" -d '{ "agencia": "Agencia exemplo", "numero": "Numero exemplo", "titular": "Titular exemplo", "saldo": 99.90, "banco": { "id": 1 } }'
curl -X DELETE http://localhost:8080/contas-bancarias/1

# Transacao
curl -X POST http://localhost:8080/transacoes -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "dataTransacao": "2026-03-15T10:30:00", "credito": true, "conta": { "id": 1 } }'
curl http://localhost:8080/transacoes
curl http://localhost:8080/transacoes/1
curl -X PUT http://localhost:8080/transacoes/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "dataTransacao": "2026-03-15T10:30:00", "credito": true, "conta": { "id": 1 } }'
curl -X DELETE http://localhost:8080/transacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/contasbancarias/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
