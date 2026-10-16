# 099 · Agência de Viagens

**Categoria:** Serviços
**Lombok:** não · **Camada Service:** sim

Venda de pacotes turísticos para clientes de uma agência de viagens.

## Entidades

### Pacote (tabela `pacotes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `destino` | `String` |
| `diasDuracao` | `Integer` |
| `preco` | `BigDecimal` |
| `dataSaida` | `LocalDate` |

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `telefone` | `String` |

### Contratacao (tabela `contratacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataContratacao` | `LocalDateTime` |
| `passageiros` | `Integer` |
| `valorPago` | `BigDecimal` |
| `pacote` | `Pacote` (ManyToOne, coluna `pacote_id`) |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/pacotes` | Lista todos |
| GET | `/pacotes/{id}` | Busca por id (404 se não existir) |
| POST | `/pacotes` | Cria (201) |
| PUT | `/pacotes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pacotes/{id}` | Exclui (204) |
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/contratacoes` | Lista todos |
| GET | `/contratacoes?pacoteId=1` | Lista filtrando por Pacote |
| GET | `/contratacoes?clienteId=1` | Lista filtrando por Cliente |
| GET | `/contratacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/contratacoes` | Cria (201) |
| PUT | `/contratacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/contratacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/pacotes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Pacote → Cliente → Contratacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/pacotes -ContentType "application/json" -Body '{ "destino": "Destino exemplo", "diasDuracao": 10, "preco": 99.90, "dataSaida": "2026-03-15" }'
```

```bash
# Pacote
curl -X POST http://localhost:8080/pacotes -H "Content-Type: application/json" -d '{ "destino": "Destino exemplo", "diasDuracao": 10, "preco": 99.90, "dataSaida": "2026-03-15" }'
curl http://localhost:8080/pacotes
curl http://localhost:8080/pacotes/1
curl -X PUT http://localhost:8080/pacotes/1 -H "Content-Type: application/json" -d '{ "destino": "Destino exemplo", "diasDuracao": 10, "preco": 99.90, "dataSaida": "2026-03-15" }'
curl -X DELETE http://localhost:8080/pacotes/1

# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/clientes/1

# Contratacao
curl -X POST http://localhost:8080/contratacoes -H "Content-Type: application/json" -d '{ "dataContratacao": "2026-03-15T10:30:00", "passageiros": 10, "valorPago": 99.90, "pacote": { "id": 1 }, "cliente": { "id": 1 } }'
curl http://localhost:8080/contratacoes
curl http://localhost:8080/contratacoes/1
curl -X PUT http://localhost:8080/contratacoes/1 -H "Content-Type: application/json" -d '{ "dataContratacao": "2026-03-15T10:30:00", "passageiros": 10, "valorPago": 99.90, "pacote": { "id": 1 }, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/contratacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/agenciaviagens/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
