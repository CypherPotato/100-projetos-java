# 005 · Farmácia

**Categoria:** Saúde
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Controle de fabricantes e medicamentos de uma farmácia.

## Entidades

### Fabricante (tabela `fabricantes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cnpj` | `String` |

### Medicamento (tabela `medicamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `preco` | `BigDecimal` |
| `estoque` | `Integer` |
| `controlado` | `Boolean` |
| `fabricante` | `Fabricante` (ManyToOne, coluna `fabricante_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/fabricantes` | Lista todos |
| GET | `/fabricantes/{id}` | Busca por id (404 se não existir) |
| POST | `/fabricantes` | Cria (201) |
| PUT | `/fabricantes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fabricantes/{id}` | Exclui (204) |
| GET | `/medicamentos` | Lista todos |
| GET | `/medicamentos?fabricanteId=1` | Lista filtrando por Fabricante |
| GET | `/medicamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/medicamentos` | Cria (201) |
| PUT | `/medicamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/medicamentos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/fabricantes

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Fabricante → Medicamento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/fabricantes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cnpj": "12.345.678/0001-00" }'
```

```bash
# Fabricante
curl -X POST http://localhost:8080/fabricantes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cnpj": "12.345.678/0001-00" }'
curl http://localhost:8080/fabricantes
curl http://localhost:8080/fabricantes/1
curl -X PUT http://localhost:8080/fabricantes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cnpj": "12.345.678/0001-00" }'
curl -X DELETE http://localhost:8080/fabricantes/1

# Medicamento
curl -X POST http://localhost:8080/medicamentos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "estoque": 10, "controlado": true, "fabricante": { "id": 1 } }'
curl http://localhost:8080/medicamentos
curl http://localhost:8080/medicamentos/1
curl -X PUT http://localhost:8080/medicamentos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "preco": 99.90, "estoque": 10, "controlado": true, "fabricante": { "id": 1 } }'
curl -X DELETE http://localhost:8080/medicamentos/1
```

## Estrutura

```
src/main/java/com/exemplo/farmacia/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
