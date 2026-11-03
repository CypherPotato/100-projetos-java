# 107 · Reforço Escolar

**Categoria:** Educação
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Registro de alunos e sessões de reforço escolar.

## Entidades

### Aluno (tabela `alunos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `serie` | `Integer` |
| `responsavel` | `String` |

### Sessao (tabela `sessoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `disciplina` | `String` |
| `dataSessao` | `LocalDate` |
| `duracaoHoras` | `Double` |
| `valor` | `BigDecimal` |
| `aluno` | `Aluno` (ManyToOne, coluna `aluno_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/alunos` | Lista todos |
| GET | `/alunos/{id}` | Busca por id (404 se não existir) |
| POST | `/alunos` | Cria (201) |
| PUT | `/alunos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/alunos/{id}` | Exclui (204) |
| GET | `/sessoes` | Lista todos |
| GET | `/sessoes?alunoId=1` | Lista filtrando por Aluno |
| GET | `/sessoes/{id}` | Busca por id (404 se não existir) |
| POST | `/sessoes` | Cria (201) |
| PUT | `/sessoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/sessoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\reforco-escolar.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/alunos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/reforco-escolar`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Aluno → Sessao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/alunos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "serie": 10, "responsavel": "Responsavel exemplo" }'
```

```bash
# Aluno
curl -X POST http://localhost:8080/alunos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "serie": 10, "responsavel": "Responsavel exemplo" }'
curl http://localhost:8080/alunos
curl http://localhost:8080/alunos/1
curl -X PUT http://localhost:8080/alunos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "serie": 10, "responsavel": "Responsavel exemplo" }'
curl -X DELETE http://localhost:8080/alunos/1

# Sessao
curl -X POST http://localhost:8080/sessoes -H "Content-Type: application/json" -d '{ "disciplina": "Disciplina exemplo", "dataSessao": "2026-03-15", "duracaoHoras": 12.5, "valor": 99.90, "aluno": { "id": 1 } }'
curl http://localhost:8080/sessoes
curl http://localhost:8080/sessoes/1
curl -X PUT http://localhost:8080/sessoes/1 -H "Content-Type: application/json" -d '{ "disciplina": "Disciplina exemplo", "dataSessao": "2026-03-15", "duracaoHoras": 12.5, "valor": 99.90, "aluno": { "id": 1 } }'
curl -X DELETE http://localhost:8080/sessoes/1
```

## Estrutura

```
src/main/java/com/exemplo/reforcoescolar/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
