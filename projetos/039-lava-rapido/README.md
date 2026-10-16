# 039 · Lava-Rápido

**Categoria:** Serviços
**Lombok:** não · **Camada Service:** sim

Registro de veículos e lavagens realizadas em um lava-rápido.

## Entidades

### Veiculo (tabela `veiculos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `modelo` | `String` |
| `cor` | `String` |

### Lavagem (tabela `lavagens`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `tipoLavagem` | `String` |
| `preco` | `BigDecimal` |
| `dataHora` | `LocalDateTime` |
| `comCera` | `Boolean` |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/veiculos` | Lista todos |
| GET | `/veiculos/{id}` | Busca por id (404 se não existir) |
| POST | `/veiculos` | Cria (201) |
| PUT | `/veiculos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/veiculos/{id}` | Exclui (204) |
| GET | `/lavagens` | Lista todos |
| GET | `/lavagens?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/lavagens/{id}` | Busca por id (404 se não existir) |
| POST | `/lavagens` | Cria (201) |
| PUT | `/lavagens/{id}` | Atualiza (404 se não existir) |
| DELETE | `/lavagens/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/veiculos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Veiculo → Lavagem.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/veiculos -ContentType "application/json" -Body '{ "placa": "ABC1D23", "modelo": "Modelo Y", "cor": "Cor exemplo" }'
```

```bash
# Veiculo
curl -X POST http://localhost:8080/veiculos -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "cor": "Cor exemplo" }'
curl http://localhost:8080/veiculos
curl http://localhost:8080/veiculos/1
curl -X PUT http://localhost:8080/veiculos/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "cor": "Cor exemplo" }'
curl -X DELETE http://localhost:8080/veiculos/1

# Lavagem
curl -X POST http://localhost:8080/lavagens -H "Content-Type: application/json" -d '{ "tipoLavagem": "TipoLavagem exemplo", "preco": 99.90, "dataHora": "2026-03-15T10:30:00", "comCera": true, "veiculo": { "id": 1 } }'
curl http://localhost:8080/lavagens
curl http://localhost:8080/lavagens/1
curl -X PUT http://localhost:8080/lavagens/1 -H "Content-Type: application/json" -d '{ "tipoLavagem": "TipoLavagem exemplo", "preco": 99.90, "dataHora": "2026-03-15T10:30:00", "comCera": true, "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/lavagens/1
```

## Estrutura

```
src/main/java/com/exemplo/lavarapido/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
