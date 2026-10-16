# 057 · Playlist de Músicas

**Categoria:** Lazer e Cultura
**Lombok:** sim · **Camada Service:** não

Organização de artistas e músicas favoritas em uma playlist.

## Entidades

### Artista (tabela `artistas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `paisOrigem` | `String` |
| `generoMusical` | `String` |

### Musica (tabela `musicas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `duracaoSegundos` | `Integer` |
| `anoLancamento` | `Integer` |
| `favorita` | `Boolean` |
| `artista` | `Artista` (ManyToOne, coluna `artista_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/artistas` | Lista todos |
| GET | `/artistas/{id}` | Busca por id (404 se não existir) |
| POST | `/artistas` | Cria (201) |
| PUT | `/artistas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/artistas/{id}` | Exclui (204) |
| GET | `/musicas` | Lista todos |
| GET | `/musicas?artistaId=1` | Lista filtrando por Artista |
| GET | `/musicas/{id}` | Busca por id (404 se não existir) |
| POST | `/musicas` | Cria (201) |
| PUT | `/musicas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/musicas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/artistas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Artista → Musica.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/artistas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "paisOrigem": "PaisOrigem exemplo", "generoMusical": "GeneroMusical exemplo" }'
```

```bash
# Artista
curl -X POST http://localhost:8080/artistas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "paisOrigem": "PaisOrigem exemplo", "generoMusical": "GeneroMusical exemplo" }'
curl http://localhost:8080/artistas
curl http://localhost:8080/artistas/1
curl -X PUT http://localhost:8080/artistas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "paisOrigem": "PaisOrigem exemplo", "generoMusical": "GeneroMusical exemplo" }'
curl -X DELETE http://localhost:8080/artistas/1

# Musica
curl -X POST http://localhost:8080/musicas -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "duracaoSegundos": 10, "anoLancamento": 10, "favorita": true, "artista": { "id": 1 } }'
curl http://localhost:8080/musicas
curl http://localhost:8080/musicas/1
curl -X PUT http://localhost:8080/musicas/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "duracaoSegundos": 10, "anoLancamento": 10, "favorita": true, "artista": { "id": 1 } }'
curl -X DELETE http://localhost:8080/musicas/1
```

## Estrutura

```
src/main/java/com/exemplo/playlist/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
