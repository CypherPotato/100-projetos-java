# 006 · Petshop

**Categoria:** Comércio
**Lombok:** não · **Camada Service:** sim

Cadastro de tutores, animais e serviços de banho e tosa.

## Entidades

### Tutor (tabela `tutores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Animal (tabela `animais`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `especie` | `String` |
| `peso` | `Double` |
| `tutor` | `Tutor` (ManyToOne, coluna `tutor_id`) |

### Atendimento (tabela `atendimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `servico` | `String` |
| `valor` | `BigDecimal` |
| `dataHora` | `LocalDateTime` |
| `animal` | `Animal` (ManyToOne, coluna `animal_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/tutores` | Lista todos |
| GET | `/tutores/{id}` | Busca por id (404 se não existir) |
| POST | `/tutores` | Cria (201) |
| PUT | `/tutores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/tutores/{id}` | Exclui (204) |
| GET | `/animais` | Lista todos |
| GET | `/animais?tutorId=1` | Lista filtrando por Tutor |
| GET | `/animais/{id}` | Busca por id (404 se não existir) |
| POST | `/animais` | Cria (201) |
| PUT | `/animais/{id}` | Atualiza (404 se não existir) |
| DELETE | `/animais/{id}` | Exclui (204) |
| GET | `/atendimentos` | Lista todos |
| GET | `/atendimentos?animalId=1` | Lista filtrando por Animal |
| GET | `/atendimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/atendimentos` | Cria (201) |
| PUT | `/atendimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/atendimentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/tutores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Tutor → Animal → Atendimento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/tutores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Tutor
curl -X POST http://localhost:8080/tutores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/tutores
curl http://localhost:8080/tutores/1
curl -X PUT http://localhost:8080/tutores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/tutores/1

# Animal
curl -X POST http://localhost:8080/animais -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "especie": "Especie exemplo", "peso": 12.5, "tutor": { "id": 1 } }'
curl http://localhost:8080/animais
curl http://localhost:8080/animais/1
curl -X PUT http://localhost:8080/animais/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "especie": "Especie exemplo", "peso": 12.5, "tutor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/animais/1

# Atendimento
curl -X POST http://localhost:8080/atendimentos -H "Content-Type: application/json" -d '{ "servico": "Servico exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "animal": { "id": 1 } }'
curl http://localhost:8080/atendimentos
curl http://localhost:8080/atendimentos/1
curl -X PUT http://localhost:8080/atendimentos/1 -H "Content-Type: application/json" -d '{ "servico": "Servico exemplo", "valor": 99.90, "dataHora": "2026-03-15T10:30:00", "animal": { "id": 1 } }'
curl -X DELETE http://localhost:8080/atendimentos/1
```

## Estrutura

```
src/main/java/com/exemplo/petshop/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
