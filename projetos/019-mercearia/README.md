# 019 · Mercearia

**Categoria:** Comércio
**Lombok:** sim · **Camada Service:** sim

Controle de fornecedores e produtos de uma mercearia de bairro.

## Entidades

### Fornecedor (tabela `fornecedores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Produto (tabela `produtos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `preco` | `BigDecimal` |
| `quantidade` | `Integer` |
| `validade` | `LocalDate` |
| `fornecedor` | `Fornecedor` (ManyToOne, coluna `fornecedor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/fornecedores` | Lista todos |
| GET | `/fornecedores/{id}` | Busca por id (404 se não existir) |
| POST | `/fornecedores` | Cria (201) |
| PUT | `/fornecedores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fornecedores/{id}` | Exclui (204) |
| GET | `/produtos` | Lista todos |
| GET | `/produtos?fornecedorId=1` | Lista filtrando por Fornecedor |
| GET | `/produtos/{id}` | Busca por id (404 se não existir) |
| POST | `/produtos` | Cria (201) |
| PUT | `/produtos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/produtos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/fornecedores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Fornecedor → Produto.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/fornecedores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Fornecedor
curl -X POST http://localhost:8080/fornecedores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/fornecedores
curl http://localhost:8080/fornecedores/1
curl -X PUT http://localhost:8080/fornecedores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/fornecedores/1

# Produto
curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "quantidade": 10, "validade": "2026-03-15", "fornecedor": { "id": 1 } }'
curl http://localhost:8080/produtos
curl http://localhost:8080/produtos/1
curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "quantidade": 10, "validade": "2026-03-15", "fornecedor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/produtos/1
```

## Estrutura

```
src/main/java/com/exemplo/mercearia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
