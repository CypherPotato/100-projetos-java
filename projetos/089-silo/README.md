# 089 · Silo de Grãos

**Categoria:** Agro e Logística
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Controle de silos e das cargas de grãos armazenadas.

## Entidades

### Silo (tabela `silos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `capacidadeToneladas` | `Double` |
| `localizacao` | `String` |

### Carga (tabela `cargas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `cultura` | `String` |
| `pesoToneladas` | `Double` |
| `umidade` | `Double` |
| `dataEntrada` | `LocalDateTime` |
| `silo` | `Silo` (ManyToOne, coluna `silo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/silos` | Lista todos |
| GET | `/silos/{id}` | Busca por id (404 se não existir) |
| POST | `/silos` | Cria (201) |
| PUT | `/silos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/silos/{id}` | Exclui (204) |
| GET | `/cargas` | Lista todos |
| GET | `/cargas?siloId=1` | Lista filtrando por Silo |
| GET | `/cargas/{id}` | Busca por id (404 se não existir) |
| POST | `/cargas` | Cria (201) |
| PUT | `/cargas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cargas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/silos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Silo → Carga.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/silos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "capacidadeToneladas": 12.5, "localizacao": "Localizacao exemplo" }'
```

```bash
# Silo
curl -X POST http://localhost:8080/silos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidadeToneladas": 12.5, "localizacao": "Localizacao exemplo" }'
curl http://localhost:8080/silos
curl http://localhost:8080/silos/1
curl -X PUT http://localhost:8080/silos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidadeToneladas": 12.5, "localizacao": "Localizacao exemplo" }'
curl -X DELETE http://localhost:8080/silos/1

# Carga
curl -X POST http://localhost:8080/cargas -H "Content-Type: application/json" -d '{ "cultura": "Cultura exemplo", "pesoToneladas": 12.5, "umidade": 12.5, "dataEntrada": "2026-03-15T10:30:00", "silo": { "id": 1 } }'
curl http://localhost:8080/cargas
curl http://localhost:8080/cargas/1
curl -X PUT http://localhost:8080/cargas/1 -H "Content-Type: application/json" -d '{ "cultura": "Cultura exemplo", "pesoToneladas": 12.5, "umidade": 12.5, "dataEntrada": "2026-03-15T10:30:00", "silo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/cargas/1
```

## Estrutura

```
src/main/java/com/exemplo/silo/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
