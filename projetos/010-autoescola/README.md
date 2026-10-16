# 010 · Autoescola

**Categoria:** Educação
**Lombok:** não · **Camada Service:** não

Agendamento de aulas práticas com instrutores e veículos.

## Entidades

### Instrutor (tabela `instrutores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `categoriaHabilitacao` | `String` |

### Veiculo (tabela `veiculos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `modelo` | `String` |
| `anoFabricacao` | `Integer` |

### Aula (tabela `aulas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `duracaoMinutos` | `Integer` |
| `realizada` | `Boolean` |
| `instrutor` | `Instrutor` (ManyToOne, coluna `instrutor_id`) |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/instrutores` | Lista todos |
| GET | `/instrutores/{id}` | Busca por id (404 se não existir) |
| POST | `/instrutores` | Cria (201) |
| PUT | `/instrutores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/instrutores/{id}` | Exclui (204) |
| GET | `/veiculos` | Lista todos |
| GET | `/veiculos/{id}` | Busca por id (404 se não existir) |
| POST | `/veiculos` | Cria (201) |
| PUT | `/veiculos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/veiculos/{id}` | Exclui (204) |
| GET | `/aulas` | Lista todos |
| GET | `/aulas?instrutorId=1` | Lista filtrando por Instrutor |
| GET | `/aulas?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/aulas/{id}` | Busca por id (404 se não existir) |
| POST | `/aulas` | Cria (201) |
| PUT | `/aulas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aulas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/instrutores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Instrutor → Veiculo → Aula.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/instrutores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "categoriaHabilitacao": "CategoriaHabilitacao exemplo" }'
```

```bash
# Instrutor
curl -X POST http://localhost:8080/instrutores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "categoriaHabilitacao": "CategoriaHabilitacao exemplo" }'
curl http://localhost:8080/instrutores
curl http://localhost:8080/instrutores/1
curl -X PUT http://localhost:8080/instrutores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "categoriaHabilitacao": "CategoriaHabilitacao exemplo" }'
curl -X DELETE http://localhost:8080/instrutores/1

# Veiculo
curl -X POST http://localhost:8080/veiculos -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10 }'
curl http://localhost:8080/veiculos
curl http://localhost:8080/veiculos/1
curl -X PUT http://localhost:8080/veiculos/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "anoFabricacao": 10 }'
curl -X DELETE http://localhost:8080/veiculos/1

# Aula
curl -X POST http://localhost:8080/aulas -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "duracaoMinutos": 10, "realizada": true, "instrutor": { "id": 1 }, "veiculo": { "id": 1 } }'
curl http://localhost:8080/aulas
curl http://localhost:8080/aulas/1
curl -X PUT http://localhost:8080/aulas/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "duracaoMinutos": 10, "realizada": true, "instrutor": { "id": 1 }, "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aulas/1
```

## Estrutura

```
src/main/java/com/exemplo/autoescola/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
