# 077 · Condomínio

**Categoria:** Comunidade
**Lombok:** não · **Camada Service:** não

Controle de apartamentos e moradores de um condomínio residencial.

## Entidades

### Apartamento (tabela `apartamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numero` | `Integer` |
| `bloco` | `String` |
| `areaM2` | `Double` |
| `valorCondominio` | `BigDecimal` |

### Morador (tabela `moradores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `proprietario` | `Boolean` |
| `dataEntrada` | `LocalDate` |
| `apartamento` | `Apartamento` (ManyToOne, coluna `apartamento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/apartamentos` | Lista todos |
| GET | `/apartamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/apartamentos` | Cria (201) |
| PUT | `/apartamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/apartamentos/{id}` | Exclui (204) |
| GET | `/moradores` | Lista todos |
| GET | `/moradores?apartamentoId=1` | Lista filtrando por Apartamento |
| GET | `/moradores/{id}` | Busca por id (404 se não existir) |
| POST | `/moradores` | Cria (201) |
| PUT | `/moradores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/moradores/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/apartamentos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Apartamento → Morador.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/apartamentos -ContentType "application/json" -Body '{ "numero": 10, "bloco": "Bloco exemplo", "areaM2": 12.5, "valorCondominio": 99.90 }'
```

```bash
# Apartamento
curl -X POST http://localhost:8080/apartamentos -H "Content-Type: application/json" -d '{ "numero": 10, "bloco": "Bloco exemplo", "areaM2": 12.5, "valorCondominio": 99.90 }'
curl http://localhost:8080/apartamentos
curl http://localhost:8080/apartamentos/1
curl -X PUT http://localhost:8080/apartamentos/1 -H "Content-Type: application/json" -d '{ "numero": 10, "bloco": "Bloco exemplo", "areaM2": 12.5, "valorCondominio": 99.90 }'
curl -X DELETE http://localhost:8080/apartamentos/1

# Morador
curl -X POST http://localhost:8080/moradores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "proprietario": true, "dataEntrada": "2026-03-15", "apartamento": { "id": 1 } }'
curl http://localhost:8080/moradores
curl http://localhost:8080/moradores/1
curl -X PUT http://localhost:8080/moradores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "proprietario": true, "dataEntrada": "2026-03-15", "apartamento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/moradores/1
```

## Estrutura

```
src/main/java/com/exemplo/condominio/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
