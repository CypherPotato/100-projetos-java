# 084 · Brechó Solidário

**Categoria:** Comunidade
**Lombok:** sim · **Camada Service:** não

Controle de peças doadas e vendidas em um brechó beneficente.

## Entidades

### Categoria (tabela `categorias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Peca (tabela `pecas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `tamanho` | `String` |
| `preco` | `BigDecimal` |
| `vendida` | `Boolean` |
| `categoria` | `Categoria` (ManyToOne, coluna `categoria_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias` | Lista todos |
| GET | `/categorias/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias` | Cria (201) |
| PUT | `/categorias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias/{id}` | Exclui (204) |
| GET | `/pecas` | Lista todos |
| GET | `/pecas?categoriaId=1` | Lista filtrando por Categoria |
| GET | `/pecas/{id}` | Busca por id (404 se não existir) |
| POST | `/pecas` | Cria (201) |
| PUT | `/pecas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pecas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/categorias

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Categoria → Peca.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/categorias -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# Categoria
curl -X POST http://localhost:8080/categorias -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/categorias
curl http://localhost:8080/categorias/1
curl -X PUT http://localhost:8080/categorias/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/categorias/1

# Peca
curl -X POST http://localhost:8080/pecas -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "tamanho": "Tamanho exemplo", "preco": 99.90, "vendida": true, "categoria": { "id": 1 } }'
curl http://localhost:8080/pecas
curl http://localhost:8080/pecas/1
curl -X PUT http://localhost:8080/pecas/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "tamanho": "Tamanho exemplo", "preco": 99.90, "vendida": true, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/pecas/1
```

## Estrutura

```
src/main/java/com/exemplo/brechosolidario/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
