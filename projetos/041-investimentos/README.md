# 041 · Investimentos

**Categoria:** Finanças
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Organização de carteiras de investimento e dos ativos que as compõem.

## Entidades

### Carteira (tabela `carteiras`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `objetivo` | `String` |
| `dataCriacao` | `LocalDate` |

### Ativo (tabela `ativos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `ticker` | `String` |
| `nome` | `String` |
| `quantidade` | `Integer` |
| `precoMedio` | `BigDecimal` |
| `carteira` | `Carteira` (ManyToOne, coluna `carteira_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/carteiras` | Lista todos |
| GET | `/carteiras/{id}` | Busca por id (404 se não existir) |
| POST | `/carteiras` | Cria (201) |
| PUT | `/carteiras/{id}` | Atualiza (404 se não existir) |
| DELETE | `/carteiras/{id}` | Exclui (204) |
| GET | `/ativos` | Lista todos |
| GET | `/ativos?carteiraId=1` | Lista filtrando por Carteira |
| GET | `/ativos/{id}` | Busca por id (404 se não existir) |
| POST | `/ativos` | Cria (201) |
| PUT | `/ativos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/ativos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/carteiras

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Carteira → Ativo.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/carteiras -ContentType "application/json" -Body '{ "nome": "Maria Silva", "objetivo": "Objetivo exemplo", "dataCriacao": "2026-03-15" }'
```

```bash
# Carteira
curl -X POST http://localhost:8080/carteiras -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "objetivo": "Objetivo exemplo", "dataCriacao": "2026-03-15" }'
curl http://localhost:8080/carteiras
curl http://localhost:8080/carteiras/1
curl -X PUT http://localhost:8080/carteiras/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "objetivo": "Objetivo exemplo", "dataCriacao": "2026-03-15" }'
curl -X DELETE http://localhost:8080/carteiras/1

# Ativo
curl -X POST http://localhost:8080/ativos -H "Content-Type: application/json" -d '{ "ticker": "Ticker exemplo", "nome": "Maria Silva", "quantidade": 10, "precoMedio": 99.90, "carteira": { "id": 1 } }'
curl http://localhost:8080/ativos
curl http://localhost:8080/ativos/1
curl -X PUT http://localhost:8080/ativos/1 -H "Content-Type: application/json" -d '{ "ticker": "Ticker exemplo", "nome": "Maria Silva", "quantidade": 10, "precoMedio": 99.90, "carteira": { "id": 1 } }'
curl -X DELETE http://localhost:8080/ativos/1
```

## Estrutura

```
src/main/java/com/exemplo/investimentos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
