# 081 · Cooperativa

**Categoria:** Comunidade
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** sim

Controle de cooperados e das contribuições mensais da cooperativa.

## Entidades

### Cooperado (tabela `cooperados`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cpf` | `String` |
| `dataAdmissao` | `LocalDate` |
| `capitalSocial` | `BigDecimal` |

### Contribuicao (tabela `contribuicoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `competencia` | `String` |
| `valor` | `BigDecimal` |
| `dataPagamento` | `LocalDate` |
| `paga` | `Boolean` |
| `cooperado` | `Cooperado` (ManyToOne, coluna `cooperado_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/cooperados` | Lista todos |
| GET | `/cooperados/{id}` | Busca por id (404 se não existir) |
| POST | `/cooperados` | Cria (201) |
| PUT | `/cooperados/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cooperados/{id}` | Exclui (204) |
| GET | `/contribuicoes` | Lista todos |
| GET | `/contribuicoes?cooperadoId=1` | Lista filtrando por Cooperado |
| GET | `/contribuicoes/{id}` | Busca por id (404 se não existir) |
| POST | `/contribuicoes` | Cria (201) |
| PUT | `/contribuicoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/contribuicoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/cooperados

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cooperado → Contribuicao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/cooperados -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "dataAdmissao": "2026-03-15", "capitalSocial": 99.90 }'
```

```bash
# Cooperado
curl -X POST http://localhost:8080/cooperados -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "dataAdmissao": "2026-03-15", "capitalSocial": 99.90 }'
curl http://localhost:8080/cooperados
curl http://localhost:8080/cooperados/1
curl -X PUT http://localhost:8080/cooperados/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "dataAdmissao": "2026-03-15", "capitalSocial": 99.90 }'
curl -X DELETE http://localhost:8080/cooperados/1

# Contribuicao
curl -X POST http://localhost:8080/contribuicoes -H "Content-Type: application/json" -d '{ "competencia": "Competencia exemplo", "valor": 99.90, "dataPagamento": "2026-03-15", "paga": true, "cooperado": { "id": 1 } }'
curl http://localhost:8080/contribuicoes
curl http://localhost:8080/contribuicoes/1
curl -X PUT http://localhost:8080/contribuicoes/1 -H "Content-Type: application/json" -d '{ "competencia": "Competencia exemplo", "valor": 99.90, "dataPagamento": "2026-03-15", "paga": true, "cooperado": { "id": 1 } }'
curl -X DELETE http://localhost:8080/contribuicoes/1
```

## Estrutura

```
src/main/java/com/exemplo/cooperativa/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
