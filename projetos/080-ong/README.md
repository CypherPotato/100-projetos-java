# 080 · ONG

**Categoria:** Comunidade
**Lombok:** sim · **Camada Service:** não

Gestão das ações sociais de uma ONG e dos voluntários participantes.

## Entidades

### Acao (tabela `acoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `local` | `String` |
| `dataAcao` | `LocalDate` |
| `vagas` | `Integer` |

### Voluntario (tabela `voluntarios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `horasDedicadas` | `Integer` |
| `ativo` | `Boolean` |
| `acao` | `Acao` (ManyToOne, coluna `acao_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/acoes` | Lista todos |
| GET | `/acoes/{id}` | Busca por id (404 se não existir) |
| POST | `/acoes` | Cria (201) |
| PUT | `/acoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/acoes/{id}` | Exclui (204) |
| GET | `/voluntarios` | Lista todos |
| GET | `/voluntarios?acaoId=1` | Lista filtrando por Acao |
| GET | `/voluntarios/{id}` | Busca por id (404 se não existir) |
| POST | `/voluntarios` | Cria (201) |
| PUT | `/voluntarios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/voluntarios/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/acoes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Acao → Voluntario.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/acoes -ContentType "application/json" -Body '{ "titulo": "Exemplo", "local": "Local exemplo", "dataAcao": "2026-03-15", "vagas": 10 }'
```

```bash
# Acao
curl -X POST http://localhost:8080/acoes -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "local": "Local exemplo", "dataAcao": "2026-03-15", "vagas": 10 }'
curl http://localhost:8080/acoes
curl http://localhost:8080/acoes/1
curl -X PUT http://localhost:8080/acoes/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "local": "Local exemplo", "dataAcao": "2026-03-15", "vagas": 10 }'
curl -X DELETE http://localhost:8080/acoes/1

# Voluntario
curl -X POST http://localhost:8080/voluntarios -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "horasDedicadas": 10, "ativo": true, "acao": { "id": 1 } }'
curl http://localhost:8080/voluntarios
curl http://localhost:8080/voluntarios/1
curl -X PUT http://localhost:8080/voluntarios/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "horasDedicadas": 10, "ativo": true, "acao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/voluntarios/1
```

## Estrutura

```
src/main/java/com/exemplo/ong/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
