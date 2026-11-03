# 137 · Registro de Domínios

**Categoria:** Tecnologia
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** sim

Controle de registradores e dos domínios com data de expiração.

## Entidades

### Registrador (tabela `registradores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `site` | `String` |

### Dominio (tabela `dominios`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `dataExpiracao` | `LocalDate` |
| `renovacaoAutomatica` | `Boolean` |
| `custoAnual` | `BigDecimal` |
| `registrador` | `Registrador` (ManyToOne, coluna `registrador_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/registradores` | Lista todos |
| GET | `/registradores/{id}` | Busca por id (404 se não existir) |
| POST | `/registradores` | Cria (201) |
| PUT | `/registradores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/registradores/{id}` | Exclui (204) |
| GET | `/dominios` | Lista todos |
| GET | `/dominios?registradorId=1` | Lista filtrando por Registrador |
| GET | `/dominios/{id}` | Busca por id (404 se não existir) |
| POST | `/dominios` | Cria (201) |
| PUT | `/dominios/{id}` | Atualiza (404 se não existir) |
| DELETE | `/dominios/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\registro-dominios.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/registradores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/registro-dominios`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Registrador → Dominio.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/registradores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "site": "Site exemplo" }'
```

```bash
# Registrador
curl -X POST http://localhost:8080/registradores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "site": "Site exemplo" }'
curl http://localhost:8080/registradores
curl http://localhost:8080/registradores/1
curl -X PUT http://localhost:8080/registradores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "site": "Site exemplo" }'
curl -X DELETE http://localhost:8080/registradores/1

# Dominio
curl -X POST http://localhost:8080/dominios -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataExpiracao": "2026-03-15", "renovacaoAutomatica": true, "custoAnual": 99.90, "registrador": { "id": 1 } }'
curl http://localhost:8080/dominios
curl http://localhost:8080/dominios/1
curl -X PUT http://localhost:8080/dominios/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "dataExpiracao": "2026-03-15", "renovacaoAutomatica": true, "custoAnual": 99.90, "registrador": { "id": 1 } }'
curl -X DELETE http://localhost:8080/dominios/1
```

## Estrutura

```
src/main/java/com/exemplo/registrodominios/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
