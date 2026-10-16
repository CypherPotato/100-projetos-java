# 014 · Sebo

**Categoria:** Comércio
**Lombok:** não · **Camada Service:** não

Controle de gêneros e exemplares de livros usados.

## Entidades

### Genero (tabela `generos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Exemplar (tabela `exemplares`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `autor` | `String` |
| `conservacao` | `String` |
| `preco` | `BigDecimal` |
| `genero` | `Genero` (ManyToOne, coluna `genero_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/generos` | Lista todos |
| GET | `/generos/{id}` | Busca por id (404 se não existir) |
| POST | `/generos` | Cria (201) |
| PUT | `/generos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/generos/{id}` | Exclui (204) |
| GET | `/exemplares` | Lista todos |
| GET | `/exemplares?generoId=1` | Lista filtrando por Genero |
| GET | `/exemplares/{id}` | Busca por id (404 se não existir) |
| POST | `/exemplares` | Cria (201) |
| PUT | `/exemplares/{id}` | Atualiza (404 se não existir) |
| DELETE | `/exemplares/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/generos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Genero → Exemplar.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/generos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# Genero
curl -X POST http://localhost:8080/generos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/generos
curl http://localhost:8080/generos/1
curl -X PUT http://localhost:8080/generos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/generos/1

# Exemplar
curl -X POST http://localhost:8080/exemplares -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "autor": "Autor exemplo", "conservacao": "Conservacao exemplo", "preco": 99.90, "genero": { "id": 1 } }'
curl http://localhost:8080/exemplares
curl http://localhost:8080/exemplares/1
curl -X PUT http://localhost:8080/exemplares/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "autor": "Autor exemplo", "conservacao": "Conservacao exemplo", "preco": 99.90, "genero": { "id": 1 } }'
curl -X DELETE http://localhost:8080/exemplares/1
```

## Estrutura

```
src/main/java/com/exemplo/sebo/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
