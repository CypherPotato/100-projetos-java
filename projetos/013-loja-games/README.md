# 013 · Loja de Games

**Categoria:** Comércio
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** não

Catálogo de plataformas e jogos novos e usados.

## Entidades

### Plataforma (tabela `plataformas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `fabricante` | `String` |

### Jogo (tabela `jogos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `titulo` | `String` |
| `preco` | `BigDecimal` |
| `lancamento` | `LocalDate` |
| `usado` | `Boolean` |
| `plataforma` | `Plataforma` (ManyToOne, coluna `plataforma_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/plataformas` | Lista todos |
| GET | `/plataformas/{id}` | Busca por id (404 se não existir) |
| POST | `/plataformas` | Cria (201) |
| PUT | `/plataformas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/plataformas/{id}` | Exclui (204) |
| GET | `/jogos` | Lista todos |
| GET | `/jogos?plataformaId=1` | Lista filtrando por Plataforma |
| GET | `/jogos/{id}` | Busca por id (404 se não existir) |
| POST | `/jogos` | Cria (201) |
| PUT | `/jogos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/jogos/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/plataformas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Plataforma → Jogo.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/plataformas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo" }'
```

```bash
# Plataforma
curl -X POST http://localhost:8080/plataformas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo" }'
curl http://localhost:8080/plataformas
curl http://localhost:8080/plataformas/1
curl -X PUT http://localhost:8080/plataformas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo" }'
curl -X DELETE http://localhost:8080/plataformas/1

# Jogo
curl -X POST http://localhost:8080/jogos -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "preco": 99.90, "lancamento": "2026-03-15", "usado": true, "plataforma": { "id": 1 } }'
curl http://localhost:8080/jogos
curl http://localhost:8080/jogos/1
curl -X PUT http://localhost:8080/jogos/1 -H "Content-Type: application/json" -d '{ "titulo": "Exemplo", "preco": 99.90, "lancamento": "2026-03-15", "usado": true, "plataforma": { "id": 1 } }'
curl -X DELETE http://localhost:8080/jogos/1
```

## Estrutura

```
src/main/java/com/exemplo/lojagames/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
```
