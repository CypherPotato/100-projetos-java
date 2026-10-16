# 063 · Padaria

**Categoria:** Alimentação
**Lombok:** sim · **Camada Service:** sim

Registro dos padeiros e das fornadas produzidas por dia.

## Entidades

### Padeiro (tabela `padeiros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `turno` | `String` |
| `dataAdmissao` | `LocalDate` |

### Fornada (tabela `fornadas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `produto` | `String` |
| `quantidade` | `Integer` |
| `precoUnitario` | `BigDecimal` |
| `dataHora` | `LocalDateTime` |
| `padeiro` | `Padeiro` (ManyToOne, coluna `padeiro_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/padeiros` | Lista todos |
| GET | `/padeiros/{id}` | Busca por id (404 se não existir) |
| POST | `/padeiros` | Cria (201) |
| PUT | `/padeiros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/padeiros/{id}` | Exclui (204) |
| GET | `/fornadas` | Lista todos |
| GET | `/fornadas?padeiroId=1` | Lista filtrando por Padeiro |
| GET | `/fornadas/{id}` | Busca por id (404 se não existir) |
| POST | `/fornadas` | Cria (201) |
| PUT | `/fornadas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fornadas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/padeiros

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Padeiro → Fornada.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/padeiros -ContentType "application/json" -Body '{ "nome": "Maria Silva", "turno": "Turno exemplo", "dataAdmissao": "2026-03-15" }'
```

```bash
# Padeiro
curl -X POST http://localhost:8080/padeiros -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "turno": "Turno exemplo", "dataAdmissao": "2026-03-15" }'
curl http://localhost:8080/padeiros
curl http://localhost:8080/padeiros/1
curl -X PUT http://localhost:8080/padeiros/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "turno": "Turno exemplo", "dataAdmissao": "2026-03-15" }'
curl -X DELETE http://localhost:8080/padeiros/1

# Fornada
curl -X POST http://localhost:8080/fornadas -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "quantidade": 10, "precoUnitario": 99.90, "dataHora": "2026-03-15T10:30:00", "padeiro": { "id": 1 } }'
curl http://localhost:8080/fornadas
curl http://localhost:8080/fornadas/1
curl -X PUT http://localhost:8080/fornadas/1 -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "quantidade": 10, "precoUnitario": 99.90, "dataHora": "2026-03-15T10:30:00", "padeiro": { "id": 1 } }'
curl -X DELETE http://localhost:8080/fornadas/1
```

## Estrutura

```
src/main/java/com/exemplo/padaria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
