# 029 · Contas a Pagar

**Categoria:** Finanças
**Lombok:** não · **Camada Service:** não

Controle de fornecedores e das contas a pagar com seus vencimentos.

## Entidades

### Fornecedor (tabela `fornecedores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cnpj` | `String` |
| `telefone` | `String` |

### ContaPagar (tabela `contas_pagar`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `valor` | `BigDecimal` |
| `vencimento` | `LocalDate` |
| `paga` | `Boolean` |
| `fornecedor` | `Fornecedor` (ManyToOne, coluna `fornecedor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/fornecedores` | Lista todos |
| GET | `/fornecedores/{id}` | Busca por id (404 se não existir) |
| POST | `/fornecedores` | Cria (201) |
| PUT | `/fornecedores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fornecedores/{id}` | Exclui (204) |
| GET | `/contas-pagar` | Lista todos |
| GET | `/contas-pagar?fornecedorId=1` | Lista filtrando por Fornecedor |
| GET | `/contas-pagar/{id}` | Busca por id (404 se não existir) |
| POST | `/contas-pagar` | Cria (201) |
| PUT | `/contas-pagar/{id}` | Atualiza (404 se não existir) |
| DELETE | `/contas-pagar/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/fornecedores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Fornecedor → ContaPagar.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/fornecedores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cnpj": "12.345.678/0001-00", "telefone": "(11) 99999-0000" }'
```

```bash
# Fornecedor
curl -X POST http://localhost:8080/fornecedores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cnpj": "12.345.678/0001-00", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/fornecedores
curl http://localhost:8080/fornecedores/1
curl -X PUT http://localhost:8080/fornecedores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cnpj": "12.345.678/0001-00", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/fornecedores/1

# ContaPagar
curl -X POST http://localhost:8080/contas-pagar -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "vencimento": "2026-03-15", "paga": true, "fornecedor": { "id": 1 } }'
curl http://localhost:8080/contas-pagar
curl http://localhost:8080/contas-pagar/1
curl -X PUT http://localhost:8080/contas-pagar/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "vencimento": "2026-03-15", "paga": true, "fornecedor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/contas-pagar/1
```

## Estrutura

```
src/main/java/com/exemplo/contasapagar/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
