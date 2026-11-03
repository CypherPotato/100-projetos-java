# 145 · Coral Comunitário

**Categoria:** Comunidade
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Cadastro de naipes e coristas de um coral comunitário.

## Entidades

### Naipe (tabela `naipes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `tessitura` | `String` |

### Corista (tabela `coristas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |
| `dataEntrada` | `LocalDate` |
| `ativo` | `Boolean` |
| `naipe` | `Naipe` (ManyToOne, coluna `naipe_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/naipes` | Lista todos |
| GET | `/naipes/{id}` | Busca por id (404 se não existir) |
| POST | `/naipes` | Cria (201) |
| PUT | `/naipes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/naipes/{id}` | Exclui (204) |
| GET | `/coristas` | Lista todos |
| GET | `/coristas?naipeId=1` | Lista filtrando por Naipe |
| GET | `/coristas/{id}` | Busca por id (404 se não existir) |
| POST | `/coristas` | Cria (201) |
| PUT | `/coristas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/coristas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\coral-comunitario.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/naipes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/coral-comunitario`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Naipe → Corista.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/naipes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "tessitura": "Tessitura exemplo" }'
```

```bash
# Naipe
curl -X POST http://localhost:8080/naipes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tessitura": "Tessitura exemplo" }'
curl http://localhost:8080/naipes
curl http://localhost:8080/naipes/1
curl -X PUT http://localhost:8080/naipes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tessitura": "Tessitura exemplo" }'
curl -X DELETE http://localhost:8080/naipes/1

# Corista
curl -X POST http://localhost:8080/coristas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataEntrada": "2026-03-15", "ativo": true, "naipe": { "id": 1 } }'
curl http://localhost:8080/coristas
curl http://localhost:8080/coristas/1
curl -X PUT http://localhost:8080/coristas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000", "dataEntrada": "2026-03-15", "ativo": true, "naipe": { "id": 1 } }'
curl -X DELETE http://localhost:8080/coristas/1
```

## Estrutura

```
src/main/java/com/exemplo/coralcomunitario/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
