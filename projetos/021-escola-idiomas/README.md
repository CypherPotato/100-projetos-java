# 021 · Escola de Idiomas

**Categoria:** Educação
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Organização de idiomas, professores e turmas de uma escola de línguas.

## Entidades

### Idioma (tabela `idiomas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `nivelMaximo` | `String` |

### Professor (tabela `professores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |

### Turma (tabela `turmas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `horario` | `String` |
| `vagas` | `Integer` |
| `mensalidade` | `BigDecimal` |
| `idioma` | `Idioma` (ManyToOne, coluna `idioma_id`) |
| `professor` | `Professor` (ManyToOne, coluna `professor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/idiomas` | Lista todos |
| GET | `/idiomas/{id}` | Busca por id (404 se não existir) |
| POST | `/idiomas` | Cria (201) |
| PUT | `/idiomas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/idiomas/{id}` | Exclui (204) |
| GET | `/professores` | Lista todos |
| GET | `/professores/{id}` | Busca por id (404 se não existir) |
| POST | `/professores` | Cria (201) |
| PUT | `/professores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/professores/{id}` | Exclui (204) |
| GET | `/turmas` | Lista todos |
| GET | `/turmas?idiomaId=1` | Lista filtrando por Idioma |
| GET | `/turmas?professorId=1` | Lista filtrando por Professor |
| GET | `/turmas/{id}` | Busca por id (404 se não existir) |
| POST | `/turmas` | Cria (201) |
| PUT | `/turmas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/turmas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/idiomas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Idioma → Professor → Turma.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/idiomas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "nivelMaximo": "NivelMaximo exemplo" }'
```

```bash
# Idioma
curl -X POST http://localhost:8080/idiomas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nivelMaximo": "NivelMaximo exemplo" }'
curl http://localhost:8080/idiomas
curl http://localhost:8080/idiomas/1
curl -X PUT http://localhost:8080/idiomas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nivelMaximo": "NivelMaximo exemplo" }'
curl -X DELETE http://localhost:8080/idiomas/1

# Professor
curl -X POST http://localhost:8080/professores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl http://localhost:8080/professores
curl http://localhost:8080/professores/1
curl -X PUT http://localhost:8080/professores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/professores/1

# Turma
curl -X POST http://localhost:8080/turmas -H "Content-Type: application/json" -d '{ "horario": "Horario exemplo", "vagas": 10, "mensalidade": 99.90, "idioma": { "id": 1 }, "professor": { "id": 1 } }'
curl http://localhost:8080/turmas
curl http://localhost:8080/turmas/1
curl -X PUT http://localhost:8080/turmas/1 -H "Content-Type: application/json" -d '{ "horario": "Horario exemplo", "vagas": 10, "mensalidade": 99.90, "idioma": { "id": 1 }, "professor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/turmas/1
```

## Estrutura

```
src/main/java/com/exemplo/escolaidiomas/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
