# 088 · Hortifruti

**Categoria:** Agro e Logística
**Lombok:** sim · **Camada Service:** não

Cadastro de produtores e dos hortifrútis que eles fornecem.

## Entidades

### Produtor (tabela `produtores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cidade` | `String` |
| `telefone` | `String` |

### Produto (tabela `produtos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `precoKg` | `BigDecimal` |
| `estoqueKg` | `Double` |
| `organico` | `Boolean` |
| `produtor` | `Produtor` (ManyToOne, coluna `produtor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/produtores` | Lista todos |
| GET | `/produtores/{id}` | Busca por id (404 se não existir) |
| POST | `/produtores` | Cria (201) |
| PUT | `/produtores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/produtores/{id}` | Exclui (204) |
| GET | `/produtos` | Lista todos |
| GET | `/produtos?produtorId=1` | Lista filtrando por Produtor |
| GET | `/produtos/{id}` | Busca por id (404 se não existir) |
| POST | `/produtos` | Cria (201) |
| PUT | `/produtos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/produtos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/produtores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Produtor → Produto.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/produtores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cidade": "São Paulo", "telefone": "(11) 99999-0000" }'
```

```bash
# Produtor
curl -X POST http://localhost:8080/produtores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/produtores
curl http://localhost:8080/produtores/1
curl -X PUT http://localhost:8080/produtores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/produtores/1

# Produto
curl -X POST http://localhost:8080/produtos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "precoKg": 99.90, "estoqueKg": 12.5, "organico": true, "produtor": { "id": 1 } }'
curl http://localhost:8080/produtos
curl http://localhost:8080/produtos/1
curl -X PUT http://localhost:8080/produtos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "precoKg": 99.90, "estoqueKg": 12.5, "organico": true, "produtor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/produtos/1
```

## Estrutura

```
src/main/java/com/exemplo/hortifruti/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
