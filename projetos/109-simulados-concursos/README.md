# 109 · Simulados para Concursos

**Categoria:** Educação
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Cadastro de concursos e dos simulados aplicados para cada um.

## Entidades

### Concurso (tabela `concursos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `orgao` | `String` |
| `dataProva` | `LocalDate` |

### Simulado (tabela `simulados`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `quantidadeQuestoes` | `Integer` |
| `duracaoMin` | `Integer` |
| `aplicadoEm` | `LocalDateTime` |
| `concurso` | `Concurso` (ManyToOne, coluna `concurso_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/concursos` | Lista todos |
| GET | `/concursos/{id}` | Busca por id (404 se não existir) |
| POST | `/concursos` | Cria (201) |
| PUT | `/concursos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/concursos/{id}` | Exclui (204) |
| GET | `/simulados` | Lista todos |
| GET | `/simulados?concursoId=1` | Lista filtrando por Concurso |
| GET | `/simulados/{id}` | Busca por id (404 se não existir) |
| POST | `/simulados` | Cria (201) |
| PUT | `/simulados/{id}` | Atualiza (404 se não existir) |
| DELETE | `/simulados/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\simulados-concursos.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/concursos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/simulados-concursos`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Concurso → Simulado.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/concursos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "orgao": "Orgao exemplo", "dataProva": "2026-03-15" }'
```

```bash
# Concurso
curl -X POST http://localhost:8080/concursos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "orgao": "Orgao exemplo", "dataProva": "2026-03-15" }'
curl http://localhost:8080/concursos
curl http://localhost:8080/concursos/1
curl -X PUT http://localhost:8080/concursos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "orgao": "Orgao exemplo", "dataProva": "2026-03-15" }'
curl -X DELETE http://localhost:8080/concursos/1

# Simulado
curl -X POST http://localhost:8080/simulados -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "quantidadeQuestoes": 10, "duracaoMin": 10, "aplicadoEm": "2026-03-15T10:30:00", "concurso": { "id": 1 } }'
curl http://localhost:8080/simulados
curl http://localhost:8080/simulados/1
curl -X PUT http://localhost:8080/simulados/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "quantidadeQuestoes": 10, "duracaoMin": 10, "aplicadoEm": "2026-03-15T10:30:00", "concurso": { "id": 1 } }'
curl -X DELETE http://localhost:8080/simulados/1
```

## Estrutura

```
src/main/java/com/exemplo/simuladosconcursos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
