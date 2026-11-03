# 103 · Loja de Móveis

**Categoria:** Comércio
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Catálogo de ambientes e móveis de uma loja.

## Entidades

### Ambiente (tabela `ambientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `descricao` | `String` |

### Movel (tabela `moveis`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cor` | `String` |
| `preco` | `BigDecimal` |
| `largura` | `Double` |
| `ambiente` | `Ambiente` (ManyToOne, coluna `ambiente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/ambientes` | Lista todos |
| GET | `/ambientes/{id}` | Busca por id (404 se não existir) |
| POST | `/ambientes` | Cria (201) |
| PUT | `/ambientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/ambientes/{id}` | Exclui (204) |
| GET | `/moveis` | Lista todos |
| GET | `/moveis?ambienteId=1` | Lista filtrando por Ambiente |
| GET | `/moveis/{id}` | Busca por id (404 se não existir) |
| POST | `/moveis` | Cria (201) |
| PUT | `/moveis/{id}` | Atualiza (404 se não existir) |
| DELETE | `/moveis/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\loja-moveis.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/ambientes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/loja-moveis`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Ambiente → Movel.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/ambientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
```

```bash
# Ambiente
curl -X POST http://localhost:8080/ambientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl http://localhost:8080/ambientes
curl http://localhost:8080/ambientes/1
curl -X PUT http://localhost:8080/ambientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "descricao": "Descrição de exemplo" }'
curl -X DELETE http://localhost:8080/ambientes/1

# Movel
curl -X POST http://localhost:8080/moveis -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cor": "Cor exemplo", "preco": 99.90, "largura": 12.5, "ambiente": { "id": 1 } }'
curl http://localhost:8080/moveis
curl http://localhost:8080/moveis/1
curl -X PUT http://localhost:8080/moveis/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cor": "Cor exemplo", "preco": 99.90, "largura": 12.5, "ambiente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/moveis/1
```

## Estrutura

```
src/main/java/com/exemplo/lojamoveis/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
