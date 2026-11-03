# 134 · Açougue

**Categoria:** Alimentação
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Cadastro de cortes de carne e registro das vendas por peso.

## Entidades

### Corte (tabela `cortes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `tipoCarne` | `String` |
| `precoKg` | `BigDecimal` |

### Venda (tabela `vendas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataVenda` | `LocalDate` |
| `pesoKg` | `Double` |
| `valorTotal` | `BigDecimal` |
| `cliente` | `String` |
| `corte` | `Corte` (ManyToOne, coluna `corte_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/cortes` | Lista todos |
| GET | `/cortes/{id}` | Busca por id (404 se não existir) |
| POST | `/cortes` | Cria (201) |
| PUT | `/cortes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cortes/{id}` | Exclui (204) |
| GET | `/vendas` | Lista todos |
| GET | `/vendas?corteId=1` | Lista filtrando por Corte |
| GET | `/vendas/{id}` | Busca por id (404 se não existir) |
| POST | `/vendas` | Cria (201) |
| PUT | `/vendas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vendas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\acougue.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/cortes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/acougue`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Corte → Venda.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/cortes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "tipoCarne": "TipoCarne exemplo", "precoKg": 99.90 }'
```

```bash
# Corte
curl -X POST http://localhost:8080/cortes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tipoCarne": "TipoCarne exemplo", "precoKg": 99.90 }'
curl http://localhost:8080/cortes
curl http://localhost:8080/cortes/1
curl -X PUT http://localhost:8080/cortes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tipoCarne": "TipoCarne exemplo", "precoKg": 99.90 }'
curl -X DELETE http://localhost:8080/cortes/1

# Venda
curl -X POST http://localhost:8080/vendas -H "Content-Type: application/json" -d '{ "dataVenda": "2026-03-15", "pesoKg": 12.5, "valorTotal": 99.90, "cliente": "Cliente exemplo", "corte": { "id": 1 } }'
curl http://localhost:8080/vendas
curl http://localhost:8080/vendas/1
curl -X PUT http://localhost:8080/vendas/1 -H "Content-Type: application/json" -d '{ "dataVenda": "2026-03-15", "pesoKg": 12.5, "valorTotal": 99.90, "cliente": "Cliente exemplo", "corte": { "id": 1 } }'
curl -X DELETE http://localhost:8080/vendas/1
```

## Estrutura

```
src/main/java/com/exemplo/acougue/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
