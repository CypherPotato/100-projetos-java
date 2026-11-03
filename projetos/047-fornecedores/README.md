# 047 · Fornecedores

**Categoria:** Gestão
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Cadastro de fornecedores por categoria e das cotações de preço recebidas.

## Entidades

### CategoriaFornecedor (tabela `categorias_fornecedor`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Fornecedor (tabela `fornecedores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `razaoSocial` | `String` |
| `cnpj` | `String` |
| `telefone` | `String` |
| `ativo` | `Boolean` |
| `categoria` | `CategoriaFornecedor` (ManyToOne, coluna `categoria_id`) |

### Cotacao (tabela `cotacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `produto` | `String` |
| `preco` | `BigDecimal` |
| `quantidade` | `Integer` |
| `dataCotacao` | `LocalDate` |
| `fornecedor` | `Fornecedor` (ManyToOne, coluna `fornecedor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias-fornecedor` | Lista todos |
| GET | `/categorias-fornecedor/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias-fornecedor` | Cria (201) |
| PUT | `/categorias-fornecedor/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias-fornecedor/{id}` | Exclui (204) |
| GET | `/fornecedores` | Lista todos |
| GET | `/fornecedores?categoriaId=1` | Lista filtrando por CategoriaFornecedor |
| GET | `/fornecedores/{id}` | Busca por id (404 se não existir) |
| POST | `/fornecedores` | Cria (201) |
| PUT | `/fornecedores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fornecedores/{id}` | Exclui (204) |
| GET | `/cotacoes` | Lista todos |
| GET | `/cotacoes?fornecedorId=1` | Lista filtrando por Fornecedor |
| GET | `/cotacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/cotacoes` | Cria (201) |
| PUT | `/cotacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cotacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/categorias-fornecedor

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): CategoriaFornecedor → Fornecedor → Cotacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/categorias-fornecedor -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# CategoriaFornecedor
curl -X POST http://localhost:8080/categorias-fornecedor -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/categorias-fornecedor
curl http://localhost:8080/categorias-fornecedor/1
curl -X PUT http://localhost:8080/categorias-fornecedor/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/categorias-fornecedor/1

# Fornecedor
curl -X POST http://localhost:8080/fornecedores -H "Content-Type: application/json" -d '{ "razaoSocial": "RazaoSocial exemplo", "cnpj": "12.345.678/0001-00", "telefone": "(11) 99999-0000", "ativo": true, "categoria": { "id": 1 } }'
curl http://localhost:8080/fornecedores
curl http://localhost:8080/fornecedores/1
curl -X PUT http://localhost:8080/fornecedores/1 -H "Content-Type: application/json" -d '{ "razaoSocial": "RazaoSocial exemplo", "cnpj": "12.345.678/0001-00", "telefone": "(11) 99999-0000", "ativo": true, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/fornecedores/1

# Cotacao
curl -X POST http://localhost:8080/cotacoes -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "preco": 99.90, "quantidade": 10, "dataCotacao": "2026-03-15", "fornecedor": { "id": 1 } }'
curl http://localhost:8080/cotacoes
curl http://localhost:8080/cotacoes/1
curl -X PUT http://localhost:8080/cotacoes/1 -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "preco": 99.90, "quantidade": 10, "dataCotacao": "2026-03-15", "fornecedor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/cotacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/fornecedores/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
