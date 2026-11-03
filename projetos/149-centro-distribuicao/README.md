# 149 · Centro de Distribuição

**Categoria:** Agro e Logística
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Controle de armazéns, mercadorias estocadas e expedições.

## Entidades

### Armazem (tabela `armazens`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cidade` | `String` |

### Mercadoria (tabela `mercadorias`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `pesoKg` | `Double` |
| `valorDeclarado` | `BigDecimal` |
| `armazem` | `Armazem` (ManyToOne, coluna `armazem_id`) |

### Expedicao (tabela `expedicoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataSaida` | `LocalDate` |
| `destino` | `String` |
| `entregue` | `Boolean` |
| `mercadoria` | `Mercadoria` (ManyToOne, coluna `mercadoria_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/armazens` | Lista todos |
| GET | `/armazens/{id}` | Busca por id (404 se não existir) |
| POST | `/armazens` | Cria (201) |
| PUT | `/armazens/{id}` | Atualiza (404 se não existir) |
| DELETE | `/armazens/{id}` | Exclui (204) |
| GET | `/mercadorias` | Lista todos |
| GET | `/mercadorias?armazemId=1` | Lista filtrando por Armazem |
| GET | `/mercadorias/{id}` | Busca por id (404 se não existir) |
| POST | `/mercadorias` | Cria (201) |
| PUT | `/mercadorias/{id}` | Atualiza (404 se não existir) |
| DELETE | `/mercadorias/{id}` | Exclui (204) |
| GET | `/expedicoes` | Lista todos |
| GET | `/expedicoes?mercadoriaId=1` | Lista filtrando por Mercadoria |
| GET | `/expedicoes/{id}` | Busca por id (404 se não existir) |
| POST | `/expedicoes` | Cria (201) |
| PUT | `/expedicoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/expedicoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\centro-distribuicao.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/armazens
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/centro-distribuicao`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Armazem → Mercadoria → Expedicao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/armazens -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cidade": "São Paulo" }'
```

```bash
# Armazem
curl -X POST http://localhost:8080/armazens -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo" }'
curl http://localhost:8080/armazens
curl http://localhost:8080/armazens/1
curl -X PUT http://localhost:8080/armazens/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cidade": "São Paulo" }'
curl -X DELETE http://localhost:8080/armazens/1

# Mercadoria
curl -X POST http://localhost:8080/mercadorias -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "pesoKg": 12.5, "valorDeclarado": 99.90, "armazem": { "id": 1 } }'
curl http://localhost:8080/mercadorias
curl http://localhost:8080/mercadorias/1
curl -X PUT http://localhost:8080/mercadorias/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "pesoKg": 12.5, "valorDeclarado": 99.90, "armazem": { "id": 1 } }'
curl -X DELETE http://localhost:8080/mercadorias/1

# Expedicao
curl -X POST http://localhost:8080/expedicoes -H "Content-Type: application/json" -d '{ "dataSaida": "2026-03-15", "destino": "Destino exemplo", "entregue": true, "mercadoria": { "id": 1 } }'
curl http://localhost:8080/expedicoes
curl http://localhost:8080/expedicoes/1
curl -X PUT http://localhost:8080/expedicoes/1 -H "Content-Type: application/json" -d '{ "dataSaida": "2026-03-15", "destino": "Destino exemplo", "entregue": true, "mercadoria": { "id": 1 } }'
curl -X DELETE http://localhost:8080/expedicoes/1
```

## Estrutura

```
src/main/java/com/exemplo/centrodistribuicao/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
