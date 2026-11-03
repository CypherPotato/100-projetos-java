# 120 · Controle de Visitantes

**Categoria:** Gestão
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Registro de entrada e saída de visitantes por anfitrião.

## Entidades

### Anfitriao (tabela `anfitrioes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `setor` | `String` |

### Visita (tabela `visitas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `visitante` | `String` |
| `documento` | `String` |
| `entrada` | `LocalDateTime` |
| `saida` | `LocalDateTime` |
| `anfitriao` | `Anfitriao` (ManyToOne, coluna `anfitriao_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/anfitrioes` | Lista todos |
| GET | `/anfitrioes/{id}` | Busca por id (404 se não existir) |
| POST | `/anfitrioes` | Cria (201) |
| PUT | `/anfitrioes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/anfitrioes/{id}` | Exclui (204) |
| GET | `/visitas` | Lista todos |
| GET | `/visitas?anfitriaoId=1` | Lista filtrando por Anfitriao |
| GET | `/visitas/{id}` | Busca por id (404 se não existir) |
| POST | `/visitas` | Cria (201) |
| PUT | `/visitas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/visitas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\controle-visitantes.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/anfitrioes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/controle-visitantes`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Anfitriao → Visita.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/anfitrioes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "setor": "Setor exemplo" }'
```

```bash
# Anfitriao
curl -X POST http://localhost:8080/anfitrioes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "setor": "Setor exemplo" }'
curl http://localhost:8080/anfitrioes
curl http://localhost:8080/anfitrioes/1
curl -X PUT http://localhost:8080/anfitrioes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "setor": "Setor exemplo" }'
curl -X DELETE http://localhost:8080/anfitrioes/1

# Visita
curl -X POST http://localhost:8080/visitas -H "Content-Type: application/json" -d '{ "visitante": "Visitante exemplo", "documento": "Documento exemplo", "entrada": "2026-03-15T10:30:00", "saida": "2026-03-15T10:30:00", "anfitriao": { "id": 1 } }'
curl http://localhost:8080/visitas
curl http://localhost:8080/visitas/1
curl -X PUT http://localhost:8080/visitas/1 -H "Content-Type: application/json" -d '{ "visitante": "Visitante exemplo", "documento": "Documento exemplo", "entrada": "2026-03-15T10:30:00", "saida": "2026-03-15T10:30:00", "anfitriao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/visitas/1
```

## Estrutura

```
src/main/java/com/exemplo/controlevisitantes/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
