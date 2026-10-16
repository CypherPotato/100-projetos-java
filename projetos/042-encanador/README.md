# 042 · Encanador

**Categoria:** Serviços
**Lombok:** sim · **Camada Service:** sim

Agenda de clientes e visitas técnicas de um serviço de encanamento.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `endereco` | `String` |

### VisitaTecnica (tabela `visitas_tecnicas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricaoProblema` | `String` |
| `valorMaoDeObra` | `BigDecimal` |
| `dataVisita` | `LocalDate` |
| `resolvida` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/visitas-tecnicas` | Lista todos |
| GET | `/visitas-tecnicas?clienteId=1` | Lista filtrando por Cliente |
| GET | `/visitas-tecnicas/{id}` | Busca por id (404 se não existir) |
| POST | `/visitas-tecnicas` | Cria (201) |
| PUT | `/visitas-tecnicas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/visitas-tecnicas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → VisitaTecnica.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "endereco": "Rua das Flores, 100" }'
curl -X DELETE http://localhost:8080/clientes/1

# VisitaTecnica
curl -X POST http://localhost:8080/visitas-tecnicas -H "Content-Type: application/json" -d '{ "descricaoProblema": "DescricaoProblema exemplo", "valorMaoDeObra": 99.90, "dataVisita": "2026-03-15", "resolvida": true, "cliente": { "id": 1 } }'
curl http://localhost:8080/visitas-tecnicas
curl http://localhost:8080/visitas-tecnicas/1
curl -X PUT http://localhost:8080/visitas-tecnicas/1 -H "Content-Type: application/json" -d '{ "descricaoProblema": "DescricaoProblema exemplo", "valorMaoDeObra": 99.90, "dataVisita": "2026-03-15", "resolvida": true, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/visitas-tecnicas/1
```

## Estrutura

```
src/main/java/com/exemplo/encanador/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
