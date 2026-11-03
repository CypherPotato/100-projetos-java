# 132 · Clube de Xadrez

**Categoria:** Lazer e Cultura
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Organização de jogadores, torneios e partidas de xadrez.

## Entidades

### Jogador (tabela `jogadores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `rating` | `Integer` |
| `dataIngresso` | `LocalDate` |

### Torneio (tabela `torneios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataInicio` | `LocalDate` |
| `taxaInscricao` | `BigDecimal` |

### Partida (tabela `partidas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `resultado` | `String` |
| `rodada` | `Integer` |
| `brancas` | `Jogador` (ManyToOne, coluna `brancas_id`) |
| `torneio` | `Torneio` (ManyToOne, coluna `torneio_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/jogadores` | Lista todos |
| GET | `/jogadores/{id}` | Busca por id (404 se não existir) |
| POST | `/jogadores` | Cria (201) |
| PUT | `/jogadores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/jogadores/{id}` | Exclui (204) |
| GET | `/torneios` | Lista todos |
| GET | `/torneios/{id}` | Busca por id (404 se não existir) |
| POST | `/torneios` | Cria (201) |
| PUT | `/torneios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/torneios/{id}` | Exclui (204) |
| GET | `/partidas` | Lista todos |
| GET | `/partidas?brancasId=1` | Lista filtrando por Jogador |
| GET | `/partidas?torneioId=1` | Lista filtrando por Torneio |
| GET | `/partidas/{id}` | Busca por id (404 se não existir) |
| POST | `/partidas` | Cria (201) |
| PUT | `/partidas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/partidas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\clube-xadrez.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/jogadores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/clube-xadrez`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Jogador → Torneio → Partida.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/jogadores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "rating": 10, "dataIngresso": "2026-03-15" }'
```

```bash
# Jogador
curl -X POST http://localhost:8080/jogadores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "rating": 10, "dataIngresso": "2026-03-15" }'
curl http://localhost:8080/jogadores
curl http://localhost:8080/jogadores/1
curl -X PUT http://localhost:8080/jogadores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "rating": 10, "dataIngresso": "2026-03-15" }'
curl -X DELETE http://localhost:8080/jogadores/1

# Torneio
curl -X POST http://localhost:8080/torneios -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataInicio": "2026-03-15", "taxaInscricao": 99.90 }'
curl http://localhost:8080/torneios
curl http://localhost:8080/torneios/1
curl -X PUT http://localhost:8080/torneios/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataInicio": "2026-03-15", "taxaInscricao": 99.90 }'
curl -X DELETE http://localhost:8080/torneios/1

# Partida
curl -X POST http://localhost:8080/partidas -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "resultado": "Resultado exemplo", "rodada": 10, "brancas": { "id": 1 }, "torneio": { "id": 1 } }'
curl http://localhost:8080/partidas
curl http://localhost:8080/partidas/1
curl -X PUT http://localhost:8080/partidas/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "resultado": "Resultado exemplo", "rodada": 10, "brancas": { "id": 1 }, "torneio": { "id": 1 } }'
curl -X DELETE http://localhost:8080/partidas/1
```

## Estrutura

```
src/main/java/com/exemplo/clubexadrez/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
