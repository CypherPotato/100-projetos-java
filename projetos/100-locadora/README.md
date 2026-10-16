# 100 · Locadora de Veículos

**Categoria:** Serviços
**Lombok:** sim · **Camada Service:** não

Controle da frota e das locações de uma locadora de veículos.

## Entidades

### Veiculo (tabela `veiculos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `modelo` | `String` |
| `anoModelo` | `Integer` |
| `diaria` | `BigDecimal` |

### Locacao (tabela `locacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `cliente` | `String` |
| `dataRetirada` | `LocalDateTime` |
| `dataDevolucao` | `LocalDateTime` |
| `valorTotal` | `BigDecimal` |
| `devolvido` | `Boolean` |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/veiculos` | Lista todos |
| GET | `/veiculos/{id}` | Busca por id (404 se não existir) |
| POST | `/veiculos` | Cria (201) |
| PUT | `/veiculos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/veiculos/{id}` | Exclui (204) |
| GET | `/locacoes` | Lista todos |
| GET | `/locacoes?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/locacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/locacoes` | Cria (201) |
| PUT | `/locacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/locacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/veiculos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Veiculo → Locacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/veiculos -ContentType "application/json" -Body '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoModelo": 10, "diaria": 99.90 }'
```

```bash
# Veiculo
curl -X POST http://localhost:8080/veiculos -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoModelo": 10, "diaria": 99.90 }'
curl http://localhost:8080/veiculos
curl http://localhost:8080/veiculos/1
curl -X PUT http://localhost:8080/veiculos/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoModelo": 10, "diaria": 99.90 }'
curl -X DELETE http://localhost:8080/veiculos/1

# Locacao
curl -X POST http://localhost:8080/locacoes -H "Content-Type: application/json" -d '{ "cliente": "Cliente exemplo", "dataRetirada": "2026-03-15T10:30:00", "dataDevolucao": "2026-03-15T10:30:00", "valorTotal": 99.90, "devolvido": true, "veiculo": { "id": 1 } }'
curl http://localhost:8080/locacoes
curl http://localhost:8080/locacoes/1
curl -X PUT http://localhost:8080/locacoes/1 -H "Content-Type: application/json" -d '{ "cliente": "Cliente exemplo", "dataRetirada": "2026-03-15T10:30:00", "dataDevolucao": "2026-03-15T10:30:00", "valorTotal": 99.90, "devolvido": true, "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/locacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/locadora/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
