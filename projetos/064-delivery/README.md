# 064 · Delivery de Comida

**Categoria:** Alimentação
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Entregas de restaurantes parceiros realizadas por entregadores.

## Entidades

### Restaurante (tabela `restaurantes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `tipoCozinha` | `String` |
| `taxaEntrega` | `BigDecimal` |
| `aberto` | `Boolean` |

### Entregador (tabela `entregadores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `veiculo` | `String` |
| `telefone` | `String` |

### Entrega (tabela `entregas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `enderecoDestino` | `String` |
| `valor` | `BigDecimal` |
| `dataHora` | `LocalDateTime` |
| `entregue` | `Boolean` |
| `restaurante` | `Restaurante` (ManyToOne, coluna `restaurante_id`) |
| `entregador` | `Entregador` (ManyToOne, coluna `entregador_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/restaurantes` | Lista todos |
| GET | `/restaurantes/{id}` | Busca por id (404 se não existir) |
| POST | `/restaurantes` | Cria (201) |
| PUT | `/restaurantes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/restaurantes/{id}` | Exclui (204) |
| GET | `/entregadores` | Lista todos |
| GET | `/entregadores/{id}` | Busca por id (404 se não existir) |
| POST | `/entregadores` | Cria (201) |
| PUT | `/entregadores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/entregadores/{id}` | Exclui (204) |
| GET | `/entregas` | Lista todos |
| GET | `/entregas?restauranteId=1` | Lista filtrando por Restaurante |
| GET | `/entregas?entregadorId=1` | Lista filtrando por Entregador |
| GET | `/entregas/{id}` | Busca por id (404 se não existir) |
| POST | `/entregas` | Cria (201) |
| PUT | `/entregas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/entregas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/restaurantes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Restaurante → Entregador → Entrega.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/restaurantes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "tipoCozinha": "TipoCozinha exemplo", "taxaEntrega": 99.90, "aberto": true }'
```

```bash
# Restaurante
curl -X POST http://localhost:8080/restaurantes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tipoCozinha": "TipoCozinha exemplo", "taxaEntrega": 99.90, "aberto": true }'
curl http://localhost:8080/restaurantes
curl http://localhost:8080/restaurantes/1
curl -X PUT http://localhost:8080/restaurantes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tipoCozinha": "TipoCozinha exemplo", "taxaEntrega": 99.90, "aberto": true }'
curl -X DELETE http://localhost:8080/restaurantes/1

# Entregador
curl -X POST http://localhost:8080/entregadores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "veiculo": "Veiculo exemplo", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/entregadores
curl http://localhost:8080/entregadores/1
curl -X PUT http://localhost:8080/entregadores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "veiculo": "Veiculo exemplo", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/entregadores/1

# Entrega
curl -X POST http://localhost:8080/entregas -H "Content-Type: application/json" -d '{ "enderecoDestino": "EnderecoDestino exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "entregue": true, "restaurante": { "id": 1 }, "entregador": { "id": 1 } }'
curl http://localhost:8080/entregas
curl http://localhost:8080/entregas/1
curl -X PUT http://localhost:8080/entregas/1 -H "Content-Type: application/json" -d '{ "enderecoDestino": "EnderecoDestino exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "entregue": true, "restaurante": { "id": 1 }, "entregador": { "id": 1 } }'
curl -X DELETE http://localhost:8080/entregas/1
```

## Estrutura

```
src/main/java/com/exemplo/delivery/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
