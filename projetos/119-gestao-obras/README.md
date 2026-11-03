# 119 · Gestão de Obras

**Categoria:** Gestão
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Acompanhamento de obras, etapas e materiais utilizados.

## Entidades

### Obra (tabela `obras`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `endereco` | `String` |
| `dataInicio` | `LocalDate` |
| `orcamento` | `BigDecimal` |

### Etapa (tabela `etapas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `concluida` | `Boolean` |
| `prazo` | `LocalDate` |
| `obra` | `Obra` (ManyToOne, coluna `obra_id`) |

### Material (tabela `materiais`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `quantidade` | `Integer` |
| `custoUnitario` | `BigDecimal` |
| `obra` | `Obra` (ManyToOne, coluna `obra_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/obras` | Lista todos |
| GET | `/obras/{id}` | Busca por id (404 se não existir) |
| POST | `/obras` | Cria (201) |
| PUT | `/obras/{id}` | Atualiza (404 se não existir) |
| DELETE | `/obras/{id}` | Exclui (204) |
| GET | `/etapas` | Lista todos |
| GET | `/etapas?obraId=1` | Lista filtrando por Obra |
| GET | `/etapas/{id}` | Busca por id (404 se não existir) |
| POST | `/etapas` | Cria (201) |
| PUT | `/etapas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/etapas/{id}` | Exclui (204) |
| GET | `/materiais` | Lista todos |
| GET | `/materiais?obraId=1` | Lista filtrando por Obra |
| GET | `/materiais/{id}` | Busca por id (404 se não existir) |
| POST | `/materiais` | Cria (201) |
| PUT | `/materiais/{id}` | Atualiza (404 se não existir) |
| DELETE | `/materiais/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\gestao-obras.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/obras
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/gestao-obras`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Obra → Etapa → Material.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/obras -ContentType "application/json" -Body '{ "nome": "Maria Silva", "endereco": "Rua das Flores, 100", "dataInicio": "2026-03-15", "orcamento": 99.90 }'
```

```bash
# Obra
curl -X POST http://localhost:8080/obras -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "endereco": "Rua das Flores, 100", "dataInicio": "2026-03-15", "orcamento": 99.90 }'
curl http://localhost:8080/obras
curl http://localhost:8080/obras/1
curl -X PUT http://localhost:8080/obras/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "endereco": "Rua das Flores, 100", "dataInicio": "2026-03-15", "orcamento": 99.90 }'
curl -X DELETE http://localhost:8080/obras/1

# Etapa
curl -X POST http://localhost:8080/etapas -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "concluida": true, "prazo": "2026-03-15", "obra": { "id": 1 } }'
curl http://localhost:8080/etapas
curl http://localhost:8080/etapas/1
curl -X PUT http://localhost:8080/etapas/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "concluida": true, "prazo": "2026-03-15", "obra": { "id": 1 } }'
curl -X DELETE http://localhost:8080/etapas/1

# Material
curl -X POST http://localhost:8080/materiais -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "quantidade": 10, "custoUnitario": 99.90, "obra": { "id": 1 } }'
curl http://localhost:8080/materiais
curl http://localhost:8080/materiais/1
curl -X PUT http://localhost:8080/materiais/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "quantidade": 10, "custoUnitario": 99.90, "obra": { "id": 1 } }'
curl -X DELETE http://localhost:8080/materiais/1
```

## Estrutura

```
src/main/java/com/exemplo/gestaoobras/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
