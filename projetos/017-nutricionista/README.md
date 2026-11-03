# 017 · Nutricionista

**Categoria:** Saúde
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Acompanhamento de pacientes e avaliações nutricionais.

## Entidades

### Paciente (tabela `pacientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataNascimento` | `LocalDate` |
| `objetivo` | `String` |

### Avaliacao (tabela `avaliacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataAvaliacao` | `LocalDate` |
| `pesoKg` | `Double` |
| `alturaM` | `Double` |
| `observacoes` | `String` |
| `paciente` | `Paciente` (ManyToOne, coluna `paciente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/pacientes` | Lista todos |
| GET | `/pacientes/{id}` | Busca por id (404 se não existir) |
| POST | `/pacientes` | Cria (201) |
| PUT | `/pacientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pacientes/{id}` | Exclui (204) |
| GET | `/avaliacoes` | Lista todos |
| GET | `/avaliacoes?pacienteId=1` | Lista filtrando por Paciente |
| GET | `/avaliacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/avaliacoes` | Cria (201) |
| PUT | `/avaliacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/avaliacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/pacientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Paciente → Avaliacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/pacientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "objetivo": "Objetivo exemplo" }'
```

```bash
# Paciente
curl -X POST http://localhost:8080/pacientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "objetivo": "Objetivo exemplo" }'
curl http://localhost:8080/pacientes
curl http://localhost:8080/pacientes/1
curl -X PUT http://localhost:8080/pacientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "objetivo": "Objetivo exemplo" }'
curl -X DELETE http://localhost:8080/pacientes/1

# Avaliacao
curl -X POST http://localhost:8080/avaliacoes -H "Content-Type: application/json" -d '{ "dataAvaliacao": "2026-03-15", "pesoKg": 12.5, "alturaM": 12.5, "observacoes": "Sem observações", "paciente": { "id": 1 } }'
curl http://localhost:8080/avaliacoes
curl http://localhost:8080/avaliacoes/1
curl -X PUT http://localhost:8080/avaliacoes/1 -H "Content-Type: application/json" -d '{ "dataAvaliacao": "2026-03-15", "pesoKg": 12.5, "alturaM": 12.5, "observacoes": "Sem observações", "paciente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/avaliacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/nutricionista/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
