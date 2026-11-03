# 045 · Controle de Contratos

**Categoria:** Gestão
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** sim

Gestão de empresas contratadas, contratos vigentes e seus aditivos.

## Entidades

### Empresa (tabela `empresas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `razaoSocial` | `String` |
| `cnpj` | `String` |
| `email` | `String` |

### Contrato (tabela `contratos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `objeto` | `String` |
| `valorMensal` | `BigDecimal` |
| `dataInicio` | `LocalDate` |
| `dataFim` | `LocalDate` |
| `vigente` | `Boolean` |
| `empresa` | `Empresa` (ManyToOne, coluna `empresa_id`) |

### Aditivo (tabela `aditivos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `valorAdicional` | `BigDecimal` |
| `dataAssinatura` | `LocalDate` |
| `contrato` | `Contrato` (ManyToOne, coluna `contrato_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/empresas` | Lista todos |
| GET | `/empresas/{id}` | Busca por id (404 se não existir) |
| POST | `/empresas` | Cria (201) |
| PUT | `/empresas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/empresas/{id}` | Exclui (204) |
| GET | `/contratos` | Lista todos |
| GET | `/contratos?empresaId=1` | Lista filtrando por Empresa |
| GET | `/contratos/{id}` | Busca por id (404 se não existir) |
| POST | `/contratos` | Cria (201) |
| PUT | `/contratos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/contratos/{id}` | Exclui (204) |
| GET | `/aditivos` | Lista todos |
| GET | `/aditivos?contratoId=1` | Lista filtrando por Contrato |
| GET | `/aditivos/{id}` | Busca por id (404 se não existir) |
| POST | `/aditivos` | Cria (201) |
| PUT | `/aditivos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aditivos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/empresas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Empresa → Contrato → Aditivo.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/empresas -ContentType "application/json" -Body '{ "razaoSocial": "RazaoSocial exemplo", "cnpj": "12.345.678/0001-00", "email": "maria@exemplo.com" }'
```

```bash
# Empresa
curl -X POST http://localhost:8080/empresas -H "Content-Type: application/json" -d '{ "razaoSocial": "RazaoSocial exemplo", "cnpj": "12.345.678/0001-00", "email": "maria@exemplo.com" }'
curl http://localhost:8080/empresas
curl http://localhost:8080/empresas/1
curl -X PUT http://localhost:8080/empresas/1 -H "Content-Type: application/json" -d '{ "razaoSocial": "RazaoSocial exemplo", "cnpj": "12.345.678/0001-00", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/empresas/1

# Contrato
curl -X POST http://localhost:8080/contratos -H "Content-Type: application/json" -d '{ "objeto": "Objeto exemplo", "valorMensal": 99.90, "dataInicio": "2026-03-15", "dataFim": "2026-03-15", "vigente": true, "empresa": { "id": 1 } }'
curl http://localhost:8080/contratos
curl http://localhost:8080/contratos/1
curl -X PUT http://localhost:8080/contratos/1 -H "Content-Type: application/json" -d '{ "objeto": "Objeto exemplo", "valorMensal": 99.90, "dataInicio": "2026-03-15", "dataFim": "2026-03-15", "vigente": true, "empresa": { "id": 1 } }'
curl -X DELETE http://localhost:8080/contratos/1

# Aditivo
curl -X POST http://localhost:8080/aditivos -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valorAdicional": 99.90, "dataAssinatura": "2026-03-15", "contrato": { "id": 1 } }'
curl http://localhost:8080/aditivos
curl http://localhost:8080/aditivos/1
curl -X PUT http://localhost:8080/aditivos/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "valorAdicional": 99.90, "dataAssinatura": "2026-03-15", "contrato": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aditivos/1
```

## Estrutura

```
src/main/java/com/exemplo/controlecontratos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
