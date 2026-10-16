# 026 · Oficina Mecânica

**Categoria:** Serviços
**Lombok:** sim · **Camada Service:** não

Controle de veículos e ordens de serviço de uma oficina mecânica.

## Entidades

### Veiculo (tabela `veiculos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `modelo` | `String` |
| `anoFabricacao` | `Integer` |
| `cor` | `String` |

### OrdemServico (tabela `ordens_servico`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `valorTotal` | `BigDecimal` |
| `dataEntrada` | `LocalDate` |
| `concluida` | `Boolean` |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/veiculos` | Lista todos |
| GET | `/veiculos/{id}` | Busca por id (404 se não existir) |
| POST | `/veiculos` | Cria (201) |
| PUT | `/veiculos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/veiculos/{id}` | Exclui (204) |
| GET | `/ordens-servico` | Lista todos |
| GET | `/ordens-servico?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/ordens-servico/{id}` | Busca por id (404 se não existir) |
| POST | `/ordens-servico` | Cria (201) |
| PUT | `/ordens-servico/{id}` | Atualiza (404 se não existir) |
| DELETE | `/ordens-servico/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/veiculos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Veiculo → OrdemServico.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/veiculos -ContentType "application/json" -Body '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10, "cor": "Cor exemplo" }'
```

```bash
# Veiculo
curl -X POST http://localhost:8080/veiculos -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10, "cor": "Cor exemplo" }'
curl http://localhost:8080/veiculos
curl http://localhost:8080/veiculos/1
curl -X PUT http://localhost:8080/veiculos/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10, "cor": "Cor exemplo" }'
curl -X DELETE http://localhost:8080/veiculos/1

# OrdemServico
curl -X POST http://localhost:8080/ordens-servico -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valorTotal": 99.90, "dataEntrada": "2026-03-15", "concluida": true, "veiculo": { "id": 1 } }'
curl http://localhost:8080/ordens-servico
curl http://localhost:8080/ordens-servico/1
curl -X PUT http://localhost:8080/ordens-servico/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valorTotal": 99.90, "dataEntrada": "2026-03-15", "concluida": true, "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/ordens-servico/1
```

## Estrutura

```
src/main/java/com/exemplo/oficinamecanica/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
