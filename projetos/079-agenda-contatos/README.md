# 079 · Agenda de Contatos

**Categoria:** Comunidade
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Agenda pessoal com contatos organizados por grupos.

## Entidades

### Grupo (tabela `grupos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Contato (tabela `contatos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `email` | `String` |
| `aniversario` | `LocalDate` |
| `grupo` | `Grupo` (ManyToOne, coluna `grupo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/grupos` | Lista todos |
| GET | `/grupos/{id}` | Busca por id (404 se não existir) |
| POST | `/grupos` | Cria (201) |
| PUT | `/grupos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/grupos/{id}` | Exclui (204) |
| GET | `/contatos` | Lista todos |
| GET | `/contatos?grupoId=1` | Lista filtrando por Grupo |
| GET | `/contatos/{id}` | Busca por id (404 se não existir) |
| POST | `/contatos` | Cria (201) |
| PUT | `/contatos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/contatos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/grupos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Grupo → Contato.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/grupos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# Grupo
curl -X POST http://localhost:8080/grupos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/grupos
curl http://localhost:8080/grupos/1
curl -X PUT http://localhost:8080/grupos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/grupos/1

# Contato
curl -X POST http://localhost:8080/contatos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com", "aniversario": "2026-03-15", "grupo": { "id": 1 } }'
curl http://localhost:8080/contatos
curl http://localhost:8080/contatos/1
curl -X PUT http://localhost:8080/contatos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com", "aniversario": "2026-03-15", "grupo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/contatos/1
```

## Estrutura

```
src/main/java/com/exemplo/agendacontatos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
