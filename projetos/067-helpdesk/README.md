# 067 · Helpdesk

**Categoria:** Tecnologia
**Lombok:** sim · **Camada Service:** não

Abertura e acompanhamento de chamados de suporte técnico.

## Entidades

### Solicitante (tabela `solicitantes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `email` | `String` |
| `setor` | `String` |

### Chamado (tabela `chamados`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `descricao` | `String` |
| `prioridade` | `String` |
| `abertoEm` | `LocalDateTime` |
| `resolvido` | `Boolean` |
| `solicitante` | `Solicitante` (ManyToOne, coluna `solicitante_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/solicitantes` | Lista todos |
| GET | `/solicitantes/{id}` | Busca por id (404 se não existir) |
| POST | `/solicitantes` | Cria (201) |
| PUT | `/solicitantes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/solicitantes/{id}` | Exclui (204) |
| GET | `/chamados` | Lista todos |
| GET | `/chamados?solicitanteId=1` | Lista filtrando por Solicitante |
| GET | `/chamados/{id}` | Busca por id (404 se não existir) |
| POST | `/chamados` | Cria (201) |
| PUT | `/chamados/{id}` | Atualiza (404 se não existir) |
| DELETE | `/chamados/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/solicitantes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Solicitante → Chamado.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/solicitantes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "setor": "Setor exemplo" }'
```

```bash
# Solicitante
curl -X POST http://localhost:8080/solicitantes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "setor": "Setor exemplo" }'
curl http://localhost:8080/solicitantes
curl http://localhost:8080/solicitantes/1
curl -X PUT http://localhost:8080/solicitantes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "email": "maria@exemplo.com", "setor": "Setor exemplo" }'
curl -X DELETE http://localhost:8080/solicitantes/1

# Chamado
curl -X POST http://localhost:8080/chamados -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "descricao": "Descrição de exemplo", "prioridade": "Prioridade exemplo", "abertoEm": "2026-03-15T10:30:00", "resolvido": true, "solicitante": { "id": 1 } }'
curl http://localhost:8080/chamados
curl http://localhost:8080/chamados/1
curl -X PUT http://localhost:8080/chamados/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "descricao": "Descrição de exemplo", "prioridade": "Prioridade exemplo", "abertoEm": "2026-03-15T10:30:00", "resolvido": true, "solicitante": { "id": 1 } }'
curl -X DELETE http://localhost:8080/chamados/1
```

## Estrutura

```
src/main/java/com/exemplo/helpdesk/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
