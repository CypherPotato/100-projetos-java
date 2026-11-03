# 112 · Banco de Sangue

**Categoria:** Saúde
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Registro de doadores e das doações de sangue realizadas.

## Entidades

### Doador (tabela `doadores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `tipoSanguineo` | `String` |
| `dataNascimento` | `LocalDate` |

### Doacao (tabela `doacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataDoacao` | `LocalDate` |
| `volumeMl` | `Integer` |
| `apto` | `Boolean` |
| `doador` | `Doador` (ManyToOne, coluna `doador_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/doadores` | Lista todos |
| GET | `/doadores/{id}` | Busca por id (404 se não existir) |
| POST | `/doadores` | Cria (201) |
| PUT | `/doadores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/doadores/{id}` | Exclui (204) |
| GET | `/doacoes` | Lista todos |
| GET | `/doacoes?doadorId=1` | Lista filtrando por Doador |
| GET | `/doacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/doacoes` | Cria (201) |
| PUT | `/doacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/doacoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\banco-sangue.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/doadores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/banco-sangue`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Doador → Doacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/doadores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "tipoSanguineo": "TipoSanguineo exemplo", "dataNascimento": "2026-03-15" }'
```

```bash
# Doador
curl -X POST http://localhost:8080/doadores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tipoSanguineo": "TipoSanguineo exemplo", "dataNascimento": "2026-03-15" }'
curl http://localhost:8080/doadores
curl http://localhost:8080/doadores/1
curl -X PUT http://localhost:8080/doadores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "tipoSanguineo": "TipoSanguineo exemplo", "dataNascimento": "2026-03-15" }'
curl -X DELETE http://localhost:8080/doadores/1

# Doacao
curl -X POST http://localhost:8080/doacoes -H "Content-Type: application/json" -d '{ "dataDoacao": "2026-03-15", "volumeMl": 10, "apto": true, "doador": { "id": 1 } }'
curl http://localhost:8080/doacoes
curl http://localhost:8080/doacoes/1
curl -X PUT http://localhost:8080/doacoes/1 -H "Content-Type: application/json" -d '{ "dataDoacao": "2026-03-15", "volumeMl": 10, "apto": true, "doador": { "id": 1 } }'
curl -X DELETE http://localhost:8080/doacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/bancosangue/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
