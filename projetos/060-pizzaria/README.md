# 060 · Pizzaria

**Categoria:** Alimentação
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** sim

Registro de clientes, sabores de pizza e pedidos de uma pizzaria.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `endereco` | `String` |

### Pizza (tabela `pizzas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `sabor` | `String` |
| `tamanho` | `String` |
| `preco` | `BigDecimal` |

### Pedido (tabela `pedidos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `quantidade` | `Integer` |
| `dataPedido` | `LocalDateTime` |
| `entregue` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |
| `pizza` | `Pizza` (ManyToOne, coluna `pizza_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/pizzas` | Lista todos |
| GET | `/pizzas/{id}` | Busca por id (404 se não existir) |
| POST | `/pizzas` | Cria (201) |
| PUT | `/pizzas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pizzas/{id}` | Exclui (204) |
| GET | `/pedidos` | Lista todos |
| GET | `/pedidos?clienteId=1` | Lista filtrando por Cliente |
| GET | `/pedidos?pizzaId=1` | Lista filtrando por Pizza |
| GET | `/pedidos/{id}` | Busca por id (404 se não existir) |
| POST | `/pedidos` | Cria (201) |
| PUT | `/pedidos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pedidos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Pizza → Pedido.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
curl -X DELETE http://localhost:8080/clientes/1

# Pizza
curl -X POST http://localhost:8080/pizzas -H "Content-Type: application/json" -d '{ "sabor": "Sabor exemplo", "tamanho": "Tamanho exemplo", "preco": 99.90 }'
curl http://localhost:8080/pizzas
curl http://localhost:8080/pizzas/1
curl -X PUT http://localhost:8080/pizzas/1 -H "Content-Type: application/json" -d '{ "sabor": "Sabor exemplo", "tamanho": "Tamanho exemplo", "preco": 99.90 }'
curl -X DELETE http://localhost:8080/pizzas/1

# Pedido
curl -X POST http://localhost:8080/pedidos -H "Content-Type: application/json" -d '{ "quantidade": 10, "dataPedido": "2026-03-15T10:30:00", "entregue": true, "cliente": { "id": 1 }, "pizza": { "id": 1 } }'
curl http://localhost:8080/pedidos
curl http://localhost:8080/pedidos/1
curl -X PUT http://localhost:8080/pedidos/1 -H "Content-Type: application/json" -d '{ "quantidade": 10, "dataPedido": "2026-03-15T10:30:00", "entregue": true, "cliente": { "id": 1 }, "pizza": { "id": 1 } }'
curl -X DELETE http://localhost:8080/pedidos/1
```

## Estrutura

```
src/main/java/com/exemplo/pizzaria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
