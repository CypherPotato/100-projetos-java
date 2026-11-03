# 121 · Ouvidoria

**Categoria:** Gestão
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Recebimento de manifestações de clientes encaminhadas aos departamentos.

## Entidades

### Departamento (tabela `departamentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `responsavel` | `String` |

### Manifestacao (tabela `manifestacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `assunto` | `String` |
| `mensagem` | `String` |
| `dataRegistro` | `LocalDateTime` |
| `resolvida` | `Boolean` |
| `departamento` | `Departamento` (ManyToOne, coluna `departamento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/departamentos` | Lista todos |
| GET | `/departamentos/{id}` | Busca por id (404 se não existir) |
| POST | `/departamentos` | Cria (201) |
| PUT | `/departamentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/departamentos/{id}` | Exclui (204) |
| GET | `/manifestacoes` | Lista todos |
| GET | `/manifestacoes?departamentoId=1` | Lista filtrando por Departamento |
| GET | `/manifestacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/manifestacoes` | Cria (201) |
| PUT | `/manifestacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/manifestacoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\ouvidoria.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/departamentos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/ouvidoria`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Departamento → Manifestacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/departamentos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
```

```bash
# Departamento
curl -X POST http://localhost:8080/departamentos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
curl http://localhost:8080/departamentos
curl http://localhost:8080/departamentos/1
curl -X PUT http://localhost:8080/departamentos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "responsavel": "Responsavel exemplo" }'
curl -X DELETE http://localhost:8080/departamentos/1

# Manifestacao
curl -X POST http://localhost:8080/manifestacoes -H "Content-Type: application/json" -d '{ "assunto": "Assunto exemplo", "mensagem": "Mensagem exemplo", "dataRegistro": "2026-03-15T10:30:00", "resolvida": true, "departamento": { "id": 1 } }'
curl http://localhost:8080/manifestacoes
curl http://localhost:8080/manifestacoes/1
curl -X PUT http://localhost:8080/manifestacoes/1 -H "Content-Type: application/json" -d '{ "assunto": "Assunto exemplo", "mensagem": "Mensagem exemplo", "dataRegistro": "2026-03-15T10:30:00", "resolvida": true, "departamento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/manifestacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/ouvidoria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
