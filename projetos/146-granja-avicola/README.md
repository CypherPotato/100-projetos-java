# 146 · Granja Avícola

**Categoria:** Agro e Logística
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Controle de galpões, lotes de aves e coleta de ovos.

## Entidades

### Galpao (tabela `galpoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `capacidadeAves` | `Integer` |

### Lote (tabela `lotes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataAlojamento` | `LocalDate` |
| `quantidadeAves` | `Integer` |
| `raca` | `String` |
| `galpao` | `Galpao` (ManyToOne, coluna `galpao_id`) |

### Coleta (tabela `coletas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataColeta` | `LocalDate` |
| `ovos` | `Long` |
| `ovosQuebrados` | `Integer` |
| `lote` | `Lote` (ManyToOne, coluna `lote_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/galpoes` | Lista todos |
| GET | `/galpoes/{id}` | Busca por id (404 se não existir) |
| POST | `/galpoes` | Cria (201) |
| PUT | `/galpoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/galpoes/{id}` | Exclui (204) |
| GET | `/lotes` | Lista todos |
| GET | `/lotes?galpaoId=1` | Lista filtrando por Galpao |
| GET | `/lotes/{id}` | Busca por id (404 se não existir) |
| POST | `/lotes` | Cria (201) |
| PUT | `/lotes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/lotes/{id}` | Exclui (204) |
| GET | `/coletas` | Lista todos |
| GET | `/coletas?loteId=1` | Lista filtrando por Lote |
| GET | `/coletas/{id}` | Busca por id (404 se não existir) |
| POST | `/coletas` | Cria (201) |
| PUT | `/coletas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/coletas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\granja-avicola.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/galpoes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/granja-avicola`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Galpao → Lote → Coleta.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/galpoes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "capacidadeAves": 10 }'
```

```bash
# Galpao
curl -X POST http://localhost:8080/galpoes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidadeAves": 10 }'
curl http://localhost:8080/galpoes
curl http://localhost:8080/galpoes/1
curl -X PUT http://localhost:8080/galpoes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidadeAves": 10 }'
curl -X DELETE http://localhost:8080/galpoes/1

# Lote
curl -X POST http://localhost:8080/lotes -H "Content-Type: application/json" -d '{ "dataAlojamento": "2026-03-15", "quantidadeAves": 10, "raca": "Raca exemplo", "galpao": { "id": 1 } }'
curl http://localhost:8080/lotes
curl http://localhost:8080/lotes/1
curl -X PUT http://localhost:8080/lotes/1 -H "Content-Type: application/json" -d '{ "dataAlojamento": "2026-03-15", "quantidadeAves": 10, "raca": "Raca exemplo", "galpao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/lotes/1

# Coleta
curl -X POST http://localhost:8080/coletas -H "Content-Type: application/json" -d '{ "dataColeta": "2026-03-15", "ovos": 100, "ovosQuebrados": 10, "lote": { "id": 1 } }'
curl http://localhost:8080/coletas
curl http://localhost:8080/coletas/1
curl -X PUT http://localhost:8080/coletas/1 -H "Content-Type: application/json" -d '{ "dataColeta": "2026-03-15", "ovos": 100, "ovosQuebrados": 10, "lote": { "id": 1 } }'
curl -X DELETE http://localhost:8080/coletas/1
```

## Estrutura

```
src/main/java/com/exemplo/granjaavicola/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
