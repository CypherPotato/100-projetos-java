# 098 · Estacionamento

**Categoria:** Serviços
**Lombok:** sim · **Camada Service:** não

Controle de vagas e tickets de entrada e saída de um estacionamento.

## Entidades

### Vaga (tabela `vagas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `codigo` | `String` |
| `setor` | `String` |
| `coberta` | `Boolean` |

### Ticket (tabela `tickets`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `entrada` | `LocalDateTime` |
| `saida` | `LocalDateTime` |
| `valorCobrado` | `BigDecimal` |
| `vaga` | `Vaga` (ManyToOne, coluna `vaga_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/vagas` | Lista todos |
| GET | `/vagas/{id}` | Busca por id (404 se não existir) |
| POST | `/vagas` | Cria (201) |
| PUT | `/vagas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vagas/{id}` | Exclui (204) |
| GET | `/tickets` | Lista todos |
| GET | `/tickets?vagaId=1` | Lista filtrando por Vaga |
| GET | `/tickets/{id}` | Busca por id (404 se não existir) |
| POST | `/tickets` | Cria (201) |
| PUT | `/tickets/{id}` | Atualiza (404 se não existir) |
| DELETE | `/tickets/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/vagas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Vaga → Ticket.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/vagas -ContentType "application/json" -Body '{ "codigo": "COD-001", "setor": "Setor exemplo", "coberta": true }'
```

```bash
# Vaga
curl -X POST http://localhost:8080/vagas -H "Content-Type: application/json" -d '{ "codigo": "COD-001", "setor": "Setor exemplo", "coberta": true }'
curl http://localhost:8080/vagas
curl http://localhost:8080/vagas/1
curl -X PUT http://localhost:8080/vagas/1 -H "Content-Type: application/json" -d '{ "codigo": "COD-001", "setor": "Setor exemplo", "coberta": true }'
curl -X DELETE http://localhost:8080/vagas/1

# Ticket
curl -X POST http://localhost:8080/tickets -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "entrada": "2026-03-15T10:30:00", "saida": "2026-03-15T10:30:00", "valorCobrado": 99.90, "vaga": { "id": 1 } }'
curl http://localhost:8080/tickets
curl http://localhost:8080/tickets/1
curl -X PUT http://localhost:8080/tickets/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "entrada": "2026-03-15T10:30:00", "saida": "2026-03-15T10:30:00", "valorCobrado": 99.90, "vaga": { "id": 1 } }'
curl -X DELETE http://localhost:8080/tickets/1
```

## Estrutura

```
src/main/java/com/exemplo/estacionamento/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
