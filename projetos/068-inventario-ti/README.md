# 068 · Inventário de Equipamentos de TI

**Categoria:** Tecnologia
**Lombok:** não · **Camada Service:** não

Controle dos equipamentos de informática distribuídos entre os setores da empresa.

## Entidades

### Setor (tabela `setores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `responsavel` | `String` |

### Equipamento (tabela `equipamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `patrimonio` | `String` |
| `modelo` | `String` |
| `tipo` | `String` |
| `dataAquisicao` | `LocalDate` |
| `valor` | `BigDecimal` |
| `setor` | `Setor` (ManyToOne, coluna `setor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/setores` | Lista todos |
| GET | `/setores/{id}` | Busca por id (404 se não existir) |
| POST | `/setores` | Cria (201) |
| PUT | `/setores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/setores/{id}` | Exclui (204) |
| GET | `/equipamentos` | Lista todos |
| GET | `/equipamentos?setorId=1` | Lista filtrando por Setor |
| GET | `/equipamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/equipamentos` | Cria (201) |
| PUT | `/equipamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/equipamentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/setores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Setor → Equipamento.

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

# Equipamento
curl -X POST http://localhost:8080/equipamentos -H "Content-Type: application/json" -d '{ "patrimonio": "Patrimonio exemplo", "modelo": "Modelo Y", "tipo": "Tipo exemplo", "dataAquisicao": "2026-03-15", "valor": 99.90, "setor": { "id": 1 } }'
curl http://localhost:8080/equipamentos
curl http://localhost:8080/equipamentos/1
curl -X PUT http://localhost:8080/equipamentos/1 -H "Content-Type: application/json" -d '{ "patrimonio": "Patrimonio exemplo", "modelo": "Modelo Y", "tipo": "Tipo exemplo", "dataAquisicao": "2026-03-15", "valor": 99.90, "setor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/equipamentos/1
```

## Estrutura

```
src/main/java/com/exemplo/inventarioti/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
