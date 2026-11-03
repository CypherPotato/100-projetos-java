# 148 · Laticínio

**Categoria:** Agro e Logística
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Acompanhamento da coleta de leite dos produtores e dos lotes fabricados.

## Entidades

### Produtor (tabela `produtores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `propriedade` | `String` |

### Coleta (tabela `coletas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataColeta` | `LocalDate` |
| `litros` | `Double` |
| `temperatura` | `Double` |
| `produtor` | `Produtor` (ManyToOne, coluna `produtor_id`) |

### Lote (tabela `lotes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `produto` | `String` |
| `unidades` | `Integer` |
| `dataFabricacao` | `LocalDate` |
| `coleta` | `Coleta` (ManyToOne, coluna `coleta_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/produtores` | Lista todos |
| GET | `/produtores/{id}` | Busca por id (404 se não existir) |
| POST | `/produtores` | Cria (201) |
| PUT | `/produtores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/produtores/{id}` | Exclui (204) |
| GET | `/coletas` | Lista todos |
| GET | `/coletas?produtorId=1` | Lista filtrando por Produtor |
| GET | `/coletas/{id}` | Busca por id (404 se não existir) |
| POST | `/coletas` | Cria (201) |
| PUT | `/coletas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/coletas/{id}` | Exclui (204) |
| GET | `/lotes` | Lista todos |
| GET | `/lotes?coletaId=1` | Lista filtrando por Coleta |
| GET | `/lotes/{id}` | Busca por id (404 se não existir) |
| POST | `/lotes` | Cria (201) |
| PUT | `/lotes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/lotes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\laticinio.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/produtores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/laticinio`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Produtor → Coleta → Lote.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/produtores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "propriedade": "Propriedade exemplo" }'
```

```bash
# Produtor
curl -X POST http://localhost:8080/produtores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "propriedade": "Propriedade exemplo" }'
curl http://localhost:8080/produtores
curl http://localhost:8080/produtores/1
curl -X PUT http://localhost:8080/produtores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "propriedade": "Propriedade exemplo" }'
curl -X DELETE http://localhost:8080/produtores/1

# Coleta
curl -X POST http://localhost:8080/coletas -H "Content-Type: application/json" -d '{ "dataColeta": "2026-03-15", "litros": 12.5, "temperatura": 12.5, "produtor": { "id": 1 } }'
curl http://localhost:8080/coletas
curl http://localhost:8080/coletas/1
curl -X PUT http://localhost:8080/coletas/1 -H "Content-Type: application/json" -d '{ "dataColeta": "2026-03-15", "litros": 12.5, "temperatura": 12.5, "produtor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/coletas/1

# Lote
curl -X POST http://localhost:8080/lotes -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "unidades": 10, "dataFabricacao": "2026-03-15", "coleta": { "id": 1 } }'
curl http://localhost:8080/lotes
curl http://localhost:8080/lotes/1
curl -X PUT http://localhost:8080/lotes/1 -H "Content-Type: application/json" -d '{ "produto": "Produto exemplo", "unidades": 10, "dataFabricacao": "2026-03-15", "coleta": { "id": 1 } }'
curl -X DELETE http://localhost:8080/lotes/1
```

## Estrutura

```
src/main/java/com/exemplo/laticinio/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
