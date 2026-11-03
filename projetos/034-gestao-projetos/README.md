# 034 · Gestão de Projetos

**Categoria:** Gestão
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Acompanhamento de projetos, responsáveis e tarefas de uma equipe.

## Entidades

### Projeto (tabela `projetos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |
| `dataInicio` | `LocalDate` |
| `orcamento` | `BigDecimal` |

### Responsavel (tabela `responsaveis`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |

### Tarefa (tabela `tarefas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `prioridade` | `Integer` |
| `prazo` | `LocalDate` |
| `concluida` | `Boolean` |
| `projeto` | `Projeto` (ManyToOne, coluna `projeto_id`) |
| `responsavel` | `Responsavel` (ManyToOne, coluna `responsavel_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/projetos` | Lista todos |
| GET | `/projetos/{id}` | Busca por id (404 se não existir) |
| POST | `/projetos` | Cria (201) |
| PUT | `/projetos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/projetos/{id}` | Exclui (204) |
| GET | `/responsaveis` | Lista todos |
| GET | `/responsaveis/{id}` | Busca por id (404 se não existir) |
| POST | `/responsaveis` | Cria (201) |
| PUT | `/responsaveis/{id}` | Atualiza (404 se não existir) |
| DELETE | `/responsaveis/{id}` | Exclui (204) |
| GET | `/tarefas` | Lista todos |
| GET | `/tarefas?projetoId=1` | Lista filtrando por Projeto |
| GET | `/tarefas?responsavelId=1` | Lista filtrando por Responsavel |
| GET | `/tarefas/{id}` | Busca por id (404 se não existir) |
| POST | `/tarefas` | Cria (201) |
| PUT | `/tarefas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/tarefas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/projetos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Projeto → Responsavel → Tarefa.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/projetos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo", "dataInicio": "2026-03-15", "orcamento": 99.90 }'
```

```bash
# Projeto
curl -X POST http://localhost:8080/projetos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo", "dataInicio": "2026-03-15", "orcamento": 99.90 }'
curl http://localhost:8080/projetos
curl http://localhost:8080/projetos/1
curl -X PUT http://localhost:8080/projetos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo", "dataInicio": "2026-03-15", "orcamento": 99.90 }'
curl -X DELETE http://localhost:8080/projetos/1

# Responsavel
curl -X POST http://localhost:8080/responsaveis -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl http://localhost:8080/responsaveis
curl http://localhost:8080/responsaveis/1
curl -X PUT http://localhost:8080/responsaveis/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/responsaveis/1

# Tarefa
curl -X POST http://localhost:8080/tarefas -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "prioridade": 10, "prazo": "2026-03-15", "concluida": true, "projeto": { "id": 1 }, "responsavel": { "id": 1 } }'
curl http://localhost:8080/tarefas
curl http://localhost:8080/tarefas/1
curl -X PUT http://localhost:8080/tarefas/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "prioridade": 10, "prazo": "2026-03-15", "concluida": true, "projeto": { "id": 1 }, "responsavel": { "id": 1 } }'
curl -X DELETE http://localhost:8080/tarefas/1
```

## Estrutura

```
src/main/java/com/exemplo/gestaoprojetos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
