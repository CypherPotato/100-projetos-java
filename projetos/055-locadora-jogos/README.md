# 055 · Locadora de Jogos

**Categoria:** Lazer e Cultura
**Lombok:** sim · **Camada Service:** não

Locação de jogos de videogame para clientes cadastrados.

## Entidades

### Jogo (tabela `jogos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `plataforma` | `String` |
| `valorDiaria` | `BigDecimal` |
| `disponivel` | `Boolean` |

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Locacao (tabela `locacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataLocacao` | `LocalDate` |
| `dataDevolucao` | `LocalDate` |
| `valorTotal` | `BigDecimal` |
| `jogo` | `Jogo` (ManyToOne, coluna `jogo_id`) |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/jogos` | Lista todos |
| GET | `/jogos/{id}` | Busca por id (404 se não existir) |
| POST | `/jogos` | Cria (201) |
| PUT | `/jogos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/jogos/{id}` | Exclui (204) |
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/locacoes` | Lista todos |
| GET | `/locacoes?jogoId=1` | Lista filtrando por Jogo |
| GET | `/locacoes?clienteId=1` | Lista filtrando por Cliente |
| GET | `/locacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/locacoes` | Cria (201) |
| PUT | `/locacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/locacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/jogos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Jogo → Cliente → Locacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/jogos -ContentType "application/json" -Body '{ "titulo": "Exemplo", "plataforma": "Plataforma exemplo", "valorDiaria": 99.90, "disponivel": true }'
```

```bash
# Jogo
curl -X POST http://localhost:8080/jogos -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "plataforma": "Plataforma exemplo", "valorDiaria": 99.90, "disponivel": true }'
curl http://localhost:8080/jogos
curl http://localhost:8080/jogos/1
curl -X PUT http://localhost:8080/jogos/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "plataforma": "Plataforma exemplo", "valorDiaria": 99.90, "disponivel": true }'
curl -X DELETE http://localhost:8080/jogos/1

# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/clientes/1

# Locacao
curl -X POST http://localhost:8080/locacoes -H "Content-Type: application/json" -d '{ "dataLocacao": "2026-03-15", "dataDevolucao": "2026-03-15", "valorTotal": 99.90, "jogo": { "id": 1 }, "cliente": { "id": 1 } }'
curl http://localhost:8080/locacoes
curl http://localhost:8080/locacoes/1
curl -X PUT http://localhost:8080/locacoes/1 -H "Content-Type: application/json" -d '{ "dataLocacao": "2026-03-15", "dataDevolucao": "2026-03-15", "valorTotal": 99.90, "jogo": { "id": 1 }, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/locacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/locadorajogos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
