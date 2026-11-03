# 133 · Sorveteria

**Categoria:** Alimentação
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Controle de sabores, sorvetes e vendas de uma sorveteria.

## Entidades

### Sabor (tabela `sabores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `base` | `String` |
| `vegano` | `Boolean` |

### Sorvete (tabela `sorvetes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `tamanho` | `String` |
| `preco` | `BigDecimal` |
| `cobertura` | `String` |
| `sabor` | `Sabor` (ManyToOne, coluna `sabor_id`) |

### Venda (tabela `vendas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `quantidade` | `Integer` |
| `formaPagamento` | `String` |
| `sorvete` | `Sorvete` (ManyToOne, coluna `sorvete_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/sabores` | Lista todos |
| GET | `/sabores/{id}` | Busca por id (404 se não existir) |
| POST | `/sabores` | Cria (201) |
| PUT | `/sabores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/sabores/{id}` | Exclui (204) |
| GET | `/sorvetes` | Lista todos |
| GET | `/sorvetes?saborId=1` | Lista filtrando por Sabor |
| GET | `/sorvetes/{id}` | Busca por id (404 se não existir) |
| POST | `/sorvetes` | Cria (201) |
| PUT | `/sorvetes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/sorvetes/{id}` | Exclui (204) |
| GET | `/vendas` | Lista todos |
| GET | `/vendas?sorveteId=1` | Lista filtrando por Sorvete |
| GET | `/vendas/{id}` | Busca por id (404 se não existir) |
| POST | `/vendas` | Cria (201) |
| PUT | `/vendas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vendas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\sorveteria.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/sabores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/sorveteria`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Sabor → Sorvete → Venda.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/sabores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "base": "Base exemplo", "vegano": true }'
```

```bash
# Sabor
curl -X POST http://localhost:8080/sabores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "base": "Base exemplo", "vegano": true }'
curl http://localhost:8080/sabores
curl http://localhost:8080/sabores/1
curl -X PUT http://localhost:8080/sabores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "base": "Base exemplo", "vegano": true }'
curl -X DELETE http://localhost:8080/sabores/1

# Sorvete
curl -X POST http://localhost:8080/sorvetes -H "Content-Type: application/json" -d '{ "tamanho": "Tamanho exemplo", "preco": 99.90, "cobertura": "Cobertura exemplo", "sabor": { "id": 1 } }'
curl http://localhost:8080/sorvetes
curl http://localhost:8080/sorvetes/1
curl -X PUT http://localhost:8080/sorvetes/1 -H "Content-Type: application/json" -d '{ "tamanho": "Tamanho exemplo", "preco": 99.90, "cobertura": "Cobertura exemplo", "sabor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/sorvetes/1

# Venda
curl -X POST http://localhost:8080/vendas -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "quantidade": 10, "formaPagamento": "FormaPagamento exemplo", "sorvete": { "id": 1 } }'
curl http://localhost:8080/vendas
curl http://localhost:8080/vendas/1
curl -X PUT http://localhost:8080/vendas/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "quantidade": 10, "formaPagamento": "FormaPagamento exemplo", "sorvete": { "id": 1 } }'
curl -X DELETE http://localhost:8080/vendas/1
```

## Estrutura

```
src/main/java/com/exemplo/sorveteria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
