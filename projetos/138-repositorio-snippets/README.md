# 138 · Repositório de Snippets

**Categoria:** Tecnologia
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Biblioteca de trechos de código organizados por linguagem, com comentários.

## Entidades

### Linguagem (tabela `linguagens`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `extensao` | `String` |

### Snippet (tabela `snippets`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `codigo` | `String` |
| `criadoEm` | `LocalDateTime` |
| `linguagem` | `Linguagem` (ManyToOne, coluna `linguagem_id`) |

### Comentario (tabela `comentarios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `autor` | `String` |
| `mensagem` | `String` |
| `dataHora` | `LocalDateTime` |
| `snippet` | `Snippet` (ManyToOne, coluna `snippet_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/linguagens` | Lista todos |
| GET | `/linguagens/{id}` | Busca por id (404 se não existir) |
| POST | `/linguagens` | Cria (201) |
| PUT | `/linguagens/{id}` | Atualiza (404 se não existir) |
| DELETE | `/linguagens/{id}` | Exclui (204) |
| GET | `/snippets` | Lista todos |
| GET | `/snippets?linguagemId=1` | Lista filtrando por Linguagem |
| GET | `/snippets/{id}` | Busca por id (404 se não existir) |
| POST | `/snippets` | Cria (201) |
| PUT | `/snippets/{id}` | Atualiza (404 se não existir) |
| DELETE | `/snippets/{id}` | Exclui (204) |
| GET | `/comentarios` | Lista todos |
| GET | `/comentarios?snippetId=1` | Lista filtrando por Snippet |
| GET | `/comentarios/{id}` | Busca por id (404 se não existir) |
| POST | `/comentarios` | Cria (201) |
| PUT | `/comentarios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/comentarios/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\repositorio-snippets.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/linguagens
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/repositorio-snippets`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Linguagem → Snippet → Comentario.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/linguagens -ContentType "application/json" -Body '{ "nome": "Maria Silva", "extensao": "Extensao exemplo" }'
```

```bash
# Linguagem
curl -X POST http://localhost:8080/linguagens -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "extensao": "Extensao exemplo" }'
curl http://localhost:8080/linguagens
curl http://localhost:8080/linguagens/1
curl -X PUT http://localhost:8080/linguagens/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "extensao": "Extensao exemplo" }'
curl -X DELETE http://localhost:8080/linguagens/1

# Snippet
curl -X POST http://localhost:8080/snippets -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "codigo": "COD-001", "criadoEm": "2026-03-15T10:30:00", "linguagem": { "id": 1 } }'
curl http://localhost:8080/snippets
curl http://localhost:8080/snippets/1
curl -X PUT http://localhost:8080/snippets/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "codigo": "COD-001", "criadoEm": "2026-03-15T10:30:00", "linguagem": { "id": 1 } }'
curl -X DELETE http://localhost:8080/snippets/1

# Comentario
curl -X POST http://localhost:8080/comentarios -H "Content-Type: application/json" -d '{ "autor": "Autor exemplo", "mensagem": "Mensagem exemplo", "dataHora": "2026-03-15T10:30:00", "snippet": { "id": 1 } }'
curl http://localhost:8080/comentarios
curl http://localhost:8080/comentarios/1
curl -X PUT http://localhost:8080/comentarios/1 -H "Content-Type: application/json" -d '{ "autor": "Autor exemplo", "mensagem": "Mensagem exemplo", "dataHora": "2026-03-15T10:30:00", "snippet": { "id": 1 } }'
curl -X DELETE http://localhost:8080/comentarios/1
```

## Estrutura

```
src/main/java/com/exemplo/repositoriosnippets/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
