# 083 · Clube Social

**Categoria:** Comunidade
**Lombok:** não · **Camada Service:** sim

Gerenciamento de modalidades esportivas e sócios de um clube.

## Entidades

### Modalidade (tabela `modalidades`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `mensalidade` | `BigDecimal` |
| `vagas` | `Integer` |

### Socio (tabela `socios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataNascimento` | `LocalDate` |
| `telefone` | `String` |
| `ativo` | `Boolean` |
| `modalidade` | `Modalidade` (ManyToOne, coluna `modalidade_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/modalidades` | Lista todos |
| GET | `/modalidades/{id}` | Busca por id (404 se não existir) |
| POST | `/modalidades` | Cria (201) |
| PUT | `/modalidades/{id}` | Atualiza (404 se não existir) |
| DELETE | `/modalidades/{id}` | Exclui (204) |
| GET | `/socios` | Lista todos |
| GET | `/socios?modalidadeId=1` | Lista filtrando por Modalidade |
| GET | `/socios/{id}` | Busca por id (404 se não existir) |
| POST | `/socios` | Cria (201) |
| PUT | `/socios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/socios/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/modalidades

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Modalidade → Socio.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/modalidades -ContentType "application/json" -Body '{ "nome": "Maria Silva", "mensalidade": 99.90, "vagas": 10 }'
```

```bash
# Modalidade
curl -X POST http://localhost:8080/modalidades -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "mensalidade": 99.90, "vagas": 10 }'
curl http://localhost:8080/modalidades
curl http://localhost:8080/modalidades/1
curl -X PUT http://localhost:8080/modalidades/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "mensalidade": 99.90, "vagas": 10 }'
curl -X DELETE http://localhost:8080/modalidades/1

# Socio
curl -X POST http://localhost:8080/socios -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "telefone": "(11) 99999-0000", "ativo": true, "modalidade": { "id": 1 } }'
curl http://localhost:8080/socios
curl http://localhost:8080/socios/1
curl -X PUT http://localhost:8080/socios/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "telefone": "(11) 99999-0000", "ativo": true, "modalidade": { "id": 1 } }'
curl -X DELETE http://localhost:8080/socios/1
```

## Estrutura

```
src/main/java/com/exemplo/clubesocial/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
