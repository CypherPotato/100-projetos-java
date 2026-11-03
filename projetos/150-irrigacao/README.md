# 150 · Irrigação

**Categoria:** Agro e Logística
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Registro de áreas irrigadas e dos acionamentos do sistema.

## Entidades

### Area (tabela `areas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `hectares` | `Double` |
| `cultura` | `String` |

### Acionamento (tabela `acionamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `inicio` | `LocalDateTime` |
| `duracaoMin` | `Integer` |
| `volumeLitros` | `Double` |
| `automatico` | `Boolean` |
| `area` | `Area` (ManyToOne, coluna `area_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/areas` | Lista todos |
| GET | `/areas/{id}` | Busca por id (404 se não existir) |
| POST | `/areas` | Cria (201) |
| PUT | `/areas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/areas/{id}` | Exclui (204) |
| GET | `/acionamentos` | Lista todos |
| GET | `/acionamentos?areaId=1` | Lista filtrando por Area |
| GET | `/acionamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/acionamentos` | Cria (201) |
| PUT | `/acionamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/acionamentos/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\irrigacao.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/areas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/irrigacao`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Area → Acionamento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/areas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "hectares": 12.5, "cultura": "Cultura exemplo" }'
```

```bash
# Area
curl -X POST http://localhost:8080/areas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "hectares": 12.5, "cultura": "Cultura exemplo" }'
curl http://localhost:8080/areas
curl http://localhost:8080/areas/1
curl -X PUT http://localhost:8080/areas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "hectares": 12.5, "cultura": "Cultura exemplo" }'
curl -X DELETE http://localhost:8080/areas/1

# Acionamento
curl -X POST http://localhost:8080/acionamentos -H "Content-Type: application/json" -d '{ "inicio": "2026-03-15T10:30:00", "duracaoMin": 10, "volumeLitros": 12.5, "automatico": true, "area": { "id": 1 } }'
curl http://localhost:8080/acionamentos
curl http://localhost:8080/acionamentos/1
curl -X PUT http://localhost:8080/acionamentos/1 -H "Content-Type: application/json" -d '{ "inicio": "2026-03-15T10:30:00", "duracaoMin": 10, "volumeLitros": 12.5, "automatico": true, "area": { "id": 1 } }'
curl -X DELETE http://localhost:8080/acionamentos/1
```

## Estrutura

```
src/main/java/com/exemplo/irrigacao/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
