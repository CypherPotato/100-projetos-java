# 018 · Consultório Odontológico

**Categoria:** Saúde
**Lombok:** não · **Camada Service:** não

Registro de dentistas e procedimentos realizados no consultório.

## Entidades

### Dentista (tabela `dentistas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cro` | `String` |
| `especialidade` | `String` |

### Procedimento (tabela `procedimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `paciente` | `String` |
| `valor` | `BigDecimal` |
| `dataHora` | `LocalDateTime` |
| `concluido` | `Boolean` |
| `dentista` | `Dentista` (ManyToOne, coluna `dentista_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/dentistas` | Lista todos |
| GET | `/dentistas/{id}` | Busca por id (404 se não existir) |
| POST | `/dentistas` | Cria (201) |
| PUT | `/dentistas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/dentistas/{id}` | Exclui (204) |
| GET | `/procedimentos` | Lista todos |
| GET | `/procedimentos?dentistaId=1` | Lista filtrando por Dentista |
| GET | `/procedimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/procedimentos` | Cria (201) |
| PUT | `/procedimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/procedimentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/dentistas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Dentista → Procedimento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/dentistas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cro": "Cro exemplo", "especialidade": "Especialidade exemplo" }'
```

```bash
# Dentista
curl -X POST http://localhost:8080/dentistas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cro": "Cro exemplo", "especialidade": "Especialidade exemplo" }'
curl http://localhost:8080/dentistas
curl http://localhost:8080/dentistas/1
curl -X PUT http://localhost:8080/dentistas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cro": "Cro exemplo", "especialidade": "Especialidade exemplo" }'
curl -X DELETE http://localhost:8080/dentistas/1

# Procedimento
curl -X POST http://localhost:8080/procedimentos -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "paciente": "Paciente exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "concluido": true, "dentista": { "id": 1 } }'
curl http://localhost:8080/procedimentos
curl http://localhost:8080/procedimentos/1
curl -X PUT http://localhost:8080/procedimentos/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "paciente": "Paciente exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "concluido": true, "dentista": { "id": 1 } }'
curl -X DELETE http://localhost:8080/procedimentos/1
```

## Estrutura

```
src/main/java/com/exemplo/dentista/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
