# 128 · Casa de Shows

**Categoria:** Lazer e Cultura
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Programação de shows, artistas e venda de ingressos.

## Entidades

### Artista (tabela `artistas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `generoMusical` | `String` |

### Show (tabela `shows`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `valorIngresso` | `BigDecimal` |
| `capacidade` | `Integer` |
| `artista` | `Artista` (ManyToOne, coluna `artista_id`) |

### Ingresso (tabela `ingressos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `comprador` | `String` |
| `setor` | `String` |
| `utilizado` | `Boolean` |
| `show` | `Show` (ManyToOne, coluna `show_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/artistas` | Lista todos |
| GET | `/artistas/{id}` | Busca por id (404 se não existir) |
| POST | `/artistas` | Cria (201) |
| PUT | `/artistas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/artistas/{id}` | Exclui (204) |
| GET | `/shows` | Lista todos |
| GET | `/shows?artistaId=1` | Lista filtrando por Artista |
| GET | `/shows/{id}` | Busca por id (404 se não existir) |
| POST | `/shows` | Cria (201) |
| PUT | `/shows/{id}` | Atualiza (404 se não existir) |
| DELETE | `/shows/{id}` | Exclui (204) |
| GET | `/ingressos` | Lista todos |
| GET | `/ingressos?showId=1` | Lista filtrando por Show |
| GET | `/ingressos/{id}` | Busca por id (404 se não existir) |
| POST | `/ingressos` | Cria (201) |
| PUT | `/ingressos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/ingressos/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\casa-shows.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/artistas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/casa-shows`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Artista → Show → Ingresso.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/artistas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "generoMusical": "GeneroMusical exemplo" }'
```

```bash
# Artista
curl -X POST http://localhost:8080/artistas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "generoMusical": "GeneroMusical exemplo" }'
curl http://localhost:8080/artistas
curl http://localhost:8080/artistas/1
curl -X PUT http://localhost:8080/artistas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "generoMusical": "GeneroMusical exemplo" }'
curl -X DELETE http://localhost:8080/artistas/1

# Show
curl -X POST http://localhost:8080/shows -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "valorIngresso": 99.90, "capacidade": 10, "artista": { "id": 1 } }'
curl http://localhost:8080/shows
curl http://localhost:8080/shows/1
curl -X PUT http://localhost:8080/shows/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "valorIngresso": 99.90, "capacidade": 10, "artista": { "id": 1 } }'
curl -X DELETE http://localhost:8080/shows/1

# Ingresso
curl -X POST http://localhost:8080/ingressos -H "Content-Type: application/json" -d '{ "comprador": "Comprador exemplo", "setor": "Setor exemplo", "utilizado": true, "show": { "id": 1 } }'
curl http://localhost:8080/ingressos
curl http://localhost:8080/ingressos/1
curl -X PUT http://localhost:8080/ingressos/1 -H "Content-Type: application/json" -d '{ "comprador": "Comprador exemplo", "setor": "Setor exemplo", "utilizado": true, "show": { "id": 1 } }'
curl -X DELETE http://localhost:8080/ingressos/1
```

## Estrutura

```
src/main/java/com/exemplo/casashows/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
