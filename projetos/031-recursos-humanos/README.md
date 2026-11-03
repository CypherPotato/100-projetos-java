# 031 · Recursos Humanos

**Categoria:** Gestão
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** não

Cadastro de departamentos e funcionários de uma empresa.

## Entidades

### Departamento (tabela `departamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `localizacao` | `String` |

### Funcionario (tabela `funcionarios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cargo` | `String` |
| `salario` | `BigDecimal` |
| `dataAdmissao` | `LocalDate` |
| `ativo` | `Boolean` |
| `departamento` | `Departamento` (ManyToOne, coluna `departamento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/departamentos` | Lista todos |
| GET | `/departamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/departamentos` | Cria (201) |
| PUT | `/departamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/departamentos/{id}` | Exclui (204) |
| GET | `/funcionarios` | Lista todos |
| GET | `/funcionarios?departamentoId=1` | Lista filtrando por Departamento |
| GET | `/funcionarios/{id}` | Busca por id (404 se não existir) |
| POST | `/funcionarios` | Cria (201) |
| PUT | `/funcionarios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/funcionarios/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/departamentos

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Departamento → Funcionario.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/departamentos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "localizacao": "Localizacao exemplo" }'
```

```bash
# Departamento
curl -X POST http://localhost:8080/departamentos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "localizacao": "Localizacao exemplo" }'
curl http://localhost:8080/departamentos
curl http://localhost:8080/departamentos/1
curl -X PUT http://localhost:8080/departamentos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "localizacao": "Localizacao exemplo" }'
curl -X DELETE http://localhost:8080/departamentos/1

# Funcionario
curl -X POST http://localhost:8080/funcionarios -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cargo": "Cargo exemplo", "salario": 99.90, "dataAdmissao": "2026-03-15", "ativo": true, "departamento": { "id": 1 } }'
curl http://localhost:8080/funcionarios
curl http://localhost:8080/funcionarios/1
curl -X PUT http://localhost:8080/funcionarios/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cargo": "Cargo exemplo", "salario": 99.90, "dataAdmissao": "2026-03-15", "ativo": true, "departamento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/funcionarios/1
```

## Estrutura

```
src/main/java/com/exemplo/recursoshumanos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
