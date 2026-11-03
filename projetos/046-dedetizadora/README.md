# 046 · Dedetizadora

**Categoria:** Serviços
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Registro de clientes e aplicações de controle de pragas.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `endereco` | `String` |

### Aplicacao (tabela `aplicacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `praga` | `String` |
| `produto` | `String` |
| `valor` | `BigDecimal` |
| `dataAplicacao` | `LocalDate` |
| `garantiaMeses` | `Integer` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/aplicacoes` | Lista todos |
| GET | `/aplicacoes?clienteId=1` | Lista filtrando por Cliente |
| GET | `/aplicacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/aplicacoes` | Cria (201) |
| PUT | `/aplicacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aplicacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Aplicacao.

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

# Aplicacao
curl -X POST http://localhost:8080/aplicacoes -H "Content-Type: application/json" -d '{ "praga": "Praga exemplo", "produto": "Produto exemplo", "valor": 99.90, "dataAplicacao": "2026-03-15", "garantiaMeses": 10, "cliente": { "id": 1 } }'
curl http://localhost:8080/aplicacoes
curl http://localhost:8080/aplicacoes/1
curl -X PUT http://localhost:8080/aplicacoes/1 -H "Content-Type: application/json" -d '{ "praga": "Praga exemplo", "produto": "Produto exemplo", "valor": 99.90, "dataAplicacao": "2026-03-15", "garantiaMeses": 10, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aplicacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/dedetizadora/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
