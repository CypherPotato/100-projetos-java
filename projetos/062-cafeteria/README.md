# 062 · Cafeteria

**Categoria:** Alimentação
**Lombok:** não · **Camada Service:** não

Controle de fornecedores e lotes de grãos de café de uma cafeteria.

## Entidades

### Fornecedor (tabela `fornecedores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cidade` | `String` |
| `contato` | `String` |

### GraoCafe (tabela `graos_cafe`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `variedade` | `String` |
| `origem` | `String` |
| `precoKg` | `BigDecimal` |
| `estoqueKg` | `Double` |
| `fornecedor` | `Fornecedor` (ManyToOne, coluna `fornecedor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/fornecedores` | Lista todos |
| GET | `/fornecedores/{id}` | Busca por id (404 se não existir) |
| POST | `/fornecedores` | Cria (201) |
| PUT | `/fornecedores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fornecedores/{id}` | Exclui (204) |
| GET | `/graos-cafe` | Lista todos |
| GET | `/graos-cafe?fornecedorId=1` | Lista filtrando por Fornecedor |
| GET | `/graos-cafe/{id}` | Busca por id (404 se não existir) |
| POST | `/graos-cafe` | Cria (201) |
| PUT | `/graos-cafe/{id}` | Atualiza (404 se não existir) |
| DELETE | `/graos-cafe/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/fornecedores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Fornecedor → GraoCafe.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/fornecedores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cidade": "São Paulo", "contato": "Contato exemplo" }'
```

```bash
# Fornecedor
curl -X POST http://localhost:8080/fornecedores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo", "contato": "Contato exemplo" }'
curl http://localhost:8080/fornecedores
curl http://localhost:8080/fornecedores/1
curl -X PUT http://localhost:8080/fornecedores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo", "contato": "Contato exemplo" }'
curl -X DELETE http://localhost:8080/fornecedores/1

# GraoCafe
curl -X POST http://localhost:8080/graos-cafe -H "Content-Type: application/json" -d '{ "variedade": "Variedade exemplo", "origem": "Origem exemplo", "precoKg": 99.90, "estoqueKg": 12.5, "fornecedor": { "id": 1 } }'
curl http://localhost:8080/graos-cafe
curl http://localhost:8080/graos-cafe/1
curl -X PUT http://localhost:8080/graos-cafe/1 -H "Content-Type: application/json" -d '{ "variedade": "Variedade exemplo", "origem": "Origem exemplo", "precoKg": 99.90, "estoqueKg": 12.5, "fornecedor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/graos-cafe/1
```

## Estrutura

```
src/main/java/com/exemplo/cafeteria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
