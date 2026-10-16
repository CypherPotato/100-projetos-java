# 036 · Assistência Técnica

**Categoria:** Serviços
**Lombok:** sim · **Camada Service:** sim

Controle de clientes, aparelhos e reparos de uma assistência técnica.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `email` | `String` |

### Aparelho (tabela `aparelhos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `tipo` | `String` |
| `marca` | `String` |
| `modelo` | `String` |
| `numeroSerie` | `String` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

### Reparo (tabela `reparos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `defeito` | `String` |
| `valorOrcado` | `BigDecimal` |
| `dataEntrada` | `LocalDate` |
| `consertado` | `Boolean` |
| `aparelho` | `Aparelho` (ManyToOne, coluna `aparelho_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/aparelhos` | Lista todos |
| GET | `/aparelhos?clienteId=1` | Lista filtrando por Cliente |
| GET | `/aparelhos/{id}` | Busca por id (404 se não existir) |
| POST | `/aparelhos` | Cria (201) |
| PUT | `/aparelhos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aparelhos/{id}` | Exclui (204) |
| GET | `/reparos` | Lista todos |
| GET | `/reparos?aparelhoId=1` | Lista filtrando por Aparelho |
| GET | `/reparos/{id}` | Busca por id (404 se não existir) |
| POST | `/reparos` | Cria (201) |
| PUT | `/reparos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/reparos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Aparelho → Reparo.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/clientes/1

# Aparelho
curl -X POST http://localhost:8080/aparelhos -H "Content-Type: application/json" -d '{ "tipo": "Tipo exemplo", "marca": "Marca X", "modelo": "Modelo Y", "numeroSerie": "NumeroSerie exemplo", "cliente": { "id": 1 } }'
curl http://localhost:8080/aparelhos
curl http://localhost:8080/aparelhos/1
curl -X PUT http://localhost:8080/aparelhos/1 -H "Content-Type: application/json" -d '{ "tipo": "Tipo exemplo", "marca": "Marca X", "modelo": "Modelo Y", "numeroSerie": "NumeroSerie exemplo", "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aparelhos/1

# Reparo
curl -X POST http://localhost:8080/reparos -H "Content-Type: application/json" -d '{ "defeito": "Defeito exemplo", "valorOrcado": 99.90, "dataEntrada": "2026-03-15", "consertado": true, "aparelho": { "id": 1 } }'
curl http://localhost:8080/reparos
curl http://localhost:8080/reparos/1
curl -X PUT http://localhost:8080/reparos/1 -H "Content-Type: application/json" -d '{ "defeito": "Defeito exemplo", "valorOrcado": 99.90, "dataEntrada": "2026-03-15", "consertado": true, "aparelho": { "id": 1 } }'
curl -X DELETE http://localhost:8080/reparos/1
```

## Estrutura

```
src/main/java/com/exemplo/assistenciatecnica/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
