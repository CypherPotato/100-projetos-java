# 102 · Loja de Eletrônicos

**Categoria:** Comércio
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Cadastro de marcas e aparelhos eletrônicos à venda.

## Entidades

### Marca (tabela `marcas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `paisOrigem` | `String` |

### Aparelho (tabela `aparelhos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `modelo` | `String` |
| `preco` | `BigDecimal` |
| `garantiaMeses` | `Integer` |
| `emEstoque` | `Boolean` |
| `marca` | `Marca` (ManyToOne, coluna `marca_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/marcas` | Lista todos |
| GET | `/marcas/{id}` | Busca por id (404 se não existir) |
| POST | `/marcas` | Cria (201) |
| PUT | `/marcas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/marcas/{id}` | Exclui (204) |
| GET | `/aparelhos` | Lista todos |
| GET | `/aparelhos?marcaId=1` | Lista filtrando por Marca |
| GET | `/aparelhos/{id}` | Busca por id (404 se não existir) |
| POST | `/aparelhos` | Cria (201) |
| PUT | `/aparelhos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aparelhos/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\loja-eletronicos.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/marcas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/loja-eletronicos`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Marca → Aparelho.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/marcas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "paisOrigem": "PaisOrigem exemplo" }'
```

```bash
# Marca
curl -X POST http://localhost:8080/marcas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "paisOrigem": "PaisOrigem exemplo" }'
curl http://localhost:8080/marcas
curl http://localhost:8080/marcas/1
curl -X PUT http://localhost:8080/marcas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "paisOrigem": "PaisOrigem exemplo" }'
curl -X DELETE http://localhost:8080/marcas/1

# Aparelho
curl -X POST http://localhost:8080/aparelhos -H "Content-Type: application/json" -d '{ "modelo": "Modelo Y", "preco": 99.90, "garantiaMeses": 10, "emEstoque": true, "marca": { "id": 1 } }'
curl http://localhost:8080/aparelhos
curl http://localhost:8080/aparelhos/1
curl -X PUT http://localhost:8080/aparelhos/1 -H "Content-Type: application/json" -d '{ "modelo": "Modelo Y", "preco": 99.90, "garantiaMeses": 10, "emEstoque": true, "marca": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aparelhos/1
```

## Estrutura

```
src/main/java/com/exemplo/lojaeletronicos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
