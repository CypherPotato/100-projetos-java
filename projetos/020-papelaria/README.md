# 020 · Papelaria

**Categoria:** Comércio
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Controle de categorias, produtos e vendas de uma papelaria.

## Entidades

### Categoria (tabela `categorias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Produto (tabela `produtos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `marca` | `String` |
| `preco` | `BigDecimal` |
| `estoque` | `Integer` |
| `categoria` | `Categoria` (ManyToOne, coluna `categoria_id`) |

### Venda (tabela `vendas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataVenda` | `LocalDateTime` |
| `quantidade` | `Integer` |
| `valorTotal` | `BigDecimal` |
| `produto` | `Produto` (ManyToOne, coluna `produto_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias` | Lista todos |
| GET | `/categorias/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias` | Cria (201) |
| PUT | `/categorias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias/{id}` | Exclui (204) |
| GET | `/produtos` | Lista todos |
| GET | `/produtos?categoriaId=1` | Lista filtrando por Categoria |
| GET | `/produtos/{id}` | Busca por id (404 se não existir) |
| POST | `/produtos` | Cria (201) |
| PUT | `/produtos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/produtos/{id}` | Exclui (204) |
| GET | `/vendas` | Lista todos |
| GET | `/vendas?produtoId=1` | Lista filtrando por Produto |
| GET | `/vendas/{id}` | Busca por id (404 se não existir) |
| POST | `/vendas` | Cria (201) |
| PUT | `/vendas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vendas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/categorias

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Categoria → Produto → Venda.

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

# Produto
curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "marca": "Marca X", "preco": 99.90, "estoque": 10, "categoria": { "id": 1 } }'
curl http://localhost:8080/produtos
curl http://localhost:8080/produtos/1
curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "marca": "Marca X", "preco": 99.90, "estoque": 10, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/produtos/1

# Venda
curl -X POST http://localhost:8080/vendas -H "Content-Type: application/json" -d '{ "dataVenda": "2026-03-15T10:30:00", "quantidade": 10, "valorTotal": 99.90, "produto": { "id": 1 } }'
curl http://localhost:8080/vendas
curl http://localhost:8080/vendas/1
curl -X PUT http://localhost:8080/vendas/1 -H "Content-Type: application/json" -d '{ "dataVenda": "2026-03-15T10:30:00", "quantidade": 10, "valorTotal": 99.90, "produto": { "id": 1 } }'
curl -X DELETE http://localhost:8080/vendas/1
```

## Estrutura

```
src/main/java/com/exemplo/papelaria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
