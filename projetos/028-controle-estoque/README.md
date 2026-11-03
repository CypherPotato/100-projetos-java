# 028 · Controle de Estoque

**Categoria:** Gestão
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Cadastro de produtos por categoria e registro das movimentações de estoque.

## Entidades

### CategoriaProduto (tabela `categorias_produto`)

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
| `codigo` | `String` |
| `quantidade` | `Integer` |
| `precoCusto` | `BigDecimal` |
| `ativo` | `Boolean` |
| `categoria` | `CategoriaProduto` (ManyToOne, coluna `categoria_id`) |

### Movimentacao (tabela `movimentacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `tipo` | `String` |
| `quantidade` | `Integer` |
| `dataHora` | `LocalDateTime` |
| `produto` | `Produto` (ManyToOne, coluna `produto_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias-produto` | Lista todos |
| GET | `/categorias-produto/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias-produto` | Cria (201) |
| PUT | `/categorias-produto/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias-produto/{id}` | Exclui (204) |
| GET | `/produtos` | Lista todos |
| GET | `/produtos?categoriaId=1` | Lista filtrando por CategoriaProduto |
| GET | `/produtos/{id}` | Busca por id (404 se não existir) |
| POST | `/produtos` | Cria (201) |
| PUT | `/produtos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/produtos/{id}` | Exclui (204) |
| GET | `/movimentacoes` | Lista todos |
| GET | `/movimentacoes?produtoId=1` | Lista filtrando por Produto |
| GET | `/movimentacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/movimentacoes` | Cria (201) |
| PUT | `/movimentacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/movimentacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/categorias-produto

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): CategoriaProduto → Produto → Movimentacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/categorias-produto -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# CategoriaProduto
curl -X POST http://localhost:8080/categorias-produto -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/categorias-produto
curl http://localhost:8080/categorias-produto/1
curl -X PUT http://localhost:8080/categorias-produto/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/categorias-produto/1

# Produto
curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "codigo": "COD-001", "quantidade": 10, "precoCusto": 99.90, "ativo": true, "categoria": { "id": 1 } }'
curl http://localhost:8080/produtos
curl http://localhost:8080/produtos/1
curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "codigo": "COD-001", "quantidade": 10, "precoCusto": 99.90, "ativo": true, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/produtos/1

# Movimentacao
curl -X POST http://localhost:8080/movimentacoes -H "Content-Type: application/json" -d '{ "tipo": "Tipo exemplo", "quantidade": 10, "dataHora": "2026-03-15T10:30:00", "produto": { "id": 1 } }'
curl http://localhost:8080/movimentacoes
curl http://localhost:8080/movimentacoes/1
curl -X PUT http://localhost:8080/movimentacoes/1 -H "Content-Type: application/json" -d '{ "tipo": "Tipo exemplo", "quantidade": 10, "dataHora": "2026-03-15T10:30:00", "produto": { "id": 1 } }'
curl -X DELETE http://localhost:8080/movimentacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/controleestoque/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
