# 048 · Caixa Diário

**Categoria:** Finanças
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** sim

Abertura de caixas e registro das entradas e saídas do dia.

## Entidades

### Caixa (tabela `caixas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `operador` | `String` |
| `dataAbertura` | `LocalDateTime` |
| `saldoInicial` | `BigDecimal` |
| `aberto` | `Boolean` |

### MovimentoCaixa (tabela `movimentos_caixa`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `valor` | `BigDecimal` |
| `entrada` | `Boolean` |
| `dataHora` | `LocalDateTime` |
| `caixa` | `Caixa` (ManyToOne, coluna `caixa_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/caixas` | Lista todos |
| GET | `/caixas/{id}` | Busca por id (404 se não existir) |
| POST | `/caixas` | Cria (201) |
| PUT | `/caixas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/caixas/{id}` | Exclui (204) |
| GET | `/movimentos-caixa` | Lista todos |
| GET | `/movimentos-caixa?caixaId=1` | Lista filtrando por Caixa |
| GET | `/movimentos-caixa/{id}` | Busca por id (404 se não existir) |
| POST | `/movimentos-caixa` | Cria (201) |
| PUT | `/movimentos-caixa/{id}` | Atualiza (404 se não existir) |
| DELETE | `/movimentos-caixa/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/caixas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Caixa → MovimentoCaixa.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/caixas -ContentType "application/json" -Body '{ "operador": "Operador exemplo", "dataAbertura": "2026-03-15T10:30:00", "saldoInicial": 99.90, "aberto": true }'
```

```bash
# Caixa
curl -X POST http://localhost:8080/caixas -H "Content-Type: application/json" -d '{ "operador": "Operador exemplo", "dataAbertura": "2026-03-15T10:30:00", "saldoInicial": 99.90, "aberto": true }'
curl http://localhost:8080/caixas
curl http://localhost:8080/caixas/1
curl -X PUT http://localhost:8080/caixas/1 -H "Content-Type: application/json" -d '{ "operador": "Operador exemplo", "dataAbertura": "2026-03-15T10:30:00", "saldoInicial": 99.90, "aberto": true }'
curl -X DELETE http://localhost:8080/caixas/1

# MovimentoCaixa
curl -X POST http://localhost:8080/movimentos-caixa -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "entrada": true, "dataHora": "2026-03-15T10:30:00", "caixa": { "id": 1 } }'
curl http://localhost:8080/movimentos-caixa
curl http://localhost:8080/movimentos-caixa/1
curl -X PUT http://localhost:8080/movimentos-caixa/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valor": 99.90, "entrada": true, "dataHora": "2026-03-15T10:30:00", "caixa": { "id": 1 } }'
curl -X DELETE http://localhost:8080/movimentos-caixa/1
```

## Estrutura

```
src/main/java/com/exemplo/caixadiario/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
