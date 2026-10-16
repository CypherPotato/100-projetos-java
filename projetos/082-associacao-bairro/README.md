# 082 · Associação de Bairro

**Categoria:** Comunidade
**Lombok:** sim · **Camada Service:** não

Cadastro de ruas e associados de uma associação de moradores.

## Entidades

### Rua (tabela `ruas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cep` | `String` |
| `extensaoKm` | `Double` |

### Associado (tabela `associados`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `numeroCasa` | `Integer` |
| `mensalidade` | `BigDecimal` |
| `adimplente` | `Boolean` |
| `rua` | `Rua` (ManyToOne, coluna `rua_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/ruas` | Lista todos |
| GET | `/ruas/{id}` | Busca por id (404 se não existir) |
| POST | `/ruas` | Cria (201) |
| PUT | `/ruas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/ruas/{id}` | Exclui (204) |
| GET | `/associados` | Lista todos |
| GET | `/associados?ruaId=1` | Lista filtrando por Rua |
| GET | `/associados/{id}` | Busca por id (404 se não existir) |
| POST | `/associados` | Cria (201) |
| PUT | `/associados/{id}` | Atualiza (404 se não existir) |
| DELETE | `/associados/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/ruas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Rua → Associado.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/ruas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cep": "01000-000", "extensaoKm": 12.5 }'
```

```bash
# Rua
curl -X POST http://localhost:8080/ruas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cep": "01000-000", "extensaoKm": 12.5 }'
curl http://localhost:8080/ruas
curl http://localhost:8080/ruas/1
curl -X PUT http://localhost:8080/ruas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cep": "01000-000", "extensaoKm": 12.5 }'
curl -X DELETE http://localhost:8080/ruas/1

# Associado
curl -X POST http://localhost:8080/associados -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "numeroCasa": 10, "mensalidade": 99.90, "adimplente": true, "rua": { "id": 1 } }'
curl http://localhost:8080/associados
curl http://localhost:8080/associados/1
curl -X PUT http://localhost:8080/associados/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "numeroCasa": 10, "mensalidade": 99.90, "adimplente": true, "rua": { "id": 1 } }'
curl -X DELETE http://localhost:8080/associados/1
```

## Estrutura

```
src/main/java/com/exemplo/associacaobairro/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
