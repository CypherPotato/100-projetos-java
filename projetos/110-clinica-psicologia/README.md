# 110 · Clínica de Psicologia

**Categoria:** Saúde
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Agenda de sessões entre psicólogos e pacientes.

## Entidades

### Psicologo (tabela `psicologos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `crp` | `String` |
| `abordagem` | `String` |

### Paciente (tabela `pacientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `dataNascimento` | `LocalDate` |

### Sessao (tabela `sessoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `valor` | `BigDecimal` |
| `online` | `Boolean` |
| `psicologo` | `Psicologo` (ManyToOne, coluna `psicologo_id`) |
| `paciente` | `Paciente` (ManyToOne, coluna `paciente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/psicologos` | Lista todos |
| GET | `/psicologos/{id}` | Busca por id (404 se não existir) |
| POST | `/psicologos` | Cria (201) |
| PUT | `/psicologos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/psicologos/{id}` | Exclui (204) |
| GET | `/pacientes` | Lista todos |
| GET | `/pacientes/{id}` | Busca por id (404 se não existir) |
| POST | `/pacientes` | Cria (201) |
| PUT | `/pacientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pacientes/{id}` | Exclui (204) |
| GET | `/sessoes` | Lista todos |
| GET | `/sessoes?psicologoId=1` | Lista filtrando por Psicologo |
| GET | `/sessoes?pacienteId=1` | Lista filtrando por Paciente |
| GET | `/sessoes/{id}` | Busca por id (404 se não existir) |
| POST | `/sessoes` | Cria (201) |
| PUT | `/sessoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/sessoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\clinica-psicologia.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/psicologos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/clinica-psicologia`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Psicologo → Paciente → Sessao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/psicologos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "crp": "Crp exemplo", "abordagem": "Abordagem exemplo" }'
```

```bash
# Psicologo
curl -X POST http://localhost:8080/psicologos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "crp": "Crp exemplo", "abordagem": "Abordagem exemplo" }'
curl http://localhost:8080/psicologos
curl http://localhost:8080/psicologos/1
curl -X PUT http://localhost:8080/psicologos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "crp": "Crp exemplo", "abordagem": "Abordagem exemplo" }'
curl -X DELETE http://localhost:8080/psicologos/1

# Paciente
curl -X POST http://localhost:8080/pacientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataNascimento": "2026-03-15" }'
curl http://localhost:8080/pacientes
curl http://localhost:8080/pacientes/1
curl -X PUT http://localhost:8080/pacientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataNascimento": "2026-03-15" }'
curl -X DELETE http://localhost:8080/pacientes/1

# Sessao
curl -X POST http://localhost:8080/sessoes -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "valor": 99.90, "online": true, "psicologo": { "id": 1 }, "paciente": { "id": 1 } }'
curl http://localhost:8080/sessoes
curl http://localhost:8080/sessoes/1
curl -X PUT http://localhost:8080/sessoes/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "valor": 99.90, "online": true, "psicologo": { "id": 1 }, "paciente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/sessoes/1
```

## Estrutura

```
src/main/java/com/exemplo/clinicapsicologia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
