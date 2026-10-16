# 051 · Cinema

**Categoria:** Lazer e Cultura
**Lombok:** sim · **Camada Service:** não

Controle de filmes em cartaz e das sessões de um cinema.

## Entidades

### Filme (tabela `filmes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `genero` | `String` |
| `duracaoMinutos` | `Integer` |
| `classificacao` | `Integer` |

### Sessao (tabela `sessoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `horario` | `LocalDateTime` |
| `sala` | `String` |
| `preco` | `BigDecimal` |
| `legendado` | `Boolean` |
| `filme` | `Filme` (ManyToOne, coluna `filme_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/filmes` | Lista todos |
| GET | `/filmes/{id}` | Busca por id (404 se não existir) |
| POST | `/filmes` | Cria (201) |
| PUT | `/filmes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/filmes/{id}` | Exclui (204) |
| GET | `/sessoes` | Lista todos |
| GET | `/sessoes?filmeId=1` | Lista filtrando por Filme |
| GET | `/sessoes/{id}` | Busca por id (404 se não existir) |
| POST | `/sessoes` | Cria (201) |
| PUT | `/sessoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/sessoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/filmes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Filme → Sessao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/filmes -ContentType "application/json" -Body '{ "titulo": "Exemplo", "genero": "Genero exemplo", "duracaoMinutos": 10, "classificacao": 10 }'
```

```bash
# Filme
curl -X POST http://localhost:8080/filmes -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "genero": "Genero exemplo", "duracaoMinutos": 10, "classificacao": 10 }'
curl http://localhost:8080/filmes
curl http://localhost:8080/filmes/1
curl -X PUT http://localhost:8080/filmes/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "genero": "Genero exemplo", "duracaoMinutos": 10, "classificacao": 10 }'
curl -X DELETE http://localhost:8080/filmes/1

# Sessao
curl -X POST http://localhost:8080/sessoes -H "Content-Type: application/json" -d '{ "horario": "2026-03-15T10:30:00", "sala": "Sala exemplo", "preco": 99.90, "legendado": true, "filme": { "id": 1 } }'
curl http://localhost:8080/sessoes
curl http://localhost:8080/sessoes/1
curl -X PUT http://localhost:8080/sessoes/1 -H "Content-Type: application/json" -d '{ "horario": "2026-03-15T10:30:00", "sala": "Sala exemplo", "preco": 99.90, "legendado": true, "filme": { "id": 1 } }'
curl -X DELETE http://localhost:8080/sessoes/1
```

## Estrutura

```
src/main/java/com/exemplo/cinema/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
