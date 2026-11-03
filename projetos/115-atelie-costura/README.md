# 115 · Ateliê de Costura

**Categoria:** Serviços
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Controle de clientes e encomendas de peças sob medida.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Encomenda (tabela `encomendas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `peca` | `String` |
| `prazoEntrega` | `LocalDate` |
| `valor` | `BigDecimal` |
| `entregue` | `Boolean` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/encomendas` | Lista todos |
| GET | `/encomendas?clienteId=1` | Lista filtrando por Cliente |
| GET | `/encomendas/{id}` | Busca por id (404 se não existir) |
| POST | `/encomendas` | Cria (201) |
| PUT | `/encomendas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/encomendas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\atelie-costura.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/atelie-costura`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Encomenda.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/clientes/1

# Encomenda
curl -X POST http://localhost:8080/encomendas -H "Content-Type: application/json" -d '{ "peca": "Peca exemplo", "prazoEntrega": "2026-03-15", "valor": 99.90, "entregue": true, "cliente": { "id": 1 } }'
curl http://localhost:8080/encomendas
curl http://localhost:8080/encomendas/1
curl -X PUT http://localhost:8080/encomendas/1 -H "Content-Type: application/json" -d '{ "peca": "Peca exemplo", "prazoEntrega": "2026-03-15", "valor": 99.90, "entregue": true, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/encomendas/1
```

## Estrutura

```
src/main/java/com/exemplo/ateliecostura/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
