# 015 · Creche

**Categoria:** Educação
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** sim

Cadastro de responsáveis e crianças atendidas pela creche.

## Entidades

### Responsavel (tabela `responsaveis`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `email` | `String` |

### Crianca (tabela `criancas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataNascimento` | `LocalDate` |
| `periodoIntegral` | `Boolean` |
| `responsavel` | `Responsavel` (ManyToOne, coluna `responsavel_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/responsaveis` | Lista todos |
| GET | `/responsaveis/{id}` | Busca por id (404 se não existir) |
| POST | `/responsaveis` | Cria (201) |
| PUT | `/responsaveis/{id}` | Atualiza (404 se não existir) |
| DELETE | `/responsaveis/{id}` | Exclui (204) |
| GET | `/criancas` | Lista todos |
| GET | `/criancas?responsavelId=1` | Lista filtrando por Responsavel |
| GET | `/criancas/{id}` | Busca por id (404 se não existir) |
| POST | `/criancas` | Cria (201) |
| PUT | `/criancas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/criancas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/responsaveis

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Responsavel → Crianca.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/responsaveis -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com" }'
```

```bash
# Responsavel
curl -X POST http://localhost:8080/responsaveis -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com" }'
curl http://localhost:8080/responsaveis
curl http://localhost:8080/responsaveis/1
curl -X PUT http://localhost:8080/responsaveis/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/responsaveis/1

# Crianca
curl -X POST http://localhost:8080/criancas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "periodoIntegral": true, "responsavel": { "id": 1 } }'
curl http://localhost:8080/criancas
curl http://localhost:8080/criancas/1
curl -X PUT http://localhost:8080/criancas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "periodoIntegral": true, "responsavel": { "id": 1 } }'
curl -X DELETE http://localhost:8080/criancas/1
```

## Estrutura

```
src/main/java/com/exemplo/creche/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
