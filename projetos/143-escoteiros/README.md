# 143 · Grupo de Escoteiros

**Categoria:** Comunidade
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Controle de patrulhas e de seus escoteiros.

## Entidades

### Patrulha (tabela `patrulhas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `lema` | `String` |

### Escoteiro (tabela `escoteiros`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataNascimento` | `LocalDate` |
| `insignia` | `String` |
| `ativo` | `Boolean` |
| `patrulha` | `Patrulha` (ManyToOne, coluna `patrulha_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/patrulhas` | Lista todos |
| GET | `/patrulhas/{id}` | Busca por id (404 se não existir) |
| POST | `/patrulhas` | Cria (201) |
| PUT | `/patrulhas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/patrulhas/{id}` | Exclui (204) |
| GET | `/escoteiros` | Lista todos |
| GET | `/escoteiros?patrulhaId=1` | Lista filtrando por Patrulha |
| GET | `/escoteiros/{id}` | Busca por id (404 se não existir) |
| POST | `/escoteiros` | Cria (201) |
| PUT | `/escoteiros/{id}` | Atualiza (404 se não existir) |
| DELETE | `/escoteiros/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\escoteiros.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/patrulhas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/escoteiros`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Patrulha → Escoteiro.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/patrulhas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "lema": "Lema exemplo" }'
```

```bash
# Patrulha
curl -X POST http://localhost:8080/patrulhas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "lema": "Lema exemplo" }'
curl http://localhost:8080/patrulhas
curl http://localhost:8080/patrulhas/1
curl -X PUT http://localhost:8080/patrulhas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "lema": "Lema exemplo" }'
curl -X DELETE http://localhost:8080/patrulhas/1

# Escoteiro
curl -X POST http://localhost:8080/escoteiros -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "insignia": "Insignia exemplo", "ativo": true, "patrulha": { "id": 1 } }'
curl http://localhost:8080/escoteiros
curl http://localhost:8080/escoteiros/1
curl -X PUT http://localhost:8080/escoteiros/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataNascimento": "2026-03-15", "insignia": "Insignia exemplo", "ativo": true, "patrulha": { "id": 1 } }'
curl -X DELETE http://localhost:8080/escoteiros/1
```

## Estrutura

```
src/main/java/com/exemplo/escoteiros/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
