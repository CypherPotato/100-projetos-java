# 065 · Livro de Receitas

**Categoria:** Alimentação
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Cadastro de receitas culinárias e de seus ingredientes.

## Entidades

### Receita (tabela `receitas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `modoPreparo` | `String` |
| `tempoPreparoMinutos` | `Integer` |
| `porcoes` | `Integer` |

### Ingrediente (tabela `ingredientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `quantidade` | `Double` |
| `unidade` | `String` |
| `receita` | `Receita` (ManyToOne, coluna `receita_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/receitas` | Lista todos |
| GET | `/receitas/{id}` | Busca por id (404 se não existir) |
| POST | `/receitas` | Cria (201) |
| PUT | `/receitas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/receitas/{id}` | Exclui (204) |
| GET | `/ingredientes` | Lista todos |
| GET | `/ingredientes?receitaId=1` | Lista filtrando por Receita |
| GET | `/ingredientes/{id}` | Busca por id (404 se não existir) |
| POST | `/ingredientes` | Cria (201) |
| PUT | `/ingredientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/ingredientes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/receitas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Receita → Ingrediente.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/receitas -ContentType "application/json" -Body '{ "titulo": "Exemplo", "modoPreparo": "ModoPreparo exemplo", "tempoPreparoMinutos": 10, "porcoes": 10 }'
```

```bash
# Receita
curl -X POST http://localhost:8080/receitas -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "modoPreparo": "ModoPreparo exemplo", "tempoPreparoMinutos": 10, "porcoes": 10 }'
curl http://localhost:8080/receitas
curl http://localhost:8080/receitas/1
curl -X PUT http://localhost:8080/receitas/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "modoPreparo": "ModoPreparo exemplo", "tempoPreparoMinutos": 10, "porcoes": 10 }'
curl -X DELETE http://localhost:8080/receitas/1

# Ingrediente
curl -X POST http://localhost:8080/ingredientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "quantidade": 12.5, "unidade": "Unidade exemplo", "receita": { "id": 1 } }'
curl http://localhost:8080/ingredientes
curl http://localhost:8080/ingredientes/1
curl -X PUT http://localhost:8080/ingredientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "quantidade": 12.5, "unidade": "Unidade exemplo", "receita": { "id": 1 } }'
curl -X DELETE http://localhost:8080/ingredientes/1
```

## Estrutura

```
src/main/java/com/exemplo/receitas/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
