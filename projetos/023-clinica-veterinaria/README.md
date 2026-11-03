# 023 · Clínica Veterinária

**Categoria:** Saúde
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Controle de animais atendidos e suas vacinas aplicadas.

## Entidades

### Animal (tabela `animais`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `especie` | `String` |
| `peso` | `Double` |

### Vacina (tabela `vacinas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataAplicacao` | `LocalDate` |
| `proximaDose` | `LocalDate` |
| `animal` | `Animal` (ManyToOne, coluna `animal_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/animais` | Lista todos |
| GET | `/animais/{id}` | Busca por id (404 se não existir) |
| POST | `/animais` | Cria (201) |
| PUT | `/animais/{id}` | Atualiza (404 se não existir) |
| DELETE | `/animais/{id}` | Exclui (204) |
| GET | `/vacinas` | Lista todos |
| GET | `/vacinas?animalId=1` | Lista filtrando por Animal |
| GET | `/vacinas/{id}` | Busca por id (404 se não existir) |
| POST | `/vacinas` | Cria (201) |
| PUT | `/vacinas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vacinas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/animais

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Animal → Vacina.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/animais -ContentType "application/json" -Body '{ "nome": "Maria Silva", "especie": "Especie exemplo", "peso": 12.5 }'
```

```bash
# Animal
curl -X POST http://localhost:8080/animais -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "especie": "Especie exemplo", "peso": 12.5 }'
curl http://localhost:8080/animais
curl http://localhost:8080/animais/1
curl -X PUT http://localhost:8080/animais/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "especie": "Especie exemplo", "peso": 12.5 }'
curl -X DELETE http://localhost:8080/animais/1

# Vacina
curl -X POST http://localhost:8080/vacinas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataAplicacao": "2026-03-15", "proximaDose": "2026-03-15", "animal": { "id": 1 } }'
curl http://localhost:8080/vacinas
curl http://localhost:8080/vacinas/1
curl -X PUT http://localhost:8080/vacinas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataAplicacao": "2026-03-15", "proximaDose": "2026-03-15", "animal": { "id": 1 } }'
curl -X DELETE http://localhost:8080/vacinas/1
```

## Estrutura

```
src/main/java/com/exemplo/clinicaveterinaria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
