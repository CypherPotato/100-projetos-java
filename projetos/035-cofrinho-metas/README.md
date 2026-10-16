# 035 · Cofrinho de Metas

**Categoria:** Finanças
**Lombok:** não · **Camada Service:** não

Definição de metas de economia e registro dos depósitos feitos em cada uma.

## Entidades

### Meta (tabela `metas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `valorAlvo` | `BigDecimal` |
| `prazo` | `LocalDate` |
| `alcancada` | `Boolean` |

### Deposito (tabela `depositos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `valor` | `BigDecimal` |
| `dataDeposito` | `LocalDate` |
| `observacao` | `String` |
| `meta` | `Meta` (ManyToOne, coluna `meta_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/metas` | Lista todos |
| GET | `/metas/{id}` | Busca por id (404 se não existir) |
| POST | `/metas` | Cria (201) |
| PUT | `/metas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/metas/{id}` | Exclui (204) |
| GET | `/depositos` | Lista todos |
| GET | `/depositos?metaId=1` | Lista filtrando por Meta |
| GET | `/depositos/{id}` | Busca por id (404 se não existir) |
| POST | `/depositos` | Cria (201) |
| PUT | `/depositos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/depositos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/metas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Meta → Deposito.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/metas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "valorAlvo": 99.90, "prazo": "2026-03-15", "alcancada": true }'
```

```bash
# Meta
curl -X POST http://localhost:8080/metas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "valorAlvo": 99.90, "prazo": "2026-03-15", "alcancada": true }'
curl http://localhost:8080/metas
curl http://localhost:8080/metas/1
curl -X PUT http://localhost:8080/metas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "valorAlvo": 99.90, "prazo": "2026-03-15", "alcancada": true }'
curl -X DELETE http://localhost:8080/metas/1

# Deposito
curl -X POST http://localhost:8080/depositos -H "Content-Type: application/json" -d '{ "valor": 99.90, "dataDeposito": "2026-03-15", "observacao": "Sem observações", "meta": { "id": 1 } }'
curl http://localhost:8080/depositos
curl http://localhost:8080/depositos/1
curl -X PUT http://localhost:8080/depositos/1 -H "Content-Type: application/json" -d '{ "valor": 99.90, "dataDeposito": "2026-03-15", "observacao": "Sem observações", "meta": { "id": 1 } }'
curl -X DELETE http://localhost:8080/depositos/1
```

## Estrutura

```
src/main/java/com/exemplo/cofrinhometas/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
