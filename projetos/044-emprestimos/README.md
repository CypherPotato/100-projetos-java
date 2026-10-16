# 044 · Empréstimos

**Categoria:** Finanças
**Lombok:** sim · **Camada Service:** não

Controle de empréstimos e das parcelas a receber de cada um.

## Entidades

### Emprestimo (tabela `emprestimos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `beneficiario` | `String` |
| `valorTotal` | `BigDecimal` |
| `taxaJuros` | `Double` |
| `dataContrato` | `LocalDate` |

### Parcela (tabela `parcelas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numero` | `Integer` |
| `valor` | `BigDecimal` |
| `vencimento` | `LocalDate` |
| `paga` | `Boolean` |
| `emprestimo` | `Emprestimo` (ManyToOne, coluna `emprestimo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/emprestimos` | Lista todos |
| GET | `/emprestimos/{id}` | Busca por id (404 se não existir) |
| POST | `/emprestimos` | Cria (201) |
| PUT | `/emprestimos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/emprestimos/{id}` | Exclui (204) |
| GET | `/parcelas` | Lista todos |
| GET | `/parcelas?emprestimoId=1` | Lista filtrando por Emprestimo |
| GET | `/parcelas/{id}` | Busca por id (404 se não existir) |
| POST | `/parcelas` | Cria (201) |
| PUT | `/parcelas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/parcelas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/emprestimos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Emprestimo → Parcela.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/emprestimos -ContentType "application/json" -Body '{ "beneficiario": "Beneficiario exemplo", "valorTotal": 99.90, "taxaJuros": 12.5, "dataContrato": "2026-03-15" }'
```

```bash
# Emprestimo
curl -X POST http://localhost:8080/emprestimos -H "Content-Type: application/json" -d '{ "beneficiario": "Beneficiario exemplo", "valorTotal": 99.90, "taxaJuros": 12.5, "dataContrato": "2026-03-15" }'
curl http://localhost:8080/emprestimos
curl http://localhost:8080/emprestimos/1
curl -X PUT http://localhost:8080/emprestimos/1 -H "Content-Type: application/json" -d '{ "beneficiario": "Beneficiario exemplo", "valorTotal": 99.90, "taxaJuros": 12.5, "dataContrato": "2026-03-15" }'
curl -X DELETE http://localhost:8080/emprestimos/1

# Parcela
curl -X POST http://localhost:8080/parcelas -H "Content-Type: application/json" -d '{ "numero": 10, "valor": 99.90, "vencimento": "2026-03-15", "paga": true, "emprestimo": { "id": 1 } }'
curl http://localhost:8080/parcelas
curl http://localhost:8080/parcelas/1
curl -X PUT http://localhost:8080/parcelas/1 -H "Content-Type: application/json" -d '{ "numero": 10, "valor": 99.90, "vencimento": "2026-03-15", "paga": true, "emprestimo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/parcelas/1
```

## Estrutura

```
src/main/java/com/exemplo/emprestimos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
