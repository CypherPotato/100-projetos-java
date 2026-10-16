# 086 · Fazenda

**Categoria:** Agro e Logística
**Lombok:** sim · **Camada Service:** não

Controle de talhões e plantios de uma propriedade rural.

## Entidades

### Talhao (tabela `talhoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `areaHectares` | `Double` |
| `tipoSolo` | `String` |

### Plantio (tabela `plantios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `cultura` | `String` |
| `dataPlantio` | `LocalDate` |
| `dataColheitaPrevista` | `LocalDate` |
| `sacasEstimadas` | `Integer` |
| `talhao` | `Talhao` (ManyToOne, coluna `talhao_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/talhoes` | Lista todos |
| GET | `/talhoes/{id}` | Busca por id (404 se não existir) |
| POST | `/talhoes` | Cria (201) |
| PUT | `/talhoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/talhoes/{id}` | Exclui (204) |
| GET | `/plantios` | Lista todos |
| GET | `/plantios?talhaoId=1` | Lista filtrando por Talhao |
| GET | `/plantios/{id}` | Busca por id (404 se não existir) |
| POST | `/plantios` | Cria (201) |
| PUT | `/plantios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/plantios/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/talhoes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Talhao → Plantio.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/talhoes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "areaHectares": 12.5, "tipoSolo": "TipoSolo exemplo" }'
```

```bash
# Talhao
curl -X POST http://localhost:8080/talhoes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "areaHectares": 12.5, "tipoSolo": "TipoSolo exemplo" }'
curl http://localhost:8080/talhoes
curl http://localhost:8080/talhoes/1
curl -X PUT http://localhost:8080/talhoes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "areaHectares": 12.5, "tipoSolo": "TipoSolo exemplo" }'
curl -X DELETE http://localhost:8080/talhoes/1

# Plantio
curl -X POST http://localhost:8080/plantios -H "Content-Type: application/json" -d '{ "cultura": "Cultura exemplo", "dataPlantio": "2026-03-15", "dataColheitaPrevista": "2026-03-15", "sacasEstimadas": 10, "talhao": { "id": 1 } }'
curl http://localhost:8080/plantios
curl http://localhost:8080/plantios/1
curl -X PUT http://localhost:8080/plantios/1 -H "Content-Type: application/json" -d '{ "cultura": "Cultura exemplo", "dataPlantio": "2026-03-15", "dataColheitaPrevista": "2026-03-15", "sacasEstimadas": 10, "talhao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/plantios/1
```

## Estrutura

```
src/main/java/com/exemplo/fazenda/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
