# 033 · Chaveiro

**Categoria:** Serviços
**Lombok:** não · **Camada Service:** sim

Controle de clientes e chamados de atendimento de um chaveiro.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `endereco` | `String` |

### ChamadoChaveiro (tabela `chamados_chaveiro`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `tipoServico` | `String` |
| `valor` | `BigDecimal` |
| `dataHora` | `LocalDateTime` |
| `urgente` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/chamados-chaveiro` | Lista todos |
| GET | `/chamados-chaveiro?clienteId=1` | Lista filtrando por Cliente |
| GET | `/chamados-chaveiro/{id}` | Busca por id (404 se não existir) |
| POST | `/chamados-chaveiro` | Cria (201) |
| PUT | `/chamados-chaveiro/{id}` | Atualiza (404 se não existir) |
| DELETE | `/chamados-chaveiro/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → ChamadoChaveiro.

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

# ChamadoChaveiro
curl -X POST http://localhost:8080/chamados-chaveiro -H "Content-Type: application/json" -d '{ "tipoServico": "TipoServico exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "urgente": true, "cliente": { "id": 1 } }'
curl http://localhost:8080/chamados-chaveiro
curl http://localhost:8080/chamados-chaveiro/1
curl -X PUT http://localhost:8080/chamados-chaveiro/1 -H "Content-Type: application/json" -d '{ "tipoServico": "TipoServico exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "urgente": true, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/chamados-chaveiro/1
```

## Estrutura

```
src/main/java/com/exemplo/chaveiro/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
