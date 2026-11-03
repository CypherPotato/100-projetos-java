# 135 · Marmitaria

**Categoria:** Alimentação
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Cardápio de marmitas e encomendas feitas pelos clientes.

## Entidades

### Marmita (tabela `marmitas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `tamanho` | `String` |
| `preco` | `BigDecimal` |

### Encomenda (tabela `encomendas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `cliente` | `String` |
| `quantidade` | `Integer` |
| `entrega` | `LocalDateTime` |
| `paga` | `Boolean` |
| `marmita` | `Marmita` (ManyToOne, coluna `marmita_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/marmitas` | Lista todos |
| GET | `/marmitas/{id}` | Busca por id (404 se não existir) |
| POST | `/marmitas` | Cria (201) |
| PUT | `/marmitas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/marmitas/{id}` | Exclui (204) |
| GET | `/encomendas` | Lista todos |
| GET | `/encomendas?marmitaId=1` | Lista filtrando por Marmita |
| GET | `/encomendas/{id}` | Busca por id (404 se não existir) |
| POST | `/encomendas` | Cria (201) |
| PUT | `/encomendas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/encomendas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\marmitaria.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/marmitas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/marmitaria`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Marmita → Encomenda.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/marmitas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "tamanho": "Tamanho exemplo", "preco": 99.90 }'
```

```bash
# Marmita
curl -X POST http://localhost:8080/marmitas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tamanho": "Tamanho exemplo", "preco": 99.90 }'
curl http://localhost:8080/marmitas
curl http://localhost:8080/marmitas/1
curl -X PUT http://localhost:8080/marmitas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tamanho": "Tamanho exemplo", "preco": 99.90 }'
curl -X DELETE http://localhost:8080/marmitas/1

# Encomenda
curl -X POST http://localhost:8080/encomendas -H "Content-Type: application/json" -d '{ "cliente": "Cliente exemplo", "quantidade": 10, "entrega": "2026-03-15T10:30:00", "paga": true, "marmita": { "id": 1 } }'
curl http://localhost:8080/encomendas
curl http://localhost:8080/encomendas/1
curl -X PUT http://localhost:8080/encomendas/1 -H "Content-Type: application/json" -d '{ "cliente": "Cliente exemplo", "quantidade": 10, "entrega": "2026-03-15T10:30:00", "paga": true, "marmita": { "id": 1 } }'
curl -X DELETE http://localhost:8080/encomendas/1
```

## Estrutura

```
src/main/java/com/exemplo/marmitaria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
