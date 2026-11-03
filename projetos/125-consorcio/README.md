# 125 · Consórcio

**Categoria:** Finanças
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Gestão de grupos de consórcio e das cotas dos participantes.

## Entidades

### Grupo (tabela `grupos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `prazoMeses` | `Integer` |
| `valorCredito` | `BigDecimal` |

### Cota (tabela `cotas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `participante` | `String` |
| `contemplada` | `Boolean` |
| `parcela` | `BigDecimal` |
| `grupo` | `Grupo` (ManyToOne, coluna `grupo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/grupos` | Lista todos |
| GET | `/grupos/{id}` | Busca por id (404 se não existir) |
| POST | `/grupos` | Cria (201) |
| PUT | `/grupos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/grupos/{id}` | Exclui (204) |
| GET | `/cotas` | Lista todos |
| GET | `/cotas?grupoId=1` | Lista filtrando por Grupo |
| GET | `/cotas/{id}` | Busca por id (404 se não existir) |
| POST | `/cotas` | Cria (201) |
| PUT | `/cotas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/cotas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\consorcio.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/grupos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/consorcio`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Grupo → Cota.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/grupos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "prazoMeses": 10, "valorCredito": 99.90 }'
```

```bash
# Grupo
curl -X POST http://localhost:8080/grupos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "prazoMeses": 10, "valorCredito": 99.90 }'
curl http://localhost:8080/grupos
curl http://localhost:8080/grupos/1
curl -X PUT http://localhost:8080/grupos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "prazoMeses": 10, "valorCredito": 99.90 }'
curl -X DELETE http://localhost:8080/grupos/1

# Cota
curl -X POST http://localhost:8080/cotas -H "Content-Type: application/json" -d '{ "participante": "Participante exemplo", "contemplada": true, "parcela": 99.90, "grupo": { "id": 1 } }'
curl http://localhost:8080/cotas
curl http://localhost:8080/cotas/1
curl -X PUT http://localhost:8080/cotas/1 -H "Content-Type: application/json" -d '{ "participante": "Participante exemplo", "contemplada": true, "parcela": 99.90, "grupo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/cotas/1
```

## Estrutura

```
src/main/java/com/exemplo/consorcio/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
