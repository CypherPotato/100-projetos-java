# 113 · Clínica de Estética

**Categoria:** Saúde
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Cadastro de procedimentos estéticos e dos atendimentos realizados.

## Entidades

### Procedimento (tabela `procedimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `duracaoMin` | `Integer` |
| `preco` | `BigDecimal` |

### Atendimento (tabela `atendimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `cliente` | `String` |
| `dataHora` | `LocalDateTime` |
| `observacao` | `String` |
| `pago` | `Boolean` |
| `procedimento` | `Procedimento` (ManyToOne, coluna `procedimento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/procedimentos` | Lista todos |
| GET | `/procedimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/procedimentos` | Cria (201) |
| PUT | `/procedimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/procedimentos/{id}` | Exclui (204) |
| GET | `/atendimentos` | Lista todos |
| GET | `/atendimentos?procedimentoId=1` | Lista filtrando por Procedimento |
| GET | `/atendimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/atendimentos` | Cria (201) |
| PUT | `/atendimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/atendimentos/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\clinica-estetica.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/procedimentos
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/clinica-estetica`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Procedimento → Atendimento.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/procedimentos -ContentType "application/json" -Body '{ "nome": "Maria Silva", "duracaoMin": 10, "preco": 99.90 }'
```

```bash
# Procedimento
curl -X POST http://localhost:8080/procedimentos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "duracaoMin": 10, "preco": 99.90 }'
curl http://localhost:8080/procedimentos
curl http://localhost:8080/procedimentos/1
curl -X PUT http://localhost:8080/procedimentos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "duracaoMin": 10, "preco": 99.90 }'
curl -X DELETE http://localhost:8080/procedimentos/1

# Atendimento
curl -X POST http://localhost:8080/atendimentos -H "Content-Type: application/json" -d '{ "cliente": "Cliente exemplo", "dataHora": "2026-03-15T10:30:00", "observacao": "Sem observações", "pago": true, "procedimento": { "id": 1 } }'
curl http://localhost:8080/atendimentos
curl http://localhost:8080/atendimentos/1
curl -X PUT http://localhost:8080/atendimentos/1 -H "Content-Type: application/json" -d '{ "cliente": "Cliente exemplo", "dataHora": "2026-03-15T10:30:00", "observacao": "Sem observações", "pago": true, "procedimento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/atendimentos/1
```

## Estrutura

```
src/main/java/com/exemplo/clinicaestetica/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
