# 061 · Lanchonete

**Categoria:** Alimentação
**Lombok:** sim · **Camada Service:** não

Cardápio de lanches organizado por categorias.

## Entidades

### Categoria (tabela `categorias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Lanche (tabela `lanches`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `preco` | `BigDecimal` |
| `calorias` | `Integer` |
| `disponivel` | `Boolean` |
| `categoria` | `Categoria` (ManyToOne, coluna `categoria_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias` | Lista todos |
| GET | `/categorias/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias` | Cria (201) |
| PUT | `/categorias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias/{id}` | Exclui (204) |
| GET | `/lanches` | Lista todos |
| GET | `/lanches?categoriaId=1` | Lista filtrando por Categoria |
| GET | `/lanches/{id}` | Busca por id (404 se não existir) |
| POST | `/lanches` | Cria (201) |
| PUT | `/lanches/{id}` | Atualiza (404 se não existir) |
| DELETE | `/lanches/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/categorias

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Categoria → Lanche.

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

# Lanche
curl -X POST http://localhost:8080/lanches -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "calorias": 10, "disponivel": true, "categoria": { "id": 1 } }'
curl http://localhost:8080/lanches
curl http://localhost:8080/lanches/1
curl -X PUT http://localhost:8080/lanches/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "calorias": 10, "disponivel": true, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/lanches/1
```

## Estrutura

```
src/main/java/com/exemplo/lanchonete/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
