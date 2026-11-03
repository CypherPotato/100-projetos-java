# 016 · Hospital

**Categoria:** Saúde
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Controle de alas, leitos e internações de um hospital.

## Entidades

### Ala (tabela `alas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `andar` | `Integer` |

### Leito (tabela `leitos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numero` | `Integer` |
| `ocupado` | `Boolean` |
| `diaria` | `BigDecimal` |
| `ala` | `Ala` (ManyToOne, coluna `ala_id`) |

### Internacao (tabela `internacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `paciente` | `String` |
| `dataEntrada` | `LocalDateTime` |
| `dataAlta` | `LocalDateTime` |
| `leito` | `Leito` (ManyToOne, coluna `leito_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/alas` | Lista todos |
| GET | `/alas/{id}` | Busca por id (404 se não existir) |
| POST | `/alas` | Cria (201) |
| PUT | `/alas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/alas/{id}` | Exclui (204) |
| GET | `/leitos` | Lista todos |
| GET | `/leitos?alaId=1` | Lista filtrando por Ala |
| GET | `/leitos/{id}` | Busca por id (404 se não existir) |
| POST | `/leitos` | Cria (201) |
| PUT | `/leitos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/leitos/{id}` | Exclui (204) |
| GET | `/internacoes` | Lista todos |
| GET | `/internacoes?leitoId=1` | Lista filtrando por Leito |
| GET | `/internacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/internacoes` | Cria (201) |
| PUT | `/internacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/internacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/alas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Ala → Leito → Internacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/alas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "andar": 10 }'
```

```bash
# Ala
curl -X POST http://localhost:8080/alas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "andar": 10 }'
curl http://localhost:8080/alas
curl http://localhost:8080/alas/1
curl -X PUT http://localhost:8080/alas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "andar": 10 }'
curl -X DELETE http://localhost:8080/alas/1

# Leito
curl -X POST http://localhost:8080/leitos -H "Content-Type: application/json" -d '{ "numero": 10, "ocupado": true, "diaria": 99.90, "ala": { "id": 1 } }'
curl http://localhost:8080/leitos
curl http://localhost:8080/leitos/1
curl -X PUT http://localhost:8080/leitos/1 -H "Content-Type: application/json" -d '{ "numero": 10, "ocupado": true, "diaria": 99.90, "ala": { "id": 1 } }'
curl -X DELETE http://localhost:8080/leitos/1

# Internacao
curl -X POST http://localhost:8080/internacoes -H "Content-Type: application/json" -d '{ "paciente": "Paciente exemplo", "dataEntrada": "2026-03-15T10:30:00", "dataAlta": "2026-03-15T10:30:00", "leito": { "id": 1 } }'
curl http://localhost:8080/internacoes
curl http://localhost:8080/internacoes/1
curl -X PUT http://localhost:8080/internacoes/1 -H "Content-Type: application/json" -d '{ "paciente": "Paciente exemplo", "dataEntrada": "2026-03-15T10:30:00", "dataAlta": "2026-03-15T10:30:00", "leito": { "id": 1 } }'
curl -X DELETE http://localhost:8080/internacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/hospital/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
