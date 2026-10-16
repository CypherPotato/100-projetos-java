# 053 · Museu

**Categoria:** Lazer e Cultura
**Lombok:** sim · **Camada Service:** sim

Gerenciamento de exposições e das visitas recebidas por um museu.

## Entidades

### Exposicao (tabela `exposicoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `tema` | `String` |
| `inicio` | `LocalDate` |
| `fim` | `LocalDate` |

### Visita (tabela `visitas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `visitante` | `String` |
| `dataVisita` | `LocalDate` |
| `quantidadePessoas` | `Integer` |
| `valorTotal` | `BigDecimal` |
| `exposicao` | `Exposicao` (ManyToOne, coluna `exposicao_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/exposicoes` | Lista todos |
| GET | `/exposicoes/{id}` | Busca por id (404 se não existir) |
| POST | `/exposicoes` | Cria (201) |
| PUT | `/exposicoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/exposicoes/{id}` | Exclui (204) |
| GET | `/visitas` | Lista todos |
| GET | `/visitas?exposicaoId=1` | Lista filtrando por Exposicao |
| GET | `/visitas/{id}` | Busca por id (404 se não existir) |
| POST | `/visitas` | Cria (201) |
| PUT | `/visitas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/visitas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/exposicoes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Exposicao → Visita.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/exposicoes -ContentType "application/json" -Body '{ "titulo": "Exemplo", "tema": "Tema exemplo", "inicio": "2026-03-15", "fim": "2026-03-15" }'
```

```bash
# Exposicao
curl -X POST http://localhost:8080/exposicoes -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "tema": "Tema exemplo", "inicio": "2026-03-15", "fim": "2026-03-15" }'
curl http://localhost:8080/exposicoes
curl http://localhost:8080/exposicoes/1
curl -X PUT http://localhost:8080/exposicoes/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "tema": "Tema exemplo", "inicio": "2026-03-15", "fim": "2026-03-15" }'
curl -X DELETE http://localhost:8080/exposicoes/1

# Visita
curl -X POST http://localhost:8080/visitas -H "Content-Type: application/json" -d '{ "visitante": "Visitante exemplo", "dataVisita": "2026-03-15", "quantidadePessoas": 10, "valorTotal": 99.90, "exposicao": { "id": 1 } }'
curl http://localhost:8080/visitas
curl http://localhost:8080/visitas/1
curl -X PUT http://localhost:8080/visitas/1 -H "Content-Type: application/json" -d '{ "visitante": "Visitante exemplo", "dataVisita": "2026-03-15", "quantidadePessoas": 10, "valorTotal": 99.90, "exposicao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/visitas/1
```

## Estrutura

```
src/main/java/com/exemplo/museu/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
