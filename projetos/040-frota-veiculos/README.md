# 040 · Frota de Veículos

**Categoria:** Gestão
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Gerenciamento da frota de uma empresa com manutenções e abastecimentos.

## Entidades

### Veiculo (tabela `veiculos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `modelo` | `String` |
| `anoFabricacao` | `Integer` |
| `quilometragem` | `Integer` |

### Manutencao (tabela `manutencoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `custo` | `BigDecimal` |
| `dataManutencao` | `LocalDate` |
| `preventiva` | `Boolean` |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

### Abastecimento (tabela `abastecimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `litros` | `Double` |
| `valorTotal` | `BigDecimal` |
| `dataAbastecimento` | `LocalDate` |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/veiculos` | Lista todos |
| GET | `/veiculos/{id}` | Busca por id (404 se não existir) |
| POST | `/veiculos` | Cria (201) |
| PUT | `/veiculos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/veiculos/{id}` | Exclui (204) |
| GET | `/manutencoes` | Lista todos |
| GET | `/manutencoes?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/manutencoes/{id}` | Busca por id (404 se não existir) |
| POST | `/manutencoes` | Cria (201) |
| PUT | `/manutencoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/manutencoes/{id}` | Exclui (204) |
| GET | `/abastecimentos` | Lista todos |
| GET | `/abastecimentos?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/abastecimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/abastecimentos` | Cria (201) |
| PUT | `/abastecimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/abastecimentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/veiculos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Veiculo → Manutencao → Abastecimento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/veiculos -ContentType "application/json" -Body '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10, "quilometragem": 10 }'
```

```bash
# Veiculo
curl -X POST http://localhost:8080/veiculos -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10, "quilometragem": 10 }'
curl http://localhost:8080/veiculos
curl http://localhost:8080/veiculos/1
curl -X PUT http://localhost:8080/veiculos/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10, "quilometragem": 10 }'
curl -X DELETE http://localhost:8080/veiculos/1

# Manutencao
curl -X POST http://localhost:8080/manutencoes -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "custo": 99.90, "dataManutencao": "2026-03-15", "preventiva": true, "veiculo": { "id": 1 } }'
curl http://localhost:8080/manutencoes
curl http://localhost:8080/manutencoes/1
curl -X PUT http://localhost:8080/manutencoes/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "custo": 99.90, "dataManutencao": "2026-03-15", "preventiva": true, "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/manutencoes/1

# Abastecimento
curl -X POST http://localhost:8080/abastecimentos -H "Content-Type: application/json" -d '{ "litros": 12.5, "valorTotal": 99.90, "dataAbastecimento": "2026-03-15", "veiculo": { "id": 1 } }'
curl http://localhost:8080/abastecimentos
curl http://localhost:8080/abastecimentos/1
curl -X PUT http://localhost:8080/abastecimentos/1 -H "Content-Type: application/json" -d '{ "litros": 12.5, "valorTotal": 99.90, "dataAbastecimento": "2026-03-15", "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/abastecimentos/1
```

## Estrutura

```
src/main/java/com/exemplo/frotaveiculos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
