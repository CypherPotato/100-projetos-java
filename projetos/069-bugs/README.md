# 069 · Controle de Bugs

**Categoria:** Tecnologia
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** sim

Registro de projetos de software e dos bugs encontrados em cada um.

## Entidades

### Projeto (tabela `projetos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `repositorio` | `String` |
| `ativo` | `Boolean` |

### Bug (tabela `bugs`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `severidade` | `String` |
| `situacao` | `String` |
| `registradoEm` | `LocalDateTime` |
| `projeto` | `Projeto` (ManyToOne, coluna `projeto_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/projetos` | Lista todos |
| GET | `/projetos/{id}` | Busca por id (404 se não existir) |
| POST | `/projetos` | Cria (201) |
| PUT | `/projetos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/projetos/{id}` | Exclui (204) |
| GET | `/bugs` | Lista todos |
| GET | `/bugs?projetoId=1` | Lista filtrando por Projeto |
| GET | `/bugs/{id}` | Busca por id (404 se não existir) |
| POST | `/bugs` | Cria (201) |
| PUT | `/bugs/{id}` | Atualiza (404 se não existir) |
| DELETE | `/bugs/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/projetos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Projeto → Bug.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/projetos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "repositorio": "Repositorio exemplo", "ativo": true }'
```

```bash
# Projeto
curl -X POST http://localhost:8080/projetos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "repositorio": "Repositorio exemplo", "ativo": true }'
curl http://localhost:8080/projetos
curl http://localhost:8080/projetos/1
curl -X PUT http://localhost:8080/projetos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "repositorio": "Repositorio exemplo", "ativo": true }'
curl -X DELETE http://localhost:8080/projetos/1

# Bug
curl -X POST http://localhost:8080/bugs -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "severidade": "Severidade exemplo", "situacao": "Situacao exemplo", "registradoEm": "2026-03-15T10:30:00", "projeto": { "id": 1 } }'
curl http://localhost:8080/bugs
curl http://localhost:8080/bugs/1
curl -X PUT http://localhost:8080/bugs/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "severidade": "Severidade exemplo", "situacao": "Situacao exemplo", "registradoEm": "2026-03-15T10:30:00", "projeto": { "id": 1 } }'
curl -X DELETE http://localhost:8080/bugs/1
```

## Estrutura

```
src/main/java/com/exemplo/bugs/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
