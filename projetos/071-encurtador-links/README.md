# 071 · Encurtador de Links

**Categoria:** Tecnologia
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Criação de links curtos por usuário, com contagem de cliques.

## Entidades

### Usuario (tabela `usuarios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |

### LinkCurto (tabela `links_curtos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `urlOriginal` | `String` |
| `codigo` | `String` |
| `cliques` | `Integer` |
| `expiraEm` | `LocalDate` |
| `ativo` | `Boolean` |
| `usuario` | `Usuario` (ManyToOne, coluna `usuario_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/usuarios` | Lista todos |
| GET | `/usuarios/{id}` | Busca por id (404 se não existir) |
| POST | `/usuarios` | Cria (201) |
| PUT | `/usuarios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/usuarios/{id}` | Exclui (204) |
| GET | `/links-curtos` | Lista todos |
| GET | `/links-curtos?usuarioId=1` | Lista filtrando por Usuario |
| GET | `/links-curtos/{id}` | Busca por id (404 se não existir) |
| POST | `/links-curtos` | Cria (201) |
| PUT | `/links-curtos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/links-curtos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/usuarios

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Usuario → LinkCurto.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/usuarios -ContentType "application/json" -Body '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
```

```bash
# Usuario
curl -X POST http://localhost:8080/usuarios -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl http://localhost:8080/usuarios
curl http://localhost:8080/usuarios/1
curl -X PUT http://localhost:8080/usuarios/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/usuarios/1

# LinkCurto
curl -X POST http://localhost:8080/links-curtos -H "Content-Type: application/json" -d '{ "urlOriginal": "UrlOriginal exemplo", "codigo": "COD-001", "cliques": 10, "expiraEm": "2026-03-15", "ativo": true, "usuario": { "id": 1 } }'
curl http://localhost:8080/links-curtos
curl http://localhost:8080/links-curtos/1
curl -X PUT http://localhost:8080/links-curtos/1 -H "Content-Type: application/json" -d '{ "urlOriginal": "UrlOriginal exemplo", "codigo": "COD-001", "cliques": 10, "expiraEm": "2026-03-15", "ativo": true, "usuario": { "id": 1 } }'
curl -X DELETE http://localhost:8080/links-curtos/1
```

## Estrutura

```
src/main/java/com/exemplo/encurtadorlinks/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
