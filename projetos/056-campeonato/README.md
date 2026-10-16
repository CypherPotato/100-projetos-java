# 056 · Campeonato de Futebol

**Categoria:** Lazer e Cultura
**Lombok:** não · **Camada Service:** sim

Cadastro de times e jogadores de um campeonato de futebol amador.

## Entidades

### Time (tabela `times`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cidade` | `String` |
| `anoFundacao` | `Integer` |

### Jogador (tabela `jogadores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `posicao` | `String` |
| `numeroCamisa` | `Integer` |
| `gols` | `Integer` |
| `equipe` | `Time` (ManyToOne, coluna `equipe_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/times` | Lista todos |
| GET | `/times/{id}` | Busca por id (404 se não existir) |
| POST | `/times` | Cria (201) |
| PUT | `/times/{id}` | Atualiza (404 se não existir) |
| DELETE | `/times/{id}` | Exclui (204) |
| GET | `/jogadores` | Lista todos |
| GET | `/jogadores?equipeId=1` | Lista filtrando por Time |
| GET | `/jogadores/{id}` | Busca por id (404 se não existir) |
| POST | `/jogadores` | Cria (201) |
| PUT | `/jogadores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/jogadores/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/times

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Time → Jogador.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/times -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cidade": "São Paulo", "anoFundacao": 10 }'
```

```bash
# Time
curl -X POST http://localhost:8080/times -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo", "anoFundacao": 10 }'
curl http://localhost:8080/times
curl http://localhost:8080/times/1
curl -X PUT http://localhost:8080/times/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo", "anoFundacao": 10 }'
curl -X DELETE http://localhost:8080/times/1

# Jogador
curl -X POST http://localhost:8080/jogadores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "posicao": "Posicao exemplo", "numeroCamisa": 10, "gols": 10, "equipe": { "id": 1 } }'
curl http://localhost:8080/jogadores
curl http://localhost:8080/jogadores/1
curl -X PUT http://localhost:8080/jogadores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "posicao": "Posicao exemplo", "numeroCamisa": 10, "gols": 10, "equipe": { "id": 1 } }'
curl -X DELETE http://localhost:8080/jogadores/1
```

## Estrutura

```
src/main/java/com/exemplo/campeonato/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
