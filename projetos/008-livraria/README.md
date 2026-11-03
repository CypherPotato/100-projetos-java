# 008 · Livraria

**Categoria:** Comércio
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Controle de editoras e livros à venda em uma livraria.

## Entidades

### Editora (tabela `editoras`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cidade` | `String` |

### Livro (tabela `livros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `isbn` | `String` |
| `preco` | `BigDecimal` |
| `estoque` | `Integer` |
| `editora` | `Editora` (ManyToOne, coluna `editora_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/editoras` | Lista todos |
| GET | `/editoras/{id}` | Busca por id (404 se não existir) |
| POST | `/editoras` | Cria (201) |
| PUT | `/editoras/{id}` | Atualiza (404 se não existir) |
| DELETE | `/editoras/{id}` | Exclui (204) |
| GET | `/livros` | Lista todos |
| GET | `/livros?editoraId=1` | Lista filtrando por Editora |
| GET | `/livros/{id}` | Busca por id (404 se não existir) |
| POST | `/livros` | Cria (201) |
| PUT | `/livros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/livros/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/editoras

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Editora → Livro.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/editoras -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cidade": "São Paulo" }'
```

```bash
# Editora
curl -X POST http://localhost:8080/editoras -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo" }'
curl http://localhost:8080/editoras
curl http://localhost:8080/editoras/1
curl -X PUT http://localhost:8080/editoras/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo" }'
curl -X DELETE http://localhost:8080/editoras/1

# Livro
curl -X POST http://localhost:8080/livros -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "isbn": "Isbn exemplo", "preco": 99.90, "estoque": 10, "editora": { "id": 1 } }'
curl http://localhost:8080/livros
curl http://localhost:8080/livros/1
curl -X PUT http://localhost:8080/livros/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "isbn": "Isbn exemplo", "preco": 99.90, "estoque": 10, "editora": { "id": 1 } }'
curl -X DELETE http://localhost:8080/livros/1
```

## Estrutura

```
src/main/java/com/exemplo/livraria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
