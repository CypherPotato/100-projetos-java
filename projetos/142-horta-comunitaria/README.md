# 142 · Horta Comunitária

**Categoria:** Comunidade
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Controle de moradores, canteiros e plantios de uma horta comunitária.

## Entidades

### Morador (tabela `moradores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Canteiro (tabela `canteiros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numero` | `Integer` |
| `areaM2` | `Double` |
| `irrigado` | `Boolean` |
| `morador` | `Morador` (ManyToOne, coluna `morador_id`) |

### Plantio (tabela `plantios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `cultura` | `String` |
| `dataPlantio` | `LocalDate` |
| `colhido` | `Boolean` |
| `canteiro` | `Canteiro` (ManyToOne, coluna `canteiro_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/moradores` | Lista todos |
| GET | `/moradores/{id}` | Busca por id (404 se não existir) |
| POST | `/moradores` | Cria (201) |
| PUT | `/moradores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/moradores/{id}` | Exclui (204) |
| GET | `/canteiros` | Lista todos |
| GET | `/canteiros?moradorId=1` | Lista filtrando por Morador |
| GET | `/canteiros/{id}` | Busca por id (404 se não existir) |
| POST | `/canteiros` | Cria (201) |
| PUT | `/canteiros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/canteiros/{id}` | Exclui (204) |
| GET | `/plantios` | Lista todos |
| GET | `/plantios?canteiroId=1` | Lista filtrando por Canteiro |
| GET | `/plantios/{id}` | Busca por id (404 se não existir) |
| POST | `/plantios` | Cria (201) |
| PUT | `/plantios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/plantios/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\horta-comunitaria.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/moradores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/horta-comunitaria`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Morador → Canteiro → Plantio.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/moradores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Morador
curl -X POST http://localhost:8080/moradores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/moradores
curl http://localhost:8080/moradores/1
curl -X PUT http://localhost:8080/moradores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/moradores/1

# Canteiro
curl -X POST http://localhost:8080/canteiros -H "Content-Type: application/json" -d '{ "numero": 10, "areaM2": 12.5, "irrigado": true, "morador": { "id": 1 } }'
curl http://localhost:8080/canteiros
curl http://localhost:8080/canteiros/1
curl -X PUT http://localhost:8080/canteiros/1 -H "Content-Type: application/json" -d '{ "numero": 10, "areaM2": 12.5, "irrigado": true, "morador": { "id": 1 } }'
curl -X DELETE http://localhost:8080/canteiros/1

# Plantio
curl -X POST http://localhost:8080/plantios -H "Content-Type: application/json" -d '{ "cultura": "Cultura exemplo", "dataPlantio": "2026-03-15", "colhido": true, "canteiro": { "id": 1 } }'
curl http://localhost:8080/plantios
curl http://localhost:8080/plantios/1
curl -X PUT http://localhost:8080/plantios/1 -H "Content-Type: application/json" -d '{ "cultura": "Cultura exemplo", "dataPlantio": "2026-03-15", "colhido": true, "canteiro": { "id": 1 } }'
curl -X DELETE http://localhost:8080/plantios/1
```

## Estrutura

```
src/main/java/com/exemplo/hortacomunitaria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
