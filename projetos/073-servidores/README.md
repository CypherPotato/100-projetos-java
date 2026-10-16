# 073 · Servidores e Aplicações

**Categoria:** Tecnologia
**Lombok:** sim · **Camada Service:** não

Cadastro de servidores e das aplicações hospedadas em cada um.

## Entidades

### Servidor (tabela `servidores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `hostname` | `String` |
| `enderecoIp` | `String` |
| `sistemaOperacional` | `String` |
| `memoriaGb` | `Integer` |

### Aplicacao (tabela `aplicacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `versao` | `String` |
| `porta` | `Integer` |
| `emExecucao` | `Boolean` |
| `servidor` | `Servidor` (ManyToOne, coluna `servidor_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/servidores` | Lista todos |
| GET | `/servidores/{id}` | Busca por id (404 se não existir) |
| POST | `/servidores` | Cria (201) |
| PUT | `/servidores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/servidores/{id}` | Exclui (204) |
| GET | `/aplicacoes` | Lista todos |
| GET | `/aplicacoes?servidorId=1` | Lista filtrando por Servidor |
| GET | `/aplicacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/aplicacoes` | Cria (201) |
| PUT | `/aplicacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aplicacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/servidores

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Servidor → Aplicacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/servidores -ContentType "application/json" -Body '{ "hostname": "Hostname exemplo", "enderecoIp": "EnderecoIp exemplo", "sistemaOperacional": "SistemaOperacional exemplo", "memoriaGb": 10 }'
```

```bash
# Servidor
curl -X POST http://localhost:8080/servidores -H "Content-Type: application/json" -d '{ "hostname": "Hostname exemplo", "enderecoIp": "EnderecoIp exemplo", "sistemaOperacional": "SistemaOperacional exemplo", "memoriaGb": 10 }'
curl http://localhost:8080/servidores
curl http://localhost:8080/servidores/1
curl -X PUT http://localhost:8080/servidores/1 -H "Content-Type: application/json" -d '{ "hostname": "Hostname exemplo", "enderecoIp": "EnderecoIp exemplo", "sistemaOperacional": "SistemaOperacional exemplo", "memoriaGb": 10 }'
curl -X DELETE http://localhost:8080/servidores/1

# Aplicacao
curl -X POST http://localhost:8080/aplicacoes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "versao": "Versao exemplo", "porta": 10, "emExecucao": true, "servidor": { "id": 1 } }'
curl http://localhost:8080/aplicacoes
curl http://localhost:8080/aplicacoes/1
curl -X PUT http://localhost:8080/aplicacoes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "versao": "Versao exemplo", "porta": 10, "emExecucao": true, "servidor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aplicacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/servidores/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
