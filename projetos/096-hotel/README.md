# 096 · Hotel

**Categoria:** Serviços
**Lombok:** sim · **Camada Service:** sim

Gestão de quartos, hóspedes e reservas de um hotel.

## Entidades

### Quarto (tabela `quartos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numero` | `Integer` |
| `tipo` | `String` |
| `diaria` | `BigDecimal` |
| `disponivel` | `Boolean` |

### Hospede (tabela `hospedes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `documento` | `String` |
| `telefone` | `String` |

### Reserva (tabela `reservas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataEntrada` | `LocalDate` |
| `dataSaida` | `LocalDate` |
| `valorTotal` | `BigDecimal` |
| `quarto` | `Quarto` (ManyToOne, coluna `quarto_id`) |
| `hospede` | `Hospede` (ManyToOne, coluna `hospede_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/quartos` | Lista todos |
| GET | `/quartos/{id}` | Busca por id (404 se não existir) |
| POST | `/quartos` | Cria (201) |
| PUT | `/quartos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/quartos/{id}` | Exclui (204) |
| GET | `/hospedes` | Lista todos |
| GET | `/hospedes/{id}` | Busca por id (404 se não existir) |
| POST | `/hospedes` | Cria (201) |
| PUT | `/hospedes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/hospedes/{id}` | Exclui (204) |
| GET | `/reservas` | Lista todos |
| GET | `/reservas?quartoId=1` | Lista filtrando por Quarto |
| GET | `/reservas?hospedeId=1` | Lista filtrando por Hospede |
| GET | `/reservas/{id}` | Busca por id (404 se não existir) |
| POST | `/reservas` | Cria (201) |
| PUT | `/reservas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/reservas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/quartos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Quarto → Hospede → Reserva.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/quartos -ContentType "application/json" -Body '{ "numero": 10, "tipo": "Tipo exemplo", "diaria": 99.90, "disponivel": true }'
```

```bash
# Quarto
curl -X POST http://localhost:8080/quartos -H "Content-Type: application/json" -d '{ "numero": 10, "tipo": "Tipo exemplo", "diaria": 99.90, "disponivel": true }'
curl http://localhost:8080/quartos
curl http://localhost:8080/quartos/1
curl -X PUT http://localhost:8080/quartos/1 -H "Content-Type: application/json" -d '{ "numero": 10, "tipo": "Tipo exemplo", "diaria": 99.90, "disponivel": true }'
curl -X DELETE http://localhost:8080/quartos/1

# Hospede
curl -X POST http://localhost:8080/hospedes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "documento": "Documento exemplo", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/hospedes
curl http://localhost:8080/hospedes/1
curl -X PUT http://localhost:8080/hospedes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "documento": "Documento exemplo", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/hospedes/1

# Reserva
curl -X POST http://localhost:8080/reservas -H "Content-Type: application/json" -d '{ "dataEntrada": "2026-03-15", "dataSaida": "2026-03-15", "valorTotal": 99.90, "quarto": { "id": 1 }, "hospede": { "id": 1 } }'
curl http://localhost:8080/reservas
curl http://localhost:8080/reservas/1
curl -X PUT http://localhost:8080/reservas/1 -H "Content-Type: application/json" -d '{ "dataEntrada": "2026-03-15", "dataSaida": "2026-03-15", "valorTotal": 99.90, "quarto": { "id": 1 }, "hospede": { "id": 1 } }'
curl -X DELETE http://localhost:8080/reservas/1
```

## Estrutura

```
src/main/java/com/exemplo/hotel/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
