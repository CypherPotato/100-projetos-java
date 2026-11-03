# 129 · Galeria de Arte

**Categoria:** Lazer e Cultura
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Catálogo de artistas e obras expostas em uma galeria.

## Entidades

### Artista (tabela `artistas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `nacionalidade` | `String` |
| `anoNascimento` | `Integer` |

### Obra (tabela `obras`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `tecnica` | `String` |
| `preco` | `BigDecimal` |
| `vendida` | `Boolean` |
| `artista` | `Artista` (ManyToOne, coluna `artista_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/artistas` | Lista todos |
| GET | `/artistas/{id}` | Busca por id (404 se não existir) |
| POST | `/artistas` | Cria (201) |
| PUT | `/artistas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/artistas/{id}` | Exclui (204) |
| GET | `/obras` | Lista todos |
| GET | `/obras?artistaId=1` | Lista filtrando por Artista |
| GET | `/obras/{id}` | Busca por id (404 se não existir) |
| POST | `/obras` | Cria (201) |
| PUT | `/obras/{id}` | Atualiza (404 se não existir) |
| DELETE | `/obras/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\galeria-arte.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/artistas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/galeria-arte`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Artista → Obra.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/artistas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo", "anoNascimento": 10 }'
```

```bash
# Artista
curl -X POST http://localhost:8080/artistas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo", "anoNascimento": 10 }'
curl http://localhost:8080/artistas
curl http://localhost:8080/artistas/1
curl -X PUT http://localhost:8080/artistas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "nacionalidade": "Nacionalidade exemplo", "anoNascimento": 10 }'
curl -X DELETE http://localhost:8080/artistas/1

# Obra
curl -X POST http://localhost:8080/obras -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "tecnica": "Tecnica exemplo", "preco": 99.90, "vendida": true, "artista": { "id": 1 } }'
curl http://localhost:8080/obras
curl http://localhost:8080/obras/1
curl -X PUT http://localhost:8080/obras/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "tecnica": "Tecnica exemplo", "preco": 99.90, "vendida": true, "artista": { "id": 1 } }'
curl -X DELETE http://localhost:8080/obras/1
```

## Estrutura

```
src/main/java/com/exemplo/galeriaarte/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
