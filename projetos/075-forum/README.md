# 075 · Fórum de Discussão

**Categoria:** Tecnologia
**Lombok:** sim · **Camada Service:** sim

Fórum com membros, tópicos de discussão e respostas.

## Entidades

### Membro (tabela `membros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `apelido` | `String` |
| `email` | `String` |
| `dataCadastro` | `LocalDate` |

### Topico (tabela `topicos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `criadoEm` | `LocalDateTime` |
| `fechado` | `Boolean` |
| `autor` | `Membro` (ManyToOne, coluna `autor_id`) |

### Resposta (tabela `respostas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `conteudo` | `String` |
| `dataHora` | `LocalDateTime` |
| `topico` | `Topico` (ManyToOne, coluna `topico_id`) |
| `autor` | `Membro` (ManyToOne, coluna `autor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/membros` | Lista todos |
| GET | `/membros/{id}` | Busca por id (404 se não existir) |
| POST | `/membros` | Cria (201) |
| PUT | `/membros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/membros/{id}` | Exclui (204) |
| GET | `/topicos` | Lista todos |
| GET | `/topicos?autorId=1` | Lista filtrando por Membro |
| GET | `/topicos/{id}` | Busca por id (404 se não existir) |
| POST | `/topicos` | Cria (201) |
| PUT | `/topicos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/topicos/{id}` | Exclui (204) |
| GET | `/respostas` | Lista todos |
| GET | `/respostas?topicoId=1` | Lista filtrando por Topico |
| GET | `/respostas?autorId=1` | Lista filtrando por Membro |
| GET | `/respostas/{id}` | Busca por id (404 se não existir) |
| POST | `/respostas` | Cria (201) |
| PUT | `/respostas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/respostas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/membros

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Membro → Topico → Resposta.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/membros -ContentType "application/json" -Body '{ "apelido": "Apelido exemplo", "email": "maria@exemplo.com", "dataCadastro": "2026-03-15" }'
```

```bash
# Membro
curl -X POST http://localhost:8080/membros -H "Content-Type: application/json" -d '{ "apelido": "Apelido exemplo", "email": "maria@exemplo.com", "dataCadastro": "2026-03-15" }'
curl http://localhost:8080/membros
curl http://localhost:8080/membros/1
curl -X PUT http://localhost:8080/membros/1 -H "Content-Type: application/json" -d '{ "apelido": "Apelido exemplo", "email": "maria@exemplo.com", "dataCadastro": "2026-03-15" }'
curl -X DELETE http://localhost:8080/membros/1

# Topico
curl -X POST http://localhost:8080/topicos -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "criadoEm": "2026-03-15T10:30:00", "fechado": true, "autor": { "id": 1 } }'
curl http://localhost:8080/topicos
curl http://localhost:8080/topicos/1
curl -X PUT http://localhost:8080/topicos/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "criadoEm": "2026-03-15T10:30:00", "fechado": true, "autor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/topicos/1

# Resposta
curl -X POST http://localhost:8080/respostas -H "Content-Type: application/json" -d '{ "conteudo": "Conteudo exemplo", "dataHora": "2026-03-15T10:30:00", "topico": { "id": 1 }, "autor": { "id": 1 } }'
curl http://localhost:8080/respostas
curl http://localhost:8080/respostas/1
curl -X PUT http://localhost:8080/respostas/1 -H "Content-Type: application/json" -d '{ "conteudo": "Conteudo exemplo", "dataHora": "2026-03-15T10:30:00", "topico": { "id": 1 }, "autor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/respostas/1
```

## Estrutura

```
src/main/java/com/exemplo/forum/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
