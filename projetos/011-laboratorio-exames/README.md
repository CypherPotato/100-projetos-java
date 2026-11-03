# 011 · Laboratório de Exames

**Categoria:** Saúde
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Registro de tipos de exame, pacientes e coletas realizadas.

## Entidades

### TipoExame (tabela `tipos_exame`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `preco` | `BigDecimal` |
| `jejum` | `Boolean` |

### Paciente (tabela `pacientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cpf` | `String` |
| `dataNascimento` | `LocalDate` |

### Exame (tabela `exames`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataColeta` | `LocalDateTime` |
| `resultadoLiberado` | `Boolean` |
| `tipoExame` | `TipoExame` (ManyToOne, coluna `tipo_exame_id`) |
| `paciente` | `Paciente` (ManyToOne, coluna `paciente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/tipos-exame` | Lista todos |
| GET | `/tipos-exame/{id}` | Busca por id (404 se não existir) |
| POST | `/tipos-exame` | Cria (201) |
| PUT | `/tipos-exame/{id}` | Atualiza (404 se não existir) |
| DELETE | `/tipos-exame/{id}` | Exclui (204) |
| GET | `/pacientes` | Lista todos |
| GET | `/pacientes/{id}` | Busca por id (404 se não existir) |
| POST | `/pacientes` | Cria (201) |
| PUT | `/pacientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pacientes/{id}` | Exclui (204) |
| GET | `/exames` | Lista todos |
| GET | `/exames?tipoExameId=1` | Lista filtrando por TipoExame |
| GET | `/exames?pacienteId=1` | Lista filtrando por Paciente |
| GET | `/exames/{id}` | Busca por id (404 se não existir) |
| POST | `/exames` | Cria (201) |
| PUT | `/exames/{id}` | Atualiza (404 se não existir) |
| DELETE | `/exames/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/tipos-exame

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): TipoExame → Paciente → Exame.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/tipos-exame -ContentType "application/json" -Body '{ "nome": "Maria Silva", "preco": 99.90, "jejum": true }'
```

```bash
# TipoExame
curl -X POST http://localhost:8080/tipos-exame -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "jejum": true }'
curl http://localhost:8080/tipos-exame
curl http://localhost:8080/tipos-exame/1
curl -X PUT http://localhost:8080/tipos-exame/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "jejum": true }'
curl -X DELETE http://localhost:8080/tipos-exame/1

# Paciente
curl -X POST http://localhost:8080/pacientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "dataNascimento": "2026-03-15" }'
curl http://localhost:8080/pacientes
curl http://localhost:8080/pacientes/1
curl -X PUT http://localhost:8080/pacientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "dataNascimento": "2026-03-15" }'
curl -X DELETE http://localhost:8080/pacientes/1

# Exame
curl -X POST http://localhost:8080/exames -H "Content-Type: application/json" -d '{ "dataColeta": "2026-03-15T10:30:00", "resultadoLiberado": true, "tipoExame": { "id": 1 }, "paciente": { "id": 1 } }'
curl http://localhost:8080/exames
curl http://localhost:8080/exames/1
curl -X PUT http://localhost:8080/exames/1 -H "Content-Type: application/json" -d '{ "dataColeta": "2026-03-15T10:30:00", "resultadoLiberado": true, "tipoExame": { "id": 1 }, "paciente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/exames/1
```

## Estrutura

```
src/main/java/com/exemplo/laboratorioexames/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
