# 002 · Clínica Médica

**Categoria:** Saúde
**Lombok:** não · **Camada Service:** sim

Agendamento de consultas entre médicos e pacientes de uma clínica.

## Entidades

### Medico (tabela `medicos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `crm` | `String` |
| `especialidade` | `String` |

### Paciente (tabela `pacientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataNascimento` | `LocalDate` |
| `telefone` | `String` |

### Consulta (tabela `consultas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `valor` | `BigDecimal` |
| `retorno` | `Boolean` |
| `medico` | `Medico` (ManyToOne, coluna `medico_id`) |
| `paciente` | `Paciente` (ManyToOne, coluna `paciente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/medicos` | Lista todos |
| GET | `/medicos/{id}` | Busca por id (404 se não existir) |
| POST | `/medicos` | Cria (201) |
| PUT | `/medicos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/medicos/{id}` | Exclui (204) |
| GET | `/pacientes` | Lista todos |
| GET | `/pacientes/{id}` | Busca por id (404 se não existir) |
| POST | `/pacientes` | Cria (201) |
| PUT | `/pacientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pacientes/{id}` | Exclui (204) |
| GET | `/consultas` | Lista todos |
| GET | `/consultas?medicoId=1` | Lista filtrando por Medico |
| GET | `/consultas?pacienteId=1` | Lista filtrando por Paciente |
| GET | `/consultas/{id}` | Busca por id (404 se não existir) |
| POST | `/consultas` | Cria (201) |
| PUT | `/consultas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/consultas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/medicos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Medico → Paciente → Consulta.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/medicos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "crm": "Crm exemplo", "especialidade": "Especialidade exemplo" }'
```

```bash
# Medico
curl -X POST http://localhost:8080/medicos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "crm": "Crm exemplo", "especialidade": "Especialidade exemplo" }'
curl http://localhost:8080/medicos
curl http://localhost:8080/medicos/1
curl -X PUT http://localhost:8080/medicos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "crm": "Crm exemplo", "especialidade": "Especialidade exemplo" }'
curl -X DELETE http://localhost:8080/medicos/1

# Paciente
curl -X POST http://localhost:8080/pacientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/pacientes
curl http://localhost:8080/pacientes/1
curl -X PUT http://localhost:8080/pacientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/pacientes/1

# Consulta
curl -X POST http://localhost:8080/consultas -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "valor": 99.90, "retorno": true, "medico": { "id": 1 }, "paciente": { "id": 1 } }'
curl http://localhost:8080/consultas
curl http://localhost:8080/consultas/1
curl -X PUT http://localhost:8080/consultas/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "valor": 99.90, "retorno": true, "medico": { "id": 1 }, "paciente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/consultas/1
```

## Estrutura

```
src/main/java/com/exemplo/clinicamedica/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
