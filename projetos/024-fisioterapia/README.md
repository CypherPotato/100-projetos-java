# 024 · Clínica de Fisioterapia

**Categoria:** Saúde
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** sim

Agendamento de sessões de fisioterapia com fisioterapeutas.

## Entidades

### Fisioterapeuta (tabela `fisioterapeutas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `crefito` | `String` |

### Sessao (tabela `sessoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `paciente` | `String` |
| `dataHora` | `LocalDateTime` |
| `duracaoMinutos` | `Integer` |
| `valor` | `BigDecimal` |
| `fisioterapeuta` | `Fisioterapeuta` (ManyToOne, coluna `fisioterapeuta_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/fisioterapeutas` | Lista todos |
| GET | `/fisioterapeutas/{id}` | Busca por id (404 se não existir) |
| POST | `/fisioterapeutas` | Cria (201) |
| PUT | `/fisioterapeutas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fisioterapeutas/{id}` | Exclui (204) |
| GET | `/sessoes` | Lista todos |
| GET | `/sessoes?fisioterapeutaId=1` | Lista filtrando por Fisioterapeuta |
| GET | `/sessoes/{id}` | Busca por id (404 se não existir) |
| POST | `/sessoes` | Cria (201) |
| PUT | `/sessoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/sessoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/fisioterapeutas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Fisioterapeuta → Sessao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/fisioterapeutas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "crefito": "Crefito exemplo" }'
```

```bash
# Fisioterapeuta
curl -X POST http://localhost:8080/fisioterapeutas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "crefito": "Crefito exemplo" }'
curl http://localhost:8080/fisioterapeutas
curl http://localhost:8080/fisioterapeutas/1
curl -X PUT http://localhost:8080/fisioterapeutas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "crefito": "Crefito exemplo" }'
curl -X DELETE http://localhost:8080/fisioterapeutas/1

# Sessao
curl -X POST http://localhost:8080/sessoes -H "Content-Type: application/json" -d '{ "paciente": "Paciente exemplo", "dataHora": "2026-03-15T10:30:00", "duracaoMinutos": 10, "valor": 99.90, "fisioterapeuta": { "id": 1 } }'
curl http://localhost:8080/sessoes
curl http://localhost:8080/sessoes/1
curl -X PUT http://localhost:8080/sessoes/1 -H "Content-Type: application/json" -d '{ "paciente": "Paciente exemplo", "dataHora": "2026-03-15T10:30:00", "duracaoMinutos": 10, "valor": 99.90, "fisioterapeuta": { "id": 1 } }'
curl -X DELETE http://localhost:8080/sessoes/1
```

## Estrutura

```
src/main/java/com/exemplo/fisioterapia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
