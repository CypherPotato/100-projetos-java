# 139 · Feature Flags

**Categoria:** Tecnologia
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Gerenciamento de flags de funcionalidades por aplicação.

## Entidades

### Aplicacao (tabela `aplicacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `responsavel` | `String` |

### Flag (tabela `flags`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `ativa` | `Boolean` |
| `percentualUsuarios` | `Integer` |
| `atualizadaEm` | `LocalDateTime` |
| `aplicacao` | `Aplicacao` (ManyToOne, coluna `aplicacao_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/aplicacoes` | Lista todos |
| GET | `/aplicacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/aplicacoes` | Cria (201) |
| PUT | `/aplicacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aplicacoes/{id}` | Exclui (204) |
| GET | `/flags` | Lista todos |
| GET | `/flags?aplicacaoId=1` | Lista filtrando por Aplicacao |
| GET | `/flags/{id}` | Busca por id (404 se não existir) |
| POST | `/flags` | Cria (201) |
| PUT | `/flags/{id}` | Atualiza (404 se não existir) |
| DELETE | `/flags/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\feature-flags.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/aplicacoes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/feature-flags`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Aplicacao → Flag.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/aplicacoes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
```

```bash
# Aplicacao
curl -X POST http://localhost:8080/aplicacoes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
curl http://localhost:8080/aplicacoes
curl http://localhost:8080/aplicacoes/1
curl -X PUT http://localhost:8080/aplicacoes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
curl -X DELETE http://localhost:8080/aplicacoes/1

# Flag
curl -X POST http://localhost:8080/flags -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "ativa": true, "percentualUsuarios": 10, "atualizadaEm": "2026-03-15T10:30:00", "aplicacao": { "id": 1 } }'
curl http://localhost:8080/flags
curl http://localhost:8080/flags/1
curl -X PUT http://localhost:8080/flags/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "ativa": true, "percentualUsuarios": 10, "atualizadaEm": "2026-03-15T10:30:00", "aplicacao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/flags/1
```

## Estrutura

```
src/main/java/com/exemplo/featureflags/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
