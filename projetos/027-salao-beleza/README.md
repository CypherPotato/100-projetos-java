# 027 · Salão de Beleza

**Categoria:** Serviços
**Lombok:** não · **Camada Service:** sim

Agendamento de atendimentos, clientes e procedimentos de um salão de beleza.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `dataCadastro` | `LocalDate` |

### Procedimento (tabela `procedimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `preco` | `BigDecimal` |
| `duracaoMinutos` | `Integer` |

### Atendimento (tabela `atendimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `observacao` | `String` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |
| `procedimento` | `Procedimento` (ManyToOne, coluna `procedimento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/procedimentos` | Lista todos |
| GET | `/procedimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/procedimentos` | Cria (201) |
| PUT | `/procedimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/procedimentos/{id}` | Exclui (204) |
| GET | `/atendimentos` | Lista todos |
| GET | `/atendimentos?clienteId=1` | Lista filtrando por Cliente |
| GET | `/atendimentos?procedimentoId=1` | Lista filtrando por Procedimento |
| GET | `/atendimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/atendimentos` | Cria (201) |
| PUT | `/atendimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/atendimentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Procedimento → Atendimento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataCadastro": "2026-03-15" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataCadastro": "2026-03-15" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataCadastro": "2026-03-15" }'
curl -X DELETE http://localhost:8080/clientes/1

# Procedimento
curl -X POST http://localhost:8080/procedimentos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "duracaoMinutos": 10 }'
curl http://localhost:8080/procedimentos
curl http://localhost:8080/procedimentos/1
curl -X PUT http://localhost:8080/procedimentos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "duracaoMinutos": 10 }'
curl -X DELETE http://localhost:8080/procedimentos/1

# Atendimento
curl -X POST http://localhost:8080/atendimentos -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "observacao": "Sem observações", "cliente": { "id": 1 }, "procedimento": { "id": 1 } }'
curl http://localhost:8080/atendimentos
curl http://localhost:8080/atendimentos/1
curl -X PUT http://localhost:8080/atendimentos/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "observacao": "Sem observações", "cliente": { "id": 1 }, "procedimento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/atendimentos/1
```

## Estrutura

```
src/main/java/com/exemplo/salaobeleza/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
