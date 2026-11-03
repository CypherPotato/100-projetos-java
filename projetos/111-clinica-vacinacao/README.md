# 111 · Clínica de Vacinação

**Categoria:** Saúde
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Controle de vacinas disponíveis e das aplicações realizadas.

## Entidades

### Vacina (tabela `vacinas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `fabricante` | `String` |
| `doses` | `Integer` |

### Aplicacao (tabela `aplicacoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `paciente` | `String` |
| `dataAplicacao` | `LocalDate` |
| `lote` | `String` |
| `dose` | `Integer` |
| `vacina` | `Vacina` (ManyToOne, coluna `vacina_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/vacinas` | Lista todos |
| GET | `/vacinas/{id}` | Busca por id (404 se não existir) |
| POST | `/vacinas` | Cria (201) |
| PUT | `/vacinas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/vacinas/{id}` | Exclui (204) |
| GET | `/aplicacoes` | Lista todos |
| GET | `/aplicacoes?vacinaId=1` | Lista filtrando por Vacina |
| GET | `/aplicacoes/{id}` | Busca por id (404 se não existir) |
| POST | `/aplicacoes` | Cria (201) |
| PUT | `/aplicacoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/aplicacoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\clinica-vacinacao.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/vacinas
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/clinica-vacinacao`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Vacina → Aplicacao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/vacinas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo", "doses": 10 }'
```

```bash
# Vacina
curl -X POST http://localhost:8080/vacinas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo", "doses": 10 }'
curl http://localhost:8080/vacinas
curl http://localhost:8080/vacinas/1
curl -X PUT http://localhost:8080/vacinas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo", "doses": 10 }'
curl -X DELETE http://localhost:8080/vacinas/1

# Aplicacao
curl -X POST http://localhost:8080/aplicacoes -H "Content-Type: application/json" -d '{ "paciente": "Paciente exemplo", "dataAplicacao": "2026-03-15", "lote": "Lote exemplo", "dose": 10, "vacina": { "id": 1 } }'
curl http://localhost:8080/aplicacoes
curl http://localhost:8080/aplicacoes/1
curl -X PUT http://localhost:8080/aplicacoes/1 -H "Content-Type: application/json" -d '{ "paciente": "Paciente exemplo", "dataAplicacao": "2026-03-15", "lote": "Lote exemplo", "dose": 10, "vacina": { "id": 1 } }'
curl -X DELETE http://localhost:8080/aplicacoes/1
```

## Estrutura

```
src/main/java/com/exemplo/clinicavacinacao/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
