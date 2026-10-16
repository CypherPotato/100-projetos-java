# 097 · Pousada

**Categoria:** Serviços
**Lombok:** não · **Camada Service:** não

Controle de chalés e hospedagens de uma pousada.

## Entidades

### Chale (tabela `chales`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `capacidade` | `Integer` |
| `diaria` | `BigDecimal` |
| `cafeIncluso` | `Boolean` |

### Hospedagem (tabela `hospedagens`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nomeHospede` | `String` |
| `telefone` | `String` |
| `dataChegada` | `LocalDate` |
| `noites` | `Integer` |
| `chale` | `Chale` (ManyToOne, coluna `chale_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/chales` | Lista todos |
| GET | `/chales/{id}` | Busca por id (404 se não existir) |
| POST | `/chales` | Cria (201) |
| PUT | `/chales/{id}` | Atualiza (404 se não existir) |
| DELETE | `/chales/{id}` | Exclui (204) |
| GET | `/hospedagens` | Lista todos |
| GET | `/hospedagens?chaleId=1` | Lista filtrando por Chale |
| GET | `/hospedagens/{id}` | Busca por id (404 se não existir) |
| POST | `/hospedagens` | Cria (201) |
| PUT | `/hospedagens/{id}` | Atualiza (404 se não existir) |
| DELETE | `/hospedagens/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/chales

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Chale → Hospedagem.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/chales -ContentType "application/json" -Body '{ "nome": "Maria Silva", "capacidade": 10, "diaria": 99.90, "cafeIncluso": true }'
```

```bash
# Chale
curl -X POST http://localhost:8080/chales -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidade": 10, "diaria": 99.90, "cafeIncluso": true }'
curl http://localhost:8080/chales
curl http://localhost:8080/chales/1
curl -X PUT http://localhost:8080/chales/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidade": 10, "diaria": 99.90, "cafeIncluso": true }'
curl -X DELETE http://localhost:8080/chales/1

# Hospedagem
curl -X POST http://localhost:8080/hospedagens -H "Content-Type: application/json" -d '{ "nomeHospede": "NomeHospede exemplo", "telefone": "(11) 99999-0000", "dataChegada": "2026-03-15", "noites": 10, "chale": { "id": 1 } }'
curl http://localhost:8080/hospedagens
curl http://localhost:8080/hospedagens/1
curl -X PUT http://localhost:8080/hospedagens/1 -H "Content-Type: application/json" -d '{ "nomeHospede": "NomeHospede exemplo", "telefone": "(11) 99999-0000", "dataChegada": "2026-03-15", "noites": 10, "chale": { "id": 1 } }'
curl -X DELETE http://localhost:8080/hospedagens/1
```

## Estrutura

```
src/main/java/com/exemplo/pousada/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
