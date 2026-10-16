# 093 · Viveiro de Mudas

**Categoria:** Agro e Logística
**Lombok:** não · **Camada Service:** sim

Cadastro de espécies e lotes de mudas de um viveiro.

## Entidades

### Especie (tabela `especies`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nomeComum` | `String` |
| `nomeCientifico` | `String` |
| `cicloDias` | `Integer` |

### Muda (tabela `mudas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `lote` | `String` |
| `quantidade` | `Integer` |
| `precoUnitario` | `BigDecimal` |
| `dataSemeadura` | `LocalDate` |
| `especie` | `Especie` (ManyToOne, coluna `especie_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/especies` | Lista todos |
| GET | `/especies/{id}` | Busca por id (404 se não existir) |
| POST | `/especies` | Cria (201) |
| PUT | `/especies/{id}` | Atualiza (404 se não existir) |
| DELETE | `/especies/{id}` | Exclui (204) |
| GET | `/mudas` | Lista todos |
| GET | `/mudas?especieId=1` | Lista filtrando por Especie |
| GET | `/mudas/{id}` | Busca por id (404 se não existir) |
| POST | `/mudas` | Cria (201) |
| PUT | `/mudas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/mudas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/especies

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Especie → Muda.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/especies -ContentType "application/json" -Body '{ "nomeComum": "NomeComum exemplo", "nomeCientifico": "NomeCientifico exemplo", "cicloDias": 10 }'
```

```bash
# Especie
curl -X POST http://localhost:8080/especies -H "Content-Type: application/json" -d '{ "nomeComum": "NomeComum exemplo", "nomeCientifico": "NomeCientifico exemplo", "cicloDias": 10 }'
curl http://localhost:8080/especies
curl http://localhost:8080/especies/1
curl -X PUT http://localhost:8080/especies/1 -H "Content-Type: application/json" -d '{ "nomeComum": "NomeComum exemplo", "nomeCientifico": "NomeCientifico exemplo", "cicloDias": 10 }'
curl -X DELETE http://localhost:8080/especies/1

# Muda
curl -X POST http://localhost:8080/mudas -H "Content-Type: application/json" -d '{ "lote": "Lote exemplo", "quantidade": 10, "precoUnitario": 99.90, "dataSemeadura": "2026-03-15", "especie": { "id": 1 } }'
curl http://localhost:8080/mudas
curl http://localhost:8080/mudas/1
curl -X PUT http://localhost:8080/mudas/1 -H "Content-Type: application/json" -d '{ "lote": "Lote exemplo", "quantidade": 10, "precoUnitario": 99.90, "dataSemeadura": "2026-03-15", "especie": { "id": 1 } }'
curl -X DELETE http://localhost:8080/mudas/1
```

## Estrutura

```
src/main/java/com/exemplo/viveiro/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
