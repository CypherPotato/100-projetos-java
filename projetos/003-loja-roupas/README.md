# 003 · Loja de Roupas

**Categoria:** Comércio
**Lombok:** sim · **Camada Service:** sim

Cadastro de categorias e peças de roupa com preço e estoque.

## Entidades

### Categoria (tabela `categorias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `ativa` | `Boolean` |

### Roupa (tabela `roupas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `tamanho` | `String` |
| `preco` | `BigDecimal` |
| `estoque` | `Integer` |
| `categoria` | `Categoria` (ManyToOne, coluna `categoria_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias` | Lista todos |
| GET | `/categorias/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias` | Cria (201) |
| PUT | `/categorias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias/{id}` | Exclui (204) |
| GET | `/roupas` | Lista todos |
| GET | `/roupas?categoriaId=1` | Lista filtrando por Categoria |
| GET | `/roupas/{id}` | Busca por id (404 se não existir) |
| POST | `/roupas` | Cria (201) |
| PUT | `/roupas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/roupas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/categorias

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Categoria → Roupa.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/categorias -ContentType "application/json" -Body '{ "nome": "Maria Silva", "ativa": true }'
```

```bash
# Categoria
curl -X POST http://localhost:8080/categorias -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "ativa": true }'
curl http://localhost:8080/categorias
curl http://localhost:8080/categorias/1
curl -X PUT http://localhost:8080/categorias/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "ativa": true }'
curl -X DELETE http://localhost:8080/categorias/1

# Roupa
curl -X POST http://localhost:8080/roupas -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "tamanho": "Tamanho exemplo", "preco": 99.90, "estoque": 10, "categoria": { "id": 1 } }'
curl http://localhost:8080/roupas
curl http://localhost:8080/roupas/1
curl -X PUT http://localhost:8080/roupas/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "tamanho": "Tamanho exemplo", "preco": 99.90, "estoque": 10, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/roupas/1
```

## Estrutura

```
src/main/java/com/exemplo/lojaroupas/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
