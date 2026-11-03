# 122 · Protocolo de Documentos

**Categoria:** Gestão
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Protocolo e tramitação de documentos entre os setores.

## Entidades

### Setor (tabela `setores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `ramal` | `String` |

### Documento (tabela `documentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `numeroProtocolo` | `String` |
| `assunto` | `String` |
| `dataEntrada` | `LocalDate` |
| `setor` | `Setor` (ManyToOne, coluna `setor_id`) |

### Tramitacao (tabela `tramitacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataHora` | `LocalDateTime` |
| `despacho` | `String` |
| `urgente` | `Boolean` |
| `documento` | `Documento` (ManyToOne, coluna `documento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/setores` | Lista todos |
| GET | `/setores/{id}` | Busca por id (404 se não existir) |
| POST | `/setores` | Cria (201) |
| PUT | `/setores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/setores/{id}` | Exclui (204) |
| GET | `/documentos` | Lista todos |
| GET | `/documentos?setorId=1` | Lista filtrando por Setor |
| GET | `/documentos/{id}` | Busca por id (404 se não existir) |
| POST | `/documentos` | Cria (201) |
| PUT | `/documentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/documentos/{id}` | Exclui (204) |
| GET | `/tramitacoes` | Lista todos |
| GET | `/tramitacoes?documentoId=1` | Lista filtrando por Documento |
| GET | `/tramitacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/tramitacoes` | Cria (201) |
| PUT | `/tramitacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/tramitacoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\protocolo-documentos.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/setores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/protocolo-documentos`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Setor → Documento → Tramitacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/setores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "ramal": "Ramal exemplo" }'
```

```bash
# Setor
curl -X POST http://localhost:8080/setores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "ramal": "Ramal exemplo" }'
curl http://localhost:8080/setores
curl http://localhost:8080/setores/1
curl -X PUT http://localhost:8080/setores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "ramal": "Ramal exemplo" }'
curl -X DELETE http://localhost:8080/setores/1

# Documento
curl -X POST http://localhost:8080/documentos -H "Content-Type: application/json" -d '{ "numeroProtocolo": "NumeroProtocolo exemplo", "assunto": "Assunto exemplo", "dataEntrada": "2026-03-15", "setor": { "id": 1 } }'
curl http://localhost:8080/documentos
curl http://localhost:8080/documentos/1
curl -X PUT http://localhost:8080/documentos/1 -H "Content-Type: application/json" -d '{ "numeroProtocolo": "NumeroProtocolo exemplo", "assunto": "Assunto exemplo", "dataEntrada": "2026-03-15", "setor": { "id": 1 } }'
curl -X DELETE http://localhost:8080/documentos/1

# Tramitacao
curl -X POST http://localhost:8080/tramitacoes -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "despacho": "Despacho exemplo", "urgente": true, "documento": { "id": 1 } }'
curl http://localhost:8080/tramitacoes
curl http://localhost:8080/tramitacoes/1
curl -X PUT http://localhost:8080/tramitacoes/1 -H "Content-Type: application/json" -d '{ "dataHora": "2026-03-15T10:30:00", "despacho": "Despacho exemplo", "urgente": true, "documento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/tramitacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/protocolodocumentos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
