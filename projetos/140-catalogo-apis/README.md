# 140 · Catálogo de APIs

**Categoria:** Tecnologia
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Catálogo interno de APIs, equipes responsáveis e seus endpoints.

## Entidades

### Equipe (tabela `equipes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |

### Api (tabela `apis`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `versao` | `String` |
| `urlBase` | `String` |
| `publica` | `Boolean` |
| `equipe` | `Equipe` (ManyToOne, coluna `equipe_id`) |

### Endpoint (tabela `endpoints`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `caminho` | `String` |
| `metodo` | `String` |
| `descricao` | `String` |
| `api` | `Api` (ManyToOne, coluna `api_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/equipes` | Lista todos |
| GET | `/equipes/{id}` | Busca por id (404 se não existir) |
| POST | `/equipes` | Cria (201) |
| PUT | `/equipes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/equipes/{id}` | Exclui (204) |
| GET | `/apis` | Lista todos |
| GET | `/apis?equipeId=1` | Lista filtrando por Equipe |
| GET | `/apis/{id}` | Busca por id (404 se não existir) |
| POST | `/apis` | Cria (201) |
| PUT | `/apis/{id}` | Atualiza (404 se não existir) |
| DELETE | `/apis/{id}` | Exclui (204) |
| GET | `/endpoints` | Lista todos |
| GET | `/endpoints?apiId=1` | Lista filtrando por Api |
| GET | `/endpoints/{id}` | Busca por id (404 se não existir) |
| POST | `/endpoints` | Cria (201) |
| PUT | `/endpoints/{id}` | Atualiza (404 se não existir) |
| DELETE | `/endpoints/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\catalogo-apis.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/equipes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/catalogo-apis`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Equipe → Api → Endpoint.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/equipes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
```

```bash
# Equipe
curl -X POST http://localhost:8080/equipes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl http://localhost:8080/equipes
curl http://localhost:8080/equipes/1
curl -X PUT http://localhost:8080/equipes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/equipes/1

# Api
curl -X POST http://localhost:8080/apis -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "versao": "Versao exemplo", "urlBase": "UrlBase exemplo", "publica": true, "equipe": { "id": 1 } }'
curl http://localhost:8080/apis
curl http://localhost:8080/apis/1
curl -X PUT http://localhost:8080/apis/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "versao": "Versao exemplo", "urlBase": "UrlBase exemplo", "publica": true, "equipe": { "id": 1 } }'
curl -X DELETE http://localhost:8080/apis/1

# Endpoint
curl -X POST http://localhost:8080/endpoints -H "Content-Type: application/json" -d '{ "caminho": "Caminho exemplo", "metodo": "Metodo exemplo", "descricao": "Descrição de exemplo", "api": { "id": 1 } }'
curl http://localhost:8080/endpoints
curl http://localhost:8080/endpoints/1
curl -X PUT http://localhost:8080/endpoints/1 -H "Content-Type: application/json" -d '{ "caminho": "Caminho exemplo", "metodo": "Metodo exemplo", "descricao": "Descrição de exemplo", "api": { "id": 1 } }'
curl -X DELETE http://localhost:8080/endpoints/1
```

## Estrutura

```
src/main/java/com/exemplo/catalogoapis/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
