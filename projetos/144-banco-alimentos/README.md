# 144 · Banco de Alimentos

**Categoria:** Comunidade
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Registro de doadores, alimentos recebidos e distribuições às famílias.

## Entidades

### Doador (tabela `doadores`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Alimento (tabela `alimentos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `validade` | `LocalDate` |
| `quantidadeKg` | `Double` |
| `doador` | `Doador` (ManyToOne, coluna `doador_id`) |

### Distribuicao (tabela `distribuicoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `familia` | `String` |
| `dataEntrega` | `LocalDate` |
| `quantidadeKg` | `Double` |
| `alimento` | `Alimento` (ManyToOne, coluna `alimento_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/doadores` | Lista todos |
| GET | `/doadores/{id}` | Busca por id (404 se não existir) |
| POST | `/doadores` | Cria (201) |
| PUT | `/doadores/{id}` | Atualiza (404 se não existir) |
| DELETE | `/doadores/{id}` | Exclui (204) |
| GET | `/alimentos` | Lista todos |
| GET | `/alimentos?doadorId=1` | Lista filtrando por Doador |
| GET | `/alimentos/{id}` | Busca por id (404 se não existir) |
| POST | `/alimentos` | Cria (201) |
| PUT | `/alimentos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/alimentos/{id}` | Exclui (204) |
| GET | `/distribuicoes` | Lista todos |
| GET | `/distribuicoes?alimentoId=1` | Lista filtrando por Alimento |
| GET | `/distribuicoes/{id}` | Busca por id (404 se não existir) |
| POST | `/distribuicoes` | Cria (201) |
| PUT | `/distribuicoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/distribuicoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\banco-alimentos.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/doadores
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/banco-alimentos`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Doador → Alimento → Distribuicao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/doadores -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Doador
curl -X POST http://localhost:8080/doadores -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/doadores
curl http://localhost:8080/doadores/1
curl -X PUT http://localhost:8080/doadores/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/doadores/1

# Alimento
curl -X POST http://localhost:8080/alimentos -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "validade": "2026-03-15", "quantidadeKg": 12.5, "doador": { "id": 1 } }'
curl http://localhost:8080/alimentos
curl http://localhost:8080/alimentos/1
curl -X PUT http://localhost:8080/alimentos/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "validade": "2026-03-15", "quantidadeKg": 12.5, "doador": { "id": 1 } }'
curl -X DELETE http://localhost:8080/alimentos/1

# Distribuicao
curl -X POST http://localhost:8080/distribuicoes -H "Content-Type: application/json" -d '{ "familia": "Familia exemplo", "dataEntrega": "2026-03-15", "quantidadeKg": 12.5, "alimento": { "id": 1 } }'
curl http://localhost:8080/distribuicoes
curl http://localhost:8080/distribuicoes/1
curl -X PUT http://localhost:8080/distribuicoes/1 -H "Content-Type: application/json" -d '{ "familia": "Familia exemplo", "dataEntrega": "2026-03-15", "quantidadeKg": 12.5, "alimento": { "id": 1 } }'
curl -X DELETE http://localhost:8080/distribuicoes/1
```

## Estrutura

```
src/main/java/com/exemplo/bancoalimentos/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
