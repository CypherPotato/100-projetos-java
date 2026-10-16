# 066 · Adega de Vinhos

**Categoria:** Alimentação
**Lombok:** não · **Camada Service:** sim

Controle de vinícolas e dos vinhos guardados em uma adega.

## Entidades

### Vinicola (tabela `vinicolas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `pais` | `String` |
| `regiao` | `String` |

### Vinho (tabela `vinhos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `rotulo` | `String` |
| `safra` | `Integer` |
| `teorAlcoolico` | `Double` |
| `preco` | `BigDecimal` |
| `estoque` | `Integer` |
| `vinicola` | `Vinicola` (ManyToOne, coluna `vinicola_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/vinicolas` | Lista todos |
| GET | `/vinicolas/{id}` | Busca por id (404 se não existir) |
| POST | `/vinicolas` | Cria (201) |
| PUT | `/vinicolas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vinicolas/{id}` | Exclui (204) |
| GET | `/vinhos` | Lista todos |
| GET | `/vinhos?vinicolaId=1` | Lista filtrando por Vinicola |
| GET | `/vinhos/{id}` | Busca por id (404 se não existir) |
| POST | `/vinhos` | Cria (201) |
| PUT | `/vinhos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vinhos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/vinicolas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Vinicola → Vinho.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/vinicolas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "pais": "Pais exemplo", "regiao": "Regiao exemplo" }'
```

```bash
# Vinicola
curl -X POST http://localhost:8080/vinicolas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pais": "Pais exemplo", "regiao": "Regiao exemplo" }'
curl http://localhost:8080/vinicolas
curl http://localhost:8080/vinicolas/1
curl -X PUT http://localhost:8080/vinicolas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pais": "Pais exemplo", "regiao": "Regiao exemplo" }'
curl -X DELETE http://localhost:8080/vinicolas/1

# Vinho
curl -X POST http://localhost:8080/vinhos -H "Content-Type: application/json" -d '{ "rotulo": "Rotulo exemplo", "safra": 10, "teorAlcoolico": 12.5, "preco": 99.90, "estoque": 10, "vinicola": { "id": 1 } }'
curl http://localhost:8080/vinhos
curl http://localhost:8080/vinhos/1
curl -X PUT http://localhost:8080/vinhos/1 -H "Content-Type: application/json" -d '{ "rotulo": "Rotulo exemplo", "safra": 10, "teorAlcoolico": 12.5, "preco": 99.90, "estoque": 10, "vinicola": { "id": 1 } }'
curl -X DELETE http://localhost:8080/vinhos/1
```

## Estrutura

```
src/main/java/com/exemplo/adega/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
