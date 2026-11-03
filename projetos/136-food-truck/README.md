# 136 · Food Truck

**Categoria:** Alimentação
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Pontos de venda de um food truck e seu faturamento diário.

## Entidades

### PontoVenda (tabela `pontos_venda`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `endereco` | `String` |
| `diaSemana` | `String` |
| `horarioAbertura` | `String` |

### Faturamento (tabela `faturamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataDia` | `LocalDate` |
| `totalVendas` | `BigDecimal` |
| `clientesAtendidos` | `Integer` |
| `pontoVenda` | `PontoVenda` (ManyToOne, coluna `ponto_venda_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/pontos-venda` | Lista todos |
| GET | `/pontos-venda/{id}` | Busca por id (404 se não existir) |
| POST | `/pontos-venda` | Cria (201) |
| PUT | `/pontos-venda/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pontos-venda/{id}` | Exclui (204) |
| GET | `/faturamentos` | Lista todos |
| GET | `/faturamentos?pontoVendaId=1` | Lista filtrando por PontoVenda |
| GET | `/faturamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/faturamentos` | Cria (201) |
| PUT | `/faturamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/faturamentos/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\food-truck.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/pontos-venda
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/food-truck`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): PontoVenda → Faturamento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/pontos-venda -ContentType "application/json" -Body '{ "endereco": "Rua das Flores, 100", "diaSemana": "DiaSemana exemplo", "horarioAbertura": "HorarioAbertura exemplo" }'
```

```bash
# PontoVenda
curl -X POST http://localhost:8080/pontos-venda -H "Content-Type: application/json" -d '{ "endereco": "Rua das Flores, 100", "diaSemana": "DiaSemana exemplo", "horarioAbertura": "HorarioAbertura exemplo" }'
curl http://localhost:8080/pontos-venda
curl http://localhost:8080/pontos-venda/1
curl -X PUT http://localhost:8080/pontos-venda/1 -H "Content-Type: application/json" -d '{ "endereco": "Rua das Flores, 100", "diaSemana": "DiaSemana exemplo", "horarioAbertura": "HorarioAbertura exemplo" }'
curl -X DELETE http://localhost:8080/pontos-venda/1

# Faturamento
curl -X POST http://localhost:8080/faturamentos -H "Content-Type: application/json" -d '{ "dataDia": "2026-03-15", "totalVendas": 99.90, "clientesAtendidos": 10, "pontoVenda": { "id": 1 } }'
curl http://localhost:8080/faturamentos
curl http://localhost:8080/faturamentos/1
curl -X PUT http://localhost:8080/faturamentos/1 -H "Content-Type: application/json" -d '{ "dataDia": "2026-03-15", "totalVendas": 99.90, "clientesAtendidos": 10, "pontoVenda": { "id": 1 } }'
curl -X DELETE http://localhost:8080/faturamentos/1
```

## Estrutura

```
src/main/java/com/exemplo/foodtruck/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
