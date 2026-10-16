# 058 · Coleção de Filmes

**Categoria:** Lazer e Cultura
**Lombok:** não · **Camada Service:** não

Catálogo pessoal de filmes organizados por diretor.

## Entidades

### Diretor (tabela `diretores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `nacionalidade` | `String` |
| `anoNascimento` | `Integer` |

### Filme (tabela `filmes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `anoLancamento` | `Integer` |
| `nota` | `Double` |
| `assistido` | `Boolean` |
| `diretor` | `Diretor` (ManyToOne, coluna `diretor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/diretores` | Lista todos |
| GET | `/diretores/{id}` | Busca por id (404 se não existir) |
| POST | `/diretores` | Cria (201) |
| PUT | `/diretores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/diretores/{id}` | Exclui (204) |
| GET | `/filmes` | Lista todos |
| GET | `/filmes?diretorId=1` | Lista filtrando por Diretor |
| GET | `/filmes/{id}` | Busca por id (404 se não existir) |
| POST | `/filmes` | Cria (201) |
| PUT | `/filmes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/filmes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/diretores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Diretor → Filme.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/diretores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo", "anoNascimento": 10 }'
```

```bash
# Diretor
curl -X POST http://localhost:8080/diretores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo", "anoNascimento": 10 }'
curl http://localhost:8080/diretores
curl http://localhost:8080/diretores/1
curl -X PUT http://localhost:8080/diretores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo", "anoNascimento": 10 }'
curl -X DELETE http://localhost:8080/diretores/1

# Filme
curl -X POST http://localhost:8080/filmes -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "anoLancamento": 10, "nota": 12.5, "assistido": true, "diretor": { "id": 1 } }'
curl http://localhost:8080/filmes
curl http://localhost:8080/filmes/1
curl -X PUT http://localhost:8080/filmes/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "anoLancamento": 10, "nota": 12.5, "assistido": true, "diretor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/filmes/1
```

## Estrutura

```
src/main/java/com/exemplo/colecaofilmes/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
