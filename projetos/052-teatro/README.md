# 052 · Teatro

**Categoria:** Lazer e Cultura
**Lombok:** não · **Camada Service:** não

Cadastro de peças teatrais e de suas apresentações.

## Entidades

### Peca (tabela `pecas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `diretor` | `String` |
| `duracaoMinutos` | `Integer` |

### Apresentacao (tabela `apresentacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `precoIngresso` | `BigDecimal` |
| `lugaresDisponiveis` | `Integer` |
| `peca` | `Peca` (ManyToOne, coluna `peca_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/pecas` | Lista todos |
| GET | `/pecas/{id}` | Busca por id (404 se não existir) |
| POST | `/pecas` | Cria (201) |
| PUT | `/pecas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pecas/{id}` | Exclui (204) |
| GET | `/apresentacoes` | Lista todos |
| GET | `/apresentacoes?pecaId=1` | Lista filtrando por Peca |
| GET | `/apresentacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/apresentacoes` | Cria (201) |
| PUT | `/apresentacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/apresentacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/pecas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Peca → Apresentacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/pecas -ContentType "application/json" -Body '{ "titulo": "Exemplo", "diretor": "Diretor exemplo", "duracaoMinutos": 10 }'
```

```bash
# Peca
curl -X POST http://localhost:8080/pecas -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "diretor": "Diretor exemplo", "duracaoMinutos": 10 }'
curl http://localhost:8080/pecas
curl http://localhost:8080/pecas/1
curl -X PUT http://localhost:8080/pecas/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "diretor": "Diretor exemplo", "duracaoMinutos": 10 }'
curl -X DELETE http://localhost:8080/pecas/1

# Apresentacao
curl -X POST http://localhost:8080/apresentacoes -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "precoIngresso": 99.90, "lugaresDisponiveis": 10, "peca": { "id": 1 } }'
curl http://localhost:8080/apresentacoes
curl http://localhost:8080/apresentacoes/1
curl -X PUT http://localhost:8080/apresentacoes/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "precoIngresso": 99.90, "lugaresDisponiveis": 10, "peca": { "id": 1 } }'
curl -X DELETE http://localhost:8080/apresentacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/teatro/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
