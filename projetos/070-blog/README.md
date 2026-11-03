# 070 · Blog

**Categoria:** Tecnologia
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Publicação de posts por autores, com comentários dos leitores.

## Entidades

### Autor (tabela `autores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `biografia` | `String` |

### Post (tabela `posts`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `conteudo` | `String` |
| `publicadoEm` | `LocalDateTime` |
| `visualizacoes` | `Integer` |
| `publicado` | `Boolean` |
| `autor` | `Autor` (ManyToOne, coluna `autor_id`) |

### Comentario (tabela `comentarios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nomeLeitor` | `String` |
| `conteudo` | `String` |
| `dataHora` | `LocalDateTime` |
| `post` | `Post` (ManyToOne, coluna `post_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/autores` | Lista todos |
| GET | `/autores/{id}` | Busca por id (404 se não existir) |
| POST | `/autores` | Cria (201) |
| PUT | `/autores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/autores/{id}` | Exclui (204) |
| GET | `/posts` | Lista todos |
| GET | `/posts?autorId=1` | Lista filtrando por Autor |
| GET | `/posts/{id}` | Busca por id (404 se não existir) |
| POST | `/posts` | Cria (201) |
| PUT | `/posts/{id}` | Atualiza (404 se não existir) |
| DELETE | `/posts/{id}` | Exclui (204) |
| GET | `/comentarios` | Lista todos |
| GET | `/comentarios?postId=1` | Lista filtrando por Post |
| GET | `/comentarios/{id}` | Busca por id (404 se não existir) |
| POST | `/comentarios` | Cria (201) |
| PUT | `/comentarios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/comentarios/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/autores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Autor → Post → Comentario.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/autores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "biografia": "Biografia exemplo" }'
```

```bash
# Autor
curl -X POST http://localhost:8080/autores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "biografia": "Biografia exemplo" }'
curl http://localhost:8080/autores
curl http://localhost:8080/autores/1
curl -X PUT http://localhost:8080/autores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "biografia": "Biografia exemplo" }'
curl -X DELETE http://localhost:8080/autores/1

# Post
curl -X POST http://localhost:8080/posts -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "conteudo": "Conteudo exemplo", "publicadoEm": "2026-03-15T10:30:00", "visualizacoes": 10, "publicado": true, "autor": { "id": 1 } }'
curl http://localhost:8080/posts
curl http://localhost:8080/posts/1
curl -X PUT http://localhost:8080/posts/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "conteudo": "Conteudo exemplo", "publicadoEm": "2026-03-15T10:30:00", "visualizacoes": 10, "publicado": true, "autor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/posts/1

# Comentario
curl -X POST http://localhost:8080/comentarios -H "Content-Type: application/json" -d '{ "nomeLeitor": "NomeLeitor exemplo", "conteudo": "Conteudo exemplo", "dataHora": "2026-03-15T10:30:00", "post": { "id": 1 } }'
curl http://localhost:8080/comentarios
curl http://localhost:8080/comentarios/1
curl -X PUT http://localhost:8080/comentarios/1 -H "Content-Type: application/json" -d '{ "nomeLeitor": "NomeLeitor exemplo", "conteudo": "Conteudo exemplo", "dataHora": "2026-03-15T10:30:00", "post": { "id": 1 } }'
curl -X DELETE http://localhost:8080/comentarios/1
```

## Estrutura

```
src/main/java/com/exemplo/blog/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
