# 007 · Cursos Online

**Categoria:** Educação
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Plataforma simples de instrutores, cursos e seus módulos.

## Entidades

### Instrutor (tabela `instrutores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |

### Curso (tabela `cursos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `cargaHoraria` | `Integer` |
| `preco` | `BigDecimal` |
| `instrutor` | `Instrutor` (ManyToOne, coluna `instrutor_id`) |

### Modulo (tabela `modulos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `duracaoMinutos` | `Integer` |
| `curso` | `Curso` (ManyToOne, coluna `curso_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/instrutores` | Lista todos |
| GET | `/instrutores/{id}` | Busca por id (404 se não existir) |
| POST | `/instrutores` | Cria (201) |
| PUT | `/instrutores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/instrutores/{id}` | Exclui (204) |
| GET | `/cursos` | Lista todos |
| GET | `/cursos?instrutorId=1` | Lista filtrando por Instrutor |
| GET | `/cursos/{id}` | Busca por id (404 se não existir) |
| POST | `/cursos` | Cria (201) |
| PUT | `/cursos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cursos/{id}` | Exclui (204) |
| GET | `/modulos` | Lista todos |
| GET | `/modulos?cursoId=1` | Lista filtrando por Curso |
| GET | `/modulos/{id}` | Busca por id (404 se não existir) |
| POST | `/modulos` | Cria (201) |
| PUT | `/modulos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/modulos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/instrutores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Instrutor → Curso → Modulo.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/instrutores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
```

```bash
# Instrutor
curl -X POST http://localhost:8080/instrutores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl http://localhost:8080/instrutores
curl http://localhost:8080/instrutores/1
curl -X PUT http://localhost:8080/instrutores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/instrutores/1

# Curso
curl -X POST http://localhost:8080/cursos -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "cargaHoraria": 10, "preco": 99.90, "instrutor": { "id": 1 } }'
curl http://localhost:8080/cursos
curl http://localhost:8080/cursos/1
curl -X PUT http://localhost:8080/cursos/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "cargaHoraria": 10, "preco": 99.90, "instrutor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/cursos/1

# Modulo
curl -X POST http://localhost:8080/modulos -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "duracaoMinutos": 10, "curso": { "id": 1 } }'
curl http://localhost:8080/modulos
curl http://localhost:8080/modulos/1
curl -X PUT http://localhost:8080/modulos/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "duracaoMinutos": 10, "curso": { "id": 1 } }'
curl -X DELETE http://localhost:8080/modulos/1
```

## Estrutura

```
src/main/java/com/exemplo/cursosonline/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
