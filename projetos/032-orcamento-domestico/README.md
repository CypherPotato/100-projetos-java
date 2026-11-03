# 032 · Orçamento Doméstico

**Categoria:** Finanças
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Lançamento de receitas e despesas da casa organizados por categoria de gasto.

## Entidades

### CategoriaGasto (tabela `categorias_gasto`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `limiteMensal` | `BigDecimal` |

### Lancamento (tabela `lancamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `valor` | `BigDecimal` |
| `dataLancamento` | `LocalDate` |
| `receita` | `Boolean` |
| `categoria` | `CategoriaGasto` (ManyToOne, coluna `categoria_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias-gasto` | Lista todos |
| GET | `/categorias-gasto/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias-gasto` | Cria (201) |
| PUT | `/categorias-gasto/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias-gasto/{id}` | Exclui (204) |
| GET | `/lancamentos` | Lista todos |
| GET | `/lancamentos?categoriaId=1` | Lista filtrando por CategoriaGasto |
| GET | `/lancamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/lancamentos` | Cria (201) |
| PUT | `/lancamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/lancamentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/categorias-gasto

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): CategoriaGasto → Lancamento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/categorias-gasto -ContentType "application/json" -Body '{ "nome": "Maria Silva", "limiteMensal": 99.90 }'
```

```bash
# CategoriaGasto
curl -X POST http://localhost:8080/categorias-gasto -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "limiteMensal": 99.90 }'
curl http://localhost:8080/categorias-gasto
curl http://localhost:8080/categorias-gasto/1
curl -X PUT http://localhost:8080/categorias-gasto/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "limiteMensal": 99.90 }'
curl -X DELETE http://localhost:8080/categorias-gasto/1

# Lancamento
curl -X POST http://localhost:8080/lancamentos -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "dataLancamento": "2026-03-15", "receita": true, "categoria": { "id": 1 } }'
curl http://localhost:8080/lancamentos
curl http://localhost:8080/lancamentos/1
curl -X PUT http://localhost:8080/lancamentos/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "dataLancamento": "2026-03-15", "receita": true, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/lancamentos/1
```

## Estrutura

```
src/main/java/com/exemplo/orcamentodomestico/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
