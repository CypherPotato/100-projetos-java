# 043 · Controle de Patrimônio

**Categoria:** Gestão
**Lombok:** não · **Camada Service:** não

Cadastro de setores e dos bens patrimoniais de cada setor.

## Entidades

### Setor (tabela `setores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `responsavel` | `String` |

### Bem (tabela `bens`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `numeroTombo` | `String` |
| `valorAquisicao` | `BigDecimal` |
| `dataAquisicao` | `LocalDate` |
| `emUso` | `Boolean` |
| `setor` | `Setor` (ManyToOne, coluna `setor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/setores` | Lista todos |
| GET | `/setores/{id}` | Busca por id (404 se não existir) |
| POST | `/setores` | Cria (201) |
| PUT | `/setores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/setores/{id}` | Exclui (204) |
| GET | `/bens` | Lista todos |
| GET | `/bens?setorId=1` | Lista filtrando por Setor |
| GET | `/bens/{id}` | Busca por id (404 se não existir) |
| POST | `/bens` | Cria (201) |
| PUT | `/bens/{id}` | Atualiza (404 se não existir) |
| DELETE | `/bens/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/setores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Setor → Bem.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/setores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
```

```bash
# Setor
curl -X POST http://localhost:8080/setores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
curl http://localhost:8080/setores
curl http://localhost:8080/setores/1
curl -X PUT http://localhost:8080/setores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
curl -X DELETE http://localhost:8080/setores/1

# Bem
curl -X POST http://localhost:8080/bens -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "numeroTombo": "NumeroTombo exemplo", "valorAquisicao": 99.90, "dataAquisicao": "2026-03-15", "emUso": true, "setor": { "id": 1 } }'
curl http://localhost:8080/bens
curl http://localhost:8080/bens/1
curl -X PUT http://localhost:8080/bens/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "numeroTombo": "NumeroTombo exemplo", "valorAquisicao": 99.90, "dataAquisicao": "2026-03-15", "emUso": true, "setor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/bens/1
```

## Estrutura

```
src/main/java/com/exemplo/patrimonio/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
