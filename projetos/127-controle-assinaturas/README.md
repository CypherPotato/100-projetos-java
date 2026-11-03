# 127 · Controle de Assinaturas

**Categoria:** Finanças
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Acompanhamento de assinaturas recorrentes e seus pagamentos.

## Entidades

### Categoria (tabela `categorias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Assinatura (tabela `assinaturas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `mensalidade` | `BigDecimal` |
| `proximaCobranca` | `LocalDate` |
| `ativa` | `Boolean` |
| `categoria` | `Categoria` (ManyToOne, coluna `categoria_id`) |

### Pagamento (tabela `pagamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataPagamento` | `LocalDate` |
| `valorPago` | `BigDecimal` |
| `assinatura` | `Assinatura` (ManyToOne, coluna `assinatura_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/categorias` | Lista todos |
| GET | `/categorias/{id}` | Busca por id (404 se não existir) |
| POST | `/categorias` | Cria (201) |
| PUT | `/categorias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/categorias/{id}` | Exclui (204) |
| GET | `/assinaturas` | Lista todos |
| GET | `/assinaturas?categoriaId=1` | Lista filtrando por Categoria |
| GET | `/assinaturas/{id}` | Busca por id (404 se não existir) |
| POST | `/assinaturas` | Cria (201) |
| PUT | `/assinaturas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/assinaturas/{id}` | Exclui (204) |
| GET | `/pagamentos` | Lista todos |
| GET | `/pagamentos?assinaturaId=1` | Lista filtrando por Assinatura |
| GET | `/pagamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/pagamentos` | Cria (201) |
| PUT | `/pagamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/pagamentos/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\controle-assinaturas.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/categorias
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/controle-assinaturas`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Categoria → Assinatura → Pagamento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/categorias -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# Categoria
curl -X POST http://localhost:8080/categorias -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/categorias
curl http://localhost:8080/categorias/1
curl -X PUT http://localhost:8080/categorias/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/categorias/1

# Assinatura
curl -X POST http://localhost:8080/assinaturas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "mensalidade": 99.90, "proximaCobranca": "2026-03-15", "ativa": true, "categoria": { "id": 1 } }'
curl http://localhost:8080/assinaturas
curl http://localhost:8080/assinaturas/1
curl -X PUT http://localhost:8080/assinaturas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "mensalidade": 99.90, "proximaCobranca": "2026-03-15", "ativa": true, "categoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/assinaturas/1

# Pagamento
curl -X POST http://localhost:8080/pagamentos -H "Content-Type: application/json" -d '{ "dataPagamento": "2026-03-15", "valorPago": 99.90, "assinatura": { "id": 1 } }'
curl http://localhost:8080/pagamentos
curl http://localhost:8080/pagamentos/1
curl -X PUT http://localhost:8080/pagamentos/1 -H "Content-Type: application/json" -d '{ "dataPagamento": "2026-03-15", "valorPago": 99.90, "assinatura": { "id": 1 } }'
curl -X DELETE http://localhost:8080/pagamentos/1
```

## Estrutura

```
src/main/java/com/exemplo/controleassinaturas/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
