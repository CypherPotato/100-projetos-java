# 012 · Ótica

**Categoria:** Comércio
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** sim

Cadastro de marcas e armações de óculos disponíveis na loja.

## Entidades

### Marca (tabela `marcas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `pais` | `String` |

### Armacao (tabela `armacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `modelo` | `String` |
| `cor` | `String` |
| `preco` | `BigDecimal` |
| `estoque` | `Integer` |
| `marca` | `Marca` (ManyToOne, coluna `marca_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/marcas` | Lista todos |
| GET | `/marcas/{id}` | Busca por id (404 se não existir) |
| POST | `/marcas` | Cria (201) |
| PUT | `/marcas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/marcas/{id}` | Exclui (204) |
| GET | `/armacoes` | Lista todos |
| GET | `/armacoes?marcaId=1` | Lista filtrando por Marca |
| GET | `/armacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/armacoes` | Cria (201) |
| PUT | `/armacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/armacoes/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/marcas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Marca → Armacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/marcas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "pais": "Pais exemplo" }'
```

```bash
# Marca
curl -X POST http://localhost:8080/marcas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pais": "Pais exemplo" }'
curl http://localhost:8080/marcas
curl http://localhost:8080/marcas/1
curl -X PUT http://localhost:8080/marcas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pais": "Pais exemplo" }'
curl -X DELETE http://localhost:8080/marcas/1

# Armacao
curl -X POST http://localhost:8080/armacoes -H "Content-Type: application/json" -d '{ "modelo": "Modelo Y", "cor": "Cor exemplo", "preco": 99.90, "estoque": 10, "marca": { "id": 1 } }'
curl http://localhost:8080/armacoes
curl http://localhost:8080/armacoes/1
curl -X PUT http://localhost:8080/armacoes/1 -H "Content-Type: application/json" -d '{ "modelo": "Modelo Y", "cor": "Cor exemplo", "preco": 99.90, "estoque": 10, "marca": { "id": 1 } }'
curl -X DELETE http://localhost:8080/armacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/otica/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
