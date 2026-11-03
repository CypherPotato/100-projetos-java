# 001 · Biblioteca

**Categoria:** Educação
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Controle de autores e livros de uma biblioteca.

## Entidades

### Autor (tabela `autores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `nacionalidade` | `String` |

### Livro (tabela `livros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `anoPublicacao` | `Integer` |
| `disponivel` | `Boolean` |
| `autor` | `Autor` (ManyToOne, coluna `autor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/autores` | Lista todos |
| GET | `/autores/{id}` | Busca por id (404 se não existir) |
| POST | `/autores` | Cria (201) |
| PUT | `/autores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/autores/{id}` | Exclui (204) |
| GET | `/livros` | Lista todos |
| GET | `/livros?autorId=1` | Lista filtrando por Autor |
| GET | `/livros/{id}` | Busca por id (404 se não existir) |
| POST | `/livros` | Cria (201) |
| PUT | `/livros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/livros/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/autores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Autor → Livro.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/autores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo" }'
```

```bash
# Autor
curl -X POST http://localhost:8080/autores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo" }'
curl http://localhost:8080/autores
curl http://localhost:8080/autores/1
curl -X PUT http://localhost:8080/autores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo" }'
curl -X DELETE http://localhost:8080/autores/1

# Livro
curl -X POST http://localhost:8080/livros -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "anoPublicacao": 10, "disponivel": true, "autor": { "id": 1 } }'
curl http://localhost:8080/livros
curl http://localhost:8080/livros/1
curl -X PUT http://localhost:8080/livros/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "anoPublicacao": 10, "disponivel": true, "autor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/livros/1
```

## Estrutura

```
src/main/java/com/exemplo/biblioteca/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
