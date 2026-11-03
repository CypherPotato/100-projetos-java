# 131 · Gibiteca

**Categoria:** Lazer e Cultura
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Controle de coleções de quadrinhos e revistas emprestadas.

## Entidades

### Colecao (tabela `colecoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `editora` | `String` |

### Revista (tabela `revistas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `edicao` | `Integer` |
| `anoPublicacao` | `Integer` |
| `emprestada` | `Boolean` |
| `colecao` | `Colecao` (ManyToOne, coluna `colecao_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/colecoes` | Lista todos |
| GET | `/colecoes/{id}` | Busca por id (404 se não existir) |
| POST | `/colecoes` | Cria (201) |
| PUT | `/colecoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/colecoes/{id}` | Exclui (204) |
| GET | `/revistas` | Lista todos |
| GET | `/revistas?colecaoId=1` | Lista filtrando por Colecao |
| GET | `/revistas/{id}` | Busca por id (404 se não existir) |
| POST | `/revistas` | Cria (201) |
| PUT | `/revistas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/revistas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\gibiteca.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/colecoes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/gibiteca`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Colecao → Revista.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/colecoes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "editora": "Editora exemplo" }'
```

```bash
# Colecao
curl -X POST http://localhost:8080/colecoes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "editora": "Editora exemplo" }'
curl http://localhost:8080/colecoes
curl http://localhost:8080/colecoes/1
curl -X PUT http://localhost:8080/colecoes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "editora": "Editora exemplo" }'
curl -X DELETE http://localhost:8080/colecoes/1

# Revista
curl -X POST http://localhost:8080/revistas -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "edicao": 10, "anoPublicacao": 10, "emprestada": true, "colecao": { "id": 1 } }'
curl http://localhost:8080/revistas
curl http://localhost:8080/revistas/1
curl -X PUT http://localhost:8080/revistas/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "edicao": 10, "anoPublicacao": 10, "emprestada": true, "colecao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/revistas/1
```

## Estrutura

```
src/main/java/com/exemplo/gibiteca/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
