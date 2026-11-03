# 091 · Frota Agrícola

**Categoria:** Agro e Logística
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Controle de máquinas agrícolas e de suas manutenções.

## Entidades

### Maquina (tabela `maquinas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `modelo` | `String` |
| `anoFabricacao` | `Integer` |
| `horasUso` | `Double` |

### Manutencao (tabela `manutencoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `dataManutencao` | `LocalDate` |
| `custo` | `BigDecimal` |
| `concluida` | `Boolean` |
| `maquina` | `Maquina` (ManyToOne, coluna `maquina_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/maquinas` | Lista todos |
| GET | `/maquinas/{id}` | Busca por id (404 se não existir) |
| POST | `/maquinas` | Cria (201) |
| PUT | `/maquinas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/maquinas/{id}` | Exclui (204) |
| GET | `/manutencoes` | Lista todos |
| GET | `/manutencoes?maquinaId=1` | Lista filtrando por Maquina |
| GET | `/manutencoes/{id}` | Busca por id (404 se não existir) |
| POST | `/manutencoes` | Cria (201) |
| PUT | `/manutencoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/manutencoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/maquinas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Maquina → Manutencao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/maquinas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "modelo": "Modelo Y", "anoFabricacao": 10, "horasUso": 12.5 }'
```

```bash
# Maquina
curl -X POST http://localhost:8080/maquinas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "modelo": "Modelo Y", "anoFabricacao": 10, "horasUso": 12.5 }'
curl http://localhost:8080/maquinas
curl http://localhost:8080/maquinas/1
curl -X PUT http://localhost:8080/maquinas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "modelo": "Modelo Y", "anoFabricacao": 10, "horasUso": 12.5 }'
curl -X DELETE http://localhost:8080/maquinas/1

# Manutencao
curl -X POST http://localhost:8080/manutencoes -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "dataManutencao": "2026-03-15", "custo": 99.90, "concluida": true, "maquina": { "id": 1 } }'
curl http://localhost:8080/manutencoes
curl http://localhost:8080/manutencoes/1
curl -X PUT http://localhost:8080/manutencoes/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "dataManutencao": "2026-03-15", "custo": 99.90, "concluida": true, "maquina": { "id": 1 } }'
curl -X DELETE http://localhost:8080/manutencoes/1
```

## Estrutura

```
src/main/java/com/exemplo/frotaagricola/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
