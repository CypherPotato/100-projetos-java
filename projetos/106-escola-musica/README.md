# 106 · Escola de Música

**Categoria:** Educação
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Agendamento de aulas de instrumentos entre professores e alunos.

## Entidades

### Instrumento (tabela `instrumentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `familia` | `String` |

### Professor (tabela `professores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `valorHora` | `BigDecimal` |

### Aula (tabela `aulas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `duracaoMin` | `Integer` |
| `presencial` | `Boolean` |
| `instrumento` | `Instrumento` (ManyToOne, coluna `instrumento_id`) |
| `professor` | `Professor` (ManyToOne, coluna `professor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/instrumentos` | Lista todos |
| GET | `/instrumentos/{id}` | Busca por id (404 se não existir) |
| POST | `/instrumentos` | Cria (201) |
| PUT | `/instrumentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/instrumentos/{id}` | Exclui (204) |
| GET | `/professores` | Lista todos |
| GET | `/professores/{id}` | Busca por id (404 se não existir) |
| POST | `/professores` | Cria (201) |
| PUT | `/professores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/professores/{id}` | Exclui (204) |
| GET | `/aulas` | Lista todos |
| GET | `/aulas?instrumentoId=1` | Lista filtrando por Instrumento |
| GET | `/aulas?professorId=1` | Lista filtrando por Professor |
| GET | `/aulas/{id}` | Busca por id (404 se não existir) |
| POST | `/aulas` | Cria (201) |
| PUT | `/aulas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aulas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\escola-musica.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/instrumentos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/escola-musica`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Instrumento → Professor → Aula.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/instrumentos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "familia": "Familia exemplo" }'
```

```bash
# Instrumento
curl -X POST http://localhost:8080/instrumentos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "familia": "Familia exemplo" }'
curl http://localhost:8080/instrumentos
curl http://localhost:8080/instrumentos/1
curl -X PUT http://localhost:8080/instrumentos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "familia": "Familia exemplo" }'
curl -X DELETE http://localhost:8080/instrumentos/1

# Professor
curl -X POST http://localhost:8080/professores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "valorHora": 99.90 }'
curl http://localhost:8080/professores
curl http://localhost:8080/professores/1
curl -X PUT http://localhost:8080/professores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "valorHora": 99.90 }'
curl -X DELETE http://localhost:8080/professores/1

# Aula
curl -X POST http://localhost:8080/aulas -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "duracaoMin": 10, "presencial": true, "instrumento": { "id": 1 }, "professor": { "id": 1 } }'
curl http://localhost:8080/aulas
curl http://localhost:8080/aulas/1
curl -X PUT http://localhost:8080/aulas/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "duracaoMin": 10, "presencial": true, "instrumento": { "id": 1 }, "professor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aulas/1
```

## Estrutura

```
src/main/java/com/exemplo/escolamusica/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
