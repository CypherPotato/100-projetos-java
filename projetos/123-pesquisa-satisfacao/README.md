# 123 · Pesquisa de Satisfação

**Categoria:** Gestão
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Criação de pesquisas de satisfação e registro das respostas recebidas.

## Entidades

### Pesquisa (tabela `pesquisas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `inicio` | `LocalDate` |
| `fim` | `LocalDate` |
| `ativa` | `Boolean` |

### Resposta (tabela `respostas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nota` | `Integer` |
| `comentario` | `String` |
| `respondidaEm` | `LocalDateTime` |
| `pesquisa` | `Pesquisa` (ManyToOne, coluna `pesquisa_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/pesquisas` | Lista todos |
| GET | `/pesquisas/{id}` | Busca por id (404 se não existir) |
| POST | `/pesquisas` | Cria (201) |
| PUT | `/pesquisas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pesquisas/{id}` | Exclui (204) |
| GET | `/respostas` | Lista todos |
| GET | `/respostas?pesquisaId=1` | Lista filtrando por Pesquisa |
| GET | `/respostas/{id}` | Busca por id (404 se não existir) |
| POST | `/respostas` | Cria (201) |
| PUT | `/respostas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/respostas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\pesquisa-satisfacao.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/pesquisas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/pesquisa-satisfacao`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Pesquisa → Resposta.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/pesquisas -ContentType "application/json" -Body '{ "titulo": "Exemplo", "inicio": "2026-03-15", "fim": "2026-03-15", "ativa": true }'
```

```bash
# Pesquisa
curl -X POST http://localhost:8080/pesquisas -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "inicio": "2026-03-15", "fim": "2026-03-15", "ativa": true }'
curl http://localhost:8080/pesquisas
curl http://localhost:8080/pesquisas/1
curl -X PUT http://localhost:8080/pesquisas/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "inicio": "2026-03-15", "fim": "2026-03-15", "ativa": true }'
curl -X DELETE http://localhost:8080/pesquisas/1

# Resposta
curl -X POST http://localhost:8080/respostas -H "Content-Type: application/json" -d '{ "nota": 10, "comentario": "Comentario exemplo", "respondidaEm": "2026-03-15T10:30:00", "pesquisa": { "id": 1 } }'
curl http://localhost:8080/respostas
curl http://localhost:8080/respostas/1
curl -X PUT http://localhost:8080/respostas/1 -H "Content-Type: application/json" -d '{ "nota": 10, "comentario": "Comentario exemplo", "respondidaEm": "2026-03-15T10:30:00", "pesquisa": { "id": 1 } }'
curl -X DELETE http://localhost:8080/respostas/1
```

## Estrutura

```
src/main/java/com/exemplo/pesquisasatisfacao/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
