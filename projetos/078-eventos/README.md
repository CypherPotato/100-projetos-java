# 078 · Eventos

**Categoria:** Comunidade
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** sim

Organização de eventos comunitários e das inscrições dos participantes.

## Entidades

### Evento (tabela `eventos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `local` | `String` |
| `dataHora` | `LocalDateTime` |
| `capacidade` | `Integer` |
| `valorIngresso` | `BigDecimal` |

### Inscricao (tabela `inscricoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nomeParticipante` | `String` |
| `email` | `String` |
| `dataInscricao` | `LocalDateTime` |
| `pago` | `Boolean` |
| `evento` | `Evento` (ManyToOne, coluna `evento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/eventos` | Lista todos |
| GET | `/eventos/{id}` | Busca por id (404 se não existir) |
| POST | `/eventos` | Cria (201) |
| PUT | `/eventos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/eventos/{id}` | Exclui (204) |
| GET | `/inscricoes` | Lista todos |
| GET | `/inscricoes?eventoId=1` | Lista filtrando por Evento |
| GET | `/inscricoes/{id}` | Busca por id (404 se não existir) |
| POST | `/inscricoes` | Cria (201) |
| PUT | `/inscricoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/inscricoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/eventos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Evento → Inscricao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/eventos -ContentType "application/json" -Body '{ "titulo": "Exemplo", "local": "Local exemplo", "dataHora": "2026-03-15T10:30:00", "capacidade": 10, "valorIngresso": 99.90 }'
```

```bash
# Evento
curl -X POST http://localhost:8080/eventos -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "local": "Local exemplo", "dataHora": "2026-03-15T10:30:00", "capacidade": 10, "valorIngresso": 99.90 }'
curl http://localhost:8080/eventos
curl http://localhost:8080/eventos/1
curl -X PUT http://localhost:8080/eventos/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "local": "Local exemplo", "dataHora": "2026-03-15T10:30:00", "capacidade": 10, "valorIngresso": 99.90 }'
curl -X DELETE http://localhost:8080/eventos/1

# Inscricao
curl -X POST http://localhost:8080/inscricoes -H "Content-Type: application/json" -d '{ "nomeParticipante": "NomeParticipante exemplo", "email": "maria@exemplo.com", "dataInscricao": "2026-03-15T10:30:00", "pago": true, "evento": { "id": 1 } }'
curl http://localhost:8080/inscricoes
curl http://localhost:8080/inscricoes/1
curl -X PUT http://localhost:8080/inscricoes/1 -H "Content-Type: application/json" -d '{ "nomeParticipante": "NomeParticipante exemplo", "email": "maria@exemplo.com", "dataInscricao": "2026-03-15T10:30:00", "pago": true, "evento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/inscricoes/1
```

## Estrutura

```
src/main/java/com/exemplo/eventos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
