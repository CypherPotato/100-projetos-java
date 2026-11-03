# 116 · Estúdio de Fotografia

**Categoria:** Serviços
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Agendamento de ensaios fotográficos para clientes.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |

### Ensaio (tabela `ensaios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `tema` | `String` |
| `dataEnsaio` | `LocalDateTime` |
| `quantidadeFotos` | `Integer` |
| `valor` | `BigDecimal` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/ensaios` | Lista todos |
| GET | `/ensaios?clienteId=1` | Lista filtrando por Cliente |
| GET | `/ensaios/{id}` | Busca por id (404 se não existir) |
| POST | `/ensaios` | Cria (201) |
| PUT | `/ensaios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/ensaios/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\estudio-fotografia.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/estudio-fotografia`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Ensaio.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/clientes/1

# Ensaio
curl -X POST http://localhost:8080/ensaios -H "Content-Type: application/json" -d '{ "tema": "Tema exemplo", "dataEnsaio": "2026-03-15T10:30:00", "quantidadeFotos": 10, "valor": 99.90, "cliente": { "id": 1 } }'
curl http://localhost:8080/ensaios
curl http://localhost:8080/ensaios/1
curl -X PUT http://localhost:8080/ensaios/1 -H "Content-Type: application/json" -d '{ "tema": "Tema exemplo", "dataEnsaio": "2026-03-15T10:30:00", "quantidadeFotos": 10, "valor": 99.90, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/ensaios/1
```

## Estrutura

```
src/main/java/com/exemplo/estudiofotografia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
