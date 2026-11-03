# 101 · Joalheria

**Categoria:** Comércio
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Controle de categorias, joias e vendas de uma joalheria.

## Entidades

### Categoria (tabela `categorias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Joia (tabela `joias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `material` | `String` |
| `peso` | `Double` |
| `preco` | `BigDecimal` |
| `categoria` | `Categoria` (ManyToOne, coluna `categoria_id`) |

### Venda (tabela `vendas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataVenda` | `LocalDate` |
| `valorTotal` | `BigDecimal` |
| `cliente` | `String` |
| `joia` | `Joia` (ManyToOne, coluna `joia_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias` | Lista todos |
| GET | `/categorias/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias` | Cria (201) |
| PUT | `/categorias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias/{id}` | Exclui (204) |
| GET | `/joias` | Lista todos |
| GET | `/joias?categoriaId=1` | Lista filtrando por Categoria |
| GET | `/joias/{id}` | Busca por id (404 se não existir) |
| POST | `/joias` | Cria (201) |
| PUT | `/joias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/joias/{id}` | Exclui (204) |
| GET | `/vendas` | Lista todos |
| GET | `/vendas?joiaId=1` | Lista filtrando por Joia |
| GET | `/vendas/{id}` | Busca por id (404 se não existir) |
| POST | `/vendas` | Cria (201) |
| PUT | `/vendas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vendas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\joalheria.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/categorias
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/joalheria`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Categoria → Joia → Venda.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/categorias -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# Categoria
curl -X POST http://localhost:8080/categorias -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/categorias
curl http://localhost:8080/categorias/1
curl -X PUT http://localhost:8080/categorias/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/categorias/1

# Joia
curl -X POST http://localhost:8080/joias -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "material": "Material exemplo", "peso": 12.5, "preco": 99.90, "categoria": { "id": 1 } }'
curl http://localhost:8080/joias
curl http://localhost:8080/joias/1
curl -X PUT http://localhost:8080/joias/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "material": "Material exemplo", "peso": 12.5, "preco": 99.90, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/joias/1

# Venda
curl -X POST http://localhost:8080/vendas -H "Content-Type: application/json" -d '{ "dataVenda": "2026-03-15", "valorTotal": 99.90, "cliente": "Cliente exemplo", "joia": { "id": 1 } }'
curl http://localhost:8080/vendas
curl http://localhost:8080/vendas/1
curl -X PUT http://localhost:8080/vendas/1 -H "Content-Type: application/json" -d '{ "dataVenda": "2026-03-15", "valorTotal": 99.90, "cliente": "Cliente exemplo", "joia": { "id": 1 } }'
curl -X DELETE http://localhost:8080/vendas/1
```

## Estrutura

```
src/main/java/com/exemplo/joalheria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
