# 037 · Reserva de Salas

**Categoria:** Gestão
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Cadastro de salas de reunião e das reservas feitas em cada uma.

## Entidades

### Sala (tabela `salas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `capacidade` | `Integer` |
| `andar` | `Integer` |
| `possuiProjetor` | `Boolean` |

### Reserva (tabela `reservas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `responsavel` | `String` |
| `assunto` | `String` |
| `inicio` | `LocalDateTime` |
| `fim` | `LocalDateTime` |
| `sala` | `Sala` (ManyToOne, coluna `sala_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/salas` | Lista todos |
| GET | `/salas/{id}` | Busca por id (404 se não existir) |
| POST | `/salas` | Cria (201) |
| PUT | `/salas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/salas/{id}` | Exclui (204) |
| GET | `/reservas` | Lista todos |
| GET | `/reservas?salaId=1` | Lista filtrando por Sala |
| GET | `/reservas/{id}` | Busca por id (404 se não existir) |
| POST | `/reservas` | Cria (201) |
| PUT | `/reservas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/reservas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/salas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Sala → Reserva.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/salas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "capacidade": 10, "andar": 10, "possuiProjetor": true }'
```

```bash
# Sala
curl -X POST http://localhost:8080/salas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidade": 10, "andar": 10, "possuiProjetor": true }'
curl http://localhost:8080/salas
curl http://localhost:8080/salas/1
curl -X PUT http://localhost:8080/salas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "capacidade": 10, "andar": 10, "possuiProjetor": true }'
curl -X DELETE http://localhost:8080/salas/1

# Reserva
curl -X POST http://localhost:8080/reservas -H "Content-Type: application/json" -d '{ "responsavel": "Responsavel exemplo", "assunto": "Assunto exemplo", "inicio": "2026-03-15T10:30:00", "fim": "2026-03-15T10:30:00", "sala": { "id": 1 } }'
curl http://localhost:8080/reservas
curl http://localhost:8080/reservas/1
curl -X PUT http://localhost:8080/reservas/1 -H "Content-Type: application/json" -d '{ "responsavel": "Responsavel exemplo", "assunto": "Assunto exemplo", "inicio": "2026-03-15T10:30:00", "fim": "2026-03-15T10:30:00", "sala": { "id": 1 } }'
curl -X DELETE http://localhost:8080/reservas/1
```

## Estrutura

```
src/main/java/com/exemplo/reservasalas/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
