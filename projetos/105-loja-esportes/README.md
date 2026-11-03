# 105 · Loja de Esportes

**Categoria:** Comércio
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Estoque de artigos esportivos organizados por modalidade.

## Entidades

### Modalidade (tabela `modalidades`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `indoor` | `Boolean` |

### Artigo (tabela `artigos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `tamanho` | `String` |
| `preco` | `BigDecimal` |
| `quantidade` | `Integer` |
| `modalidade` | `Modalidade` (ManyToOne, coluna `modalidade_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/modalidades` | Lista todos |
| GET | `/modalidades/{id}` | Busca por id (404 se não existir) |
| POST | `/modalidades` | Cria (201) |
| PUT | `/modalidades/{id}` | Atualiza (404 se não existir) |
| DELETE | `/modalidades/{id}` | Exclui (204) |
| GET | `/artigos` | Lista todos |
| GET | `/artigos?modalidadeId=1` | Lista filtrando por Modalidade |
| GET | `/artigos/{id}` | Busca por id (404 se não existir) |
| POST | `/artigos` | Cria (201) |
| PUT | `/artigos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/artigos/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\loja-esportes.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/modalidades
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/loja-esportes`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Modalidade → Artigo.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/modalidades -ContentType "application/json" -Body '{ "nome": "Maria Silva", "indoor": true }'
```

```bash
# Modalidade
curl -X POST http://localhost:8080/modalidades -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "indoor": true }'
curl http://localhost:8080/modalidades
curl http://localhost:8080/modalidades/1
curl -X PUT http://localhost:8080/modalidades/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "indoor": true }'
curl -X DELETE http://localhost:8080/modalidades/1

# Artigo
curl -X POST http://localhost:8080/artigos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tamanho": "Tamanho exemplo", "preco": 99.90, "quantidade": 10, "modalidade": { "id": 1 } }'
curl http://localhost:8080/artigos
curl http://localhost:8080/artigos/1
curl -X PUT http://localhost:8080/artigos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tamanho": "Tamanho exemplo", "preco": 99.90, "quantidade": 10, "modalidade": { "id": 1 } }'
curl -X DELETE http://localhost:8080/artigos/1
```

## Estrutura

```
src/main/java/com/exemplo/lojaesportes/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
