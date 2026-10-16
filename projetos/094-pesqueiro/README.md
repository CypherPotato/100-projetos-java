# 094 · Pesqueiro

**Categoria:** Agro e Logística
**Lombok:** sim · **Camada Service:** não

Controle de tanques de peixes e das despescas realizadas.

## Entidades

### Tanque (tabela `tanques`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `volumeLitros` | `Double` |
| `tipoPeixe` | `String` |

### Despesca (tabela `despescas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `pesoKg` | `Double` |
| `quantidade` | `Integer` |
| `dataDespesca` | `LocalDateTime` |
| `valorTotal` | `BigDecimal` |
| `tanque` | `Tanque` (ManyToOne, coluna `tanque_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/tanques` | Lista todos |
| GET | `/tanques/{id}` | Busca por id (404 se não existir) |
| POST | `/tanques` | Cria (201) |
| PUT | `/tanques/{id}` | Atualiza (404 se não existir) |
| DELETE | `/tanques/{id}` | Exclui (204) |
| GET | `/despescas` | Lista todos |
| GET | `/despescas?tanqueId=1` | Lista filtrando por Tanque |
| GET | `/despescas/{id}` | Busca por id (404 se não existir) |
| POST | `/despescas` | Cria (201) |
| PUT | `/despescas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/despescas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/tanques

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Tanque → Despesca.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/tanques -ContentType "application/json" -Body '{ "nome": "Maria Silva", "volumeLitros": 12.5, "tipoPeixe": "TipoPeixe exemplo" }'
```

```bash
# Tanque
curl -X POST http://localhost:8080/tanques -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "volumeLitros": 12.5, "tipoPeixe": "TipoPeixe exemplo" }'
curl http://localhost:8080/tanques
curl http://localhost:8080/tanques/1
curl -X PUT http://localhost:8080/tanques/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "volumeLitros": 12.5, "tipoPeixe": "TipoPeixe exemplo" }'
curl -X DELETE http://localhost:8080/tanques/1

# Despesca
curl -X POST http://localhost:8080/despescas -H "Content-Type: application/json" -d '{ "pesoKg": 12.5, "quantidade": 10, "dataDespesca": "2026-03-15T10:30:00", "valorTotal": 99.90, "tanque": { "id": 1 } }'
curl http://localhost:8080/despescas
curl http://localhost:8080/despescas/1
curl -X PUT http://localhost:8080/despescas/1 -H "Content-Type: application/json" -d '{ "pesoKg": 12.5, "quantidade": 10, "dataDespesca": "2026-03-15T10:30:00", "valorTotal": 99.90, "tanque": { "id": 1 } }'
curl -X DELETE http://localhost:8080/despescas/1
```

## Estrutura

```
src/main/java/com/exemplo/pesqueiro/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
