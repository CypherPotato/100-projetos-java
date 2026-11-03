# 141 · Controle de Backups

**Categoria:** Tecnologia
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Acompanhamento de rotinas de backup e de suas execuções.

## Entidades

### Rotina (tabela `rotinas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `destino` | `String` |
| `frequenciaHoras` | `Integer` |

### Execucao (tabela `execucoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `tamanhoMb` | `Double` |
| `sucesso` | `Boolean` |
| `rotina` | `Rotina` (ManyToOne, coluna `rotina_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/rotinas` | Lista todos |
| GET | `/rotinas/{id}` | Busca por id (404 se não existir) |
| POST | `/rotinas` | Cria (201) |
| PUT | `/rotinas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/rotinas/{id}` | Exclui (204) |
| GET | `/execucoes` | Lista todos |
| GET | `/execucoes?rotinaId=1` | Lista filtrando por Rotina |
| GET | `/execucoes/{id}` | Busca por id (404 se não existir) |
| POST | `/execucoes` | Cria (201) |
| PUT | `/execucoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/execucoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\controle-backups.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/rotinas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/controle-backups`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Rotina → Execucao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/rotinas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "destino": "Destino exemplo", "frequenciaHoras": 10 }'
```

```bash
# Rotina
curl -X POST http://localhost:8080/rotinas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "destino": "Destino exemplo", "frequenciaHoras": 10 }'
curl http://localhost:8080/rotinas
curl http://localhost:8080/rotinas/1
curl -X PUT http://localhost:8080/rotinas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "destino": "Destino exemplo", "frequenciaHoras": 10 }'
curl -X DELETE http://localhost:8080/rotinas/1

# Execucao
curl -X POST http://localhost:8080/execucoes -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "tamanhoMb": 12.5, "sucesso": true, "rotina": { "id": 1 } }'
curl http://localhost:8080/execucoes
curl http://localhost:8080/execucoes/1
curl -X PUT http://localhost:8080/execucoes/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "tamanhoMb": 12.5, "sucesso": true, "rotina": { "id": 1 } }'
curl -X DELETE http://localhost:8080/execucoes/1
```

## Estrutura

```
src/main/java/com/exemplo/controlebackups/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
