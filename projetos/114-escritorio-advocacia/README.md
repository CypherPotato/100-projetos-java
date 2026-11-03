# 114 · Escritório de Advocacia

**Categoria:** Serviços
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Acompanhamento de clientes, processos e audiências de um escritório.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cpf` | `String` |
| `telefone` | `String` |

### Processo (tabela `processos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numeroProcesso` | `String` |
| `area` | `String` |
| `dataAbertura` | `LocalDate` |
| `valorCausa` | `BigDecimal` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

### Audiencia (tabela `audiencias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `local` | `String` |
| `realizada` | `Boolean` |
| `processo` | `Processo` (ManyToOne, coluna `processo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/processos` | Lista todos |
| GET | `/processos?clienteId=1` | Lista filtrando por Cliente |
| GET | `/processos/{id}` | Busca por id (404 se não existir) |
| POST | `/processos` | Cria (201) |
| PUT | `/processos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/processos/{id}` | Exclui (204) |
| GET | `/audiencias` | Lista todos |
| GET | `/audiencias?processoId=1` | Lista filtrando por Processo |
| GET | `/audiencias/{id}` | Busca por id (404 se não existir) |
| POST | `/audiencias` | Cria (201) |
| PUT | `/audiencias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/audiencias/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\escritorio-advocacia.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/escritorio-advocacia`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Processo → Audiencia.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "telefone": "(11) 99999-0000" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/clientes/1

# Processo
curl -X POST http://localhost:8080/processos -H "Content-Type: application/json" -d '{ "numeroProcesso": "NumeroProcesso exemplo", "area": "Area exemplo", "dataAbertura": "2026-03-15", "valorCausa": 99.90, "cliente": { "id": 1 } }'
curl http://localhost:8080/processos
curl http://localhost:8080/processos/1
curl -X PUT http://localhost:8080/processos/1 -H "Content-Type: application/json" -d '{ "numeroProcesso": "NumeroProcesso exemplo", "area": "Area exemplo", "dataAbertura": "2026-03-15", "valorCausa": 99.90, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/processos/1

# Audiencia
curl -X POST http://localhost:8080/audiencias -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "local": "Local exemplo", "realizada": true, "processo": { "id": 1 } }'
curl http://localhost:8080/audiencias
curl http://localhost:8080/audiencias/1
curl -X PUT http://localhost:8080/audiencias/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "local": "Local exemplo", "realizada": true, "processo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/audiencias/1
```

## Estrutura

```
src/main/java/com/exemplo/escritorioadvocacia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
