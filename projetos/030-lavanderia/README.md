# 030 · Lavanderia

**Categoria:** Serviços
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** sim

Registro de clientes e pedidos de lavagem de roupas de uma lavanderia.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### PedidoLavagem (tabela `pedidos_lavagem`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `quantidadePecas` | `Integer` |
| `valorTotal` | `BigDecimal` |
| `dataRetirada` | `LocalDate` |
| `entregue` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/pedidos-lavagem` | Lista todos |
| GET | `/pedidos-lavagem?clienteId=1` | Lista filtrando por Cliente |
| GET | `/pedidos-lavagem/{id}` | Busca por id (404 se não existir) |
| POST | `/pedidos-lavagem` | Cria (201) |
| PUT | `/pedidos-lavagem/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pedidos-lavagem/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → PedidoLavagem.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/clientes/1

# PedidoLavagem
curl -X POST http://localhost:8080/pedidos-lavagem -H "Content-Type: application/json" -d '{ "quantidadePecas": 10, "valorTotal": 99.90, "dataRetirada": "2026-03-15", "entregue": true, "cliente": { "id": 1 } }'
curl http://localhost:8080/pedidos-lavagem
curl http://localhost:8080/pedidos-lavagem/1
curl -X PUT http://localhost:8080/pedidos-lavagem/1 -H "Content-Type: application/json" -d '{ "quantidadePecas": 10, "valorTotal": 99.90, "dataRetirada": "2026-03-15", "entregue": true, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/pedidos-lavagem/1
```

## Estrutura

```
src/main/java/com/exemplo/lavanderia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
