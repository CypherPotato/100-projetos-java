# 108 · Transporte Escolar

**Categoria:** Educação
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Gestão de veículos, rotas e passageiros do transporte escolar.

## Entidades

### Veiculo (tabela `veiculos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `capacidade` | `Integer` |

### Rota (tabela `rotas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `horarioSaida` | `String` |
| `distanciaKm` | `Double` |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

### Passageiro (tabela `passageiros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `endereco` | `String` |
| `mensalidade` | `BigDecimal` |
| `rota` | `Rota` (ManyToOne, coluna `rota_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/veiculos` | Lista todos |
| GET | `/veiculos/{id}` | Busca por id (404 se não existir) |
| POST | `/veiculos` | Cria (201) |
| PUT | `/veiculos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/veiculos/{id}` | Exclui (204) |
| GET | `/rotas` | Lista todos |
| GET | `/rotas?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/rotas/{id}` | Busca por id (404 se não existir) |
| POST | `/rotas` | Cria (201) |
| PUT | `/rotas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/rotas/{id}` | Exclui (204) |
| GET | `/passageiros` | Lista todos |
| GET | `/passageiros?rotaId=1` | Lista filtrando por Rota |
| GET | `/passageiros/{id}` | Busca por id (404 se não existir) |
| POST | `/passageiros` | Cria (201) |
| PUT | `/passageiros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/passageiros/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\transporte-escolar.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/veiculos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/transporte-escolar`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Veiculo → Rota → Passageiro.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/veiculos -ContentType "application/json" -Body '{ "placa": "ABC1D23", "capacidade": 10 }'
```

```bash
# Veiculo
curl -X POST http://localhost:8080/veiculos -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "capacidade": 10 }'
curl http://localhost:8080/veiculos
curl http://localhost:8080/veiculos/1
curl -X PUT http://localhost:8080/veiculos/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "capacidade": 10 }'
curl -X DELETE http://localhost:8080/veiculos/1

# Rota
curl -X POST http://localhost:8080/rotas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "horarioSaida": "HorarioSaida exemplo", "distanciaKm": 12.5, "veiculo": { "id": 1 } }'
curl http://localhost:8080/rotas
curl http://localhost:8080/rotas/1
curl -X PUT http://localhost:8080/rotas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "horarioSaida": "HorarioSaida exemplo", "distanciaKm": 12.5, "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/rotas/1

# Passageiro
curl -X POST http://localhost:8080/passageiros -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "endereco": "Rua das Flores, 100", "mensalidade": 99.90, "rota": { "id": 1 } }'
curl http://localhost:8080/passageiros
curl http://localhost:8080/passageiros/1
curl -X PUT http://localhost:8080/passageiros/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "endereco": "Rua das Flores, 100", "mensalidade": 99.90, "rota": { "id": 1 } }'
curl -X DELETE http://localhost:8080/passageiros/1
```

## Estrutura

```
src/main/java/com/exemplo/transporteescolar/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
