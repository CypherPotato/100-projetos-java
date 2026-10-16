# 076 · Igreja

**Categoria:** Comunidade
**Lombok:** sim · **Camada Service:** não

Cadastro de células e membros de uma igreja.

## Entidades

### Celula (tabela `celulas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `bairro` | `String` |
| `diaReuniao` | `String` |
| `ativa` | `Boolean` |

### Membro (tabela `membros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `dataBatismo` | `LocalDate` |
| `contribuicaoMensal` | `BigDecimal` |
| `celula` | `Celula` (ManyToOne, coluna `celula_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/celulas` | Lista todos |
| GET | `/celulas/{id}` | Busca por id (404 se não existir) |
| POST | `/celulas` | Cria (201) |
| PUT | `/celulas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/celulas/{id}` | Exclui (204) |
| GET | `/membros` | Lista todos |
| GET | `/membros?celulaId=1` | Lista filtrando por Celula |
| GET | `/membros/{id}` | Busca por id (404 se não existir) |
| POST | `/membros` | Cria (201) |
| PUT | `/membros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/membros/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/celulas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Celula → Membro.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/celulas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "bairro": "Bairro exemplo", "diaReuniao": "DiaReuniao exemplo", "ativa": true }'
```

```bash
# Celula
curl -X POST http://localhost:8080/celulas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "bairro": "Bairro exemplo", "diaReuniao": "DiaReuniao exemplo", "ativa": true }'
curl http://localhost:8080/celulas
curl http://localhost:8080/celulas/1
curl -X PUT http://localhost:8080/celulas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "bairro": "Bairro exemplo", "diaReuniao": "DiaReuniao exemplo", "ativa": true }'
curl -X DELETE http://localhost:8080/celulas/1

# Membro
curl -X POST http://localhost:8080/membros -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataBatismo": "2026-03-15", "contribuicaoMensal": 99.90, "celula": { "id": 1 } }'
curl http://localhost:8080/membros
curl http://localhost:8080/membros/1
curl -X PUT http://localhost:8080/membros/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataBatismo": "2026-03-15", "contribuicaoMensal": 99.90, "celula": { "id": 1 } }'
curl -X DELETE http://localhost:8080/membros/1
```

## Estrutura

```
src/main/java/com/exemplo/igreja/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
