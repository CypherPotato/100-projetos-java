# 124 · Corretora de Seguros

**Categoria:** Finanças
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Controle de segurados, apólices e sinistros de uma corretora.

## Entidades

### Segurado (tabela `segurados`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cpf` | `String` |

### Apolice (tabela `apolices`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numero` | `String` |
| `tipoSeguro` | `String` |
| `premio` | `BigDecimal` |
| `vigenciaFim` | `LocalDate` |
| `segurado` | `Segurado` (ManyToOne, coluna `segurado_id`) |

### Sinistro (tabela `sinistros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataOcorrencia` | `LocalDate` |
| `descricao` | `String` |
| `valorIndenizado` | `BigDecimal` |
| `apolice` | `Apolice` (ManyToOne, coluna `apolice_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/segurados` | Lista todos |
| GET | `/segurados/{id}` | Busca por id (404 se não existir) |
| POST | `/segurados` | Cria (201) |
| PUT | `/segurados/{id}` | Atualiza (404 se não existir) |
| DELETE | `/segurados/{id}` | Exclui (204) |
| GET | `/apolices` | Lista todos |
| GET | `/apolices?seguradoId=1` | Lista filtrando por Segurado |
| GET | `/apolices/{id}` | Busca por id (404 se não existir) |
| POST | `/apolices` | Cria (201) |
| PUT | `/apolices/{id}` | Atualiza (404 se não existir) |
| DELETE | `/apolices/{id}` | Exclui (204) |
| GET | `/sinistros` | Lista todos |
| GET | `/sinistros?apoliceId=1` | Lista filtrando por Apolice |
| GET | `/sinistros/{id}` | Busca por id (404 se não existir) |
| POST | `/sinistros` | Cria (201) |
| PUT | `/sinistros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/sinistros/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\corretora-seguros.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/segurados
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/corretora-seguros`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Segurado → Apolice → Sinistro.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/segurados -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cpf": "123.456.789-00" }'
```

```bash
# Segurado
curl -X POST http://localhost:8080/segurados -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00" }'
curl http://localhost:8080/segurados
curl http://localhost:8080/segurados/1
curl -X PUT http://localhost:8080/segurados/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cpf": "123.456.789-00" }'
curl -X DELETE http://localhost:8080/segurados/1

# Apolice
curl -X POST http://localhost:8080/apolices -H "Content-Type: application/json" -d '{ "numero": "Numero exemplo", "tipoSeguro": "TipoSeguro exemplo", "premio": 99.90, "vigenciaFim": "2026-03-15", "segurado": { "id": 1 } }'
curl http://localhost:8080/apolices
curl http://localhost:8080/apolices/1
curl -X PUT http://localhost:8080/apolices/1 -H "Content-Type: application/json" -d '{ "numero": "Numero exemplo", "tipoSeguro": "TipoSeguro exemplo", "premio": 99.90, "vigenciaFim": "2026-03-15", "segurado": { "id": 1 } }'
curl -X DELETE http://localhost:8080/apolices/1

# Sinistro
curl -X POST http://localhost:8080/sinistros -H "Content-Type: application/json" -d '{ "dataOcorrencia": "2026-03-15", "descricao": "Descrição de exemplo", "valorIndenizado": 99.90, "apolice": { "id": 1 } }'
curl http://localhost:8080/sinistros
curl http://localhost:8080/sinistros/1
curl -X PUT http://localhost:8080/sinistros/1 -H "Content-Type: application/json" -d '{ "dataOcorrencia": "2026-03-15", "descricao": "Descrição de exemplo", "valorIndenizado": 99.90, "apolice": { "id": 1 } }'
curl -X DELETE http://localhost:8080/sinistros/1
```

## Estrutura

```
src/main/java/com/exemplo/corretoraseguros/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
