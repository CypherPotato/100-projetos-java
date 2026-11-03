# 126 · Casa de Câmbio

**Categoria:** Finanças
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Registro de moedas, cotações e operações de câmbio.

## Entidades

### Moeda (tabela `moedas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `codigo` | `String` |
| `nome` | `String` |
| `cotacao` | `BigDecimal` |

### Operacao (tabela `operacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `cliente` | `String` |
| `quantidade` | `BigDecimal` |
| `valorTotal` | `BigDecimal` |
| `moeda` | `Moeda` (ManyToOne, coluna `moeda_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/moedas` | Lista todos |
| GET | `/moedas/{id}` | Busca por id (404 se não existir) |
| POST | `/moedas` | Cria (201) |
| PUT | `/moedas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/moedas/{id}` | Exclui (204) |
| GET | `/operacoes` | Lista todos |
| GET | `/operacoes?moedaId=1` | Lista filtrando por Moeda |
| GET | `/operacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/operacoes` | Cria (201) |
| PUT | `/operacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/operacoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\casa-cambio.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/moedas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/casa-cambio`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Moeda → Operacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/moedas -ContentType "application/json" -Body '{ "codigo": "COD-001", "nome": "Maria Silva", "cotacao": 99.90 }'
```

```bash
# Moeda
curl -X POST http://localhost:8080/moedas -H "Content-Type: application/json" -d '{ "codigo": "COD-001", "nome": "Maria Silva", "cotacao": 99.90 }'
curl http://localhost:8080/moedas
curl http://localhost:8080/moedas/1
curl -X PUT http://localhost:8080/moedas/1 -H "Content-Type: application/json" -d '{ "codigo": "COD-001", "nome": "Maria Silva", "cotacao": 99.90 }'
curl -X DELETE http://localhost:8080/moedas/1

# Operacao
curl -X POST http://localhost:8080/operacoes -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "cliente": "Cliente exemplo", "quantidade": 99.90, "valorTotal": 99.90, "moeda": { "id": 1 } }'
curl http://localhost:8080/operacoes
curl http://localhost:8080/operacoes/1
curl -X PUT http://localhost:8080/operacoes/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "cliente": "Cliente exemplo", "quantidade": 99.90, "valorTotal": 99.90, "moeda": { "id": 1 } }'
curl -X DELETE http://localhost:8080/operacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/casacambio/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
