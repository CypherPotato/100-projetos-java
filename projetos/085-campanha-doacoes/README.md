# 085 · Campanha de Doações

**Categoria:** Comunidade
**Lombok:** não · **Camada Service:** não

Acompanhamento de campanhas de arrecadação, doadores e doações recebidas.

## Entidades

### Campanha (tabela `campanhas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `metaValor` | `BigDecimal` |
| `dataInicio` | `LocalDate` |
| `dataFim` | `LocalDate` |
| `ativa` | `Boolean` |

### Doador (tabela `doadores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `telefone` | `String` |

### Doacao (tabela `doacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `valor` | `BigDecimal` |
| `dataDoacao` | `LocalDateTime` |
| `anonima` | `Boolean` |
| `campanha` | `Campanha` (ManyToOne, coluna `campanha_id`) |
| `doador` | `Doador` (ManyToOne, coluna `doador_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/campanhas` | Lista todos |
| GET | `/campanhas/{id}` | Busca por id (404 se não existir) |
| POST | `/campanhas` | Cria (201) |
| PUT | `/campanhas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/campanhas/{id}` | Exclui (204) |
| GET | `/doadores` | Lista todos |
| GET | `/doadores/{id}` | Busca por id (404 se não existir) |
| POST | `/doadores` | Cria (201) |
| PUT | `/doadores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/doadores/{id}` | Exclui (204) |
| GET | `/doacoes` | Lista todos |
| GET | `/doacoes?campanhaId=1` | Lista filtrando por Campanha |
| GET | `/doacoes?doadorId=1` | Lista filtrando por Doador |
| GET | `/doacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/doacoes` | Cria (201) |
| PUT | `/doacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/doacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/campanhas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Campanha → Doador → Doacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/campanhas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "metaValor": 99.90, "dataInicio": "2026-03-15", "dataFim": "2026-03-15", "ativa": true }'
```

```bash
# Campanha
curl -X POST http://localhost:8080/campanhas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "metaValor": 99.90, "dataInicio": "2026-03-15", "dataFim": "2026-03-15", "ativa": true }'
curl http://localhost:8080/campanhas
curl http://localhost:8080/campanhas/1
curl -X PUT http://localhost:8080/campanhas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "metaValor": 99.90, "dataInicio": "2026-03-15", "dataFim": "2026-03-15", "ativa": true }'
curl -X DELETE http://localhost:8080/campanhas/1

# Doador
curl -X POST http://localhost:8080/doadores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/doadores
curl http://localhost:8080/doadores/1
curl -X PUT http://localhost:8080/doadores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/doadores/1

# Doacao
curl -X POST http://localhost:8080/doacoes -H "Content-Type: application/json" -d '{ "valor": 99.90, "dataDoacao": "2026-03-15T10:30:00", "anonima": true, "campanha": { "id": 1 }, "doador": { "id": 1 } }'
curl http://localhost:8080/doacoes
curl http://localhost:8080/doacoes/1
curl -X PUT http://localhost:8080/doacoes/1 -H "Content-Type: application/json" -d '{ "valor": 99.90, "dataDoacao": "2026-03-15T10:30:00", "anonima": true, "campanha": { "id": 1 }, "doador": { "id": 1 } }'
curl -X DELETE http://localhost:8080/doacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/campanhadoacoes/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
