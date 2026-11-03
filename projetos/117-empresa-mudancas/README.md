# 117 · Empresa de Mudanças

**Categoria:** Serviços
**Banco:** H2 (arquivo) · **Lombok:** sim · **Camada Service:** não

Organização de mudanças residenciais, clientes e caminhões da empresa.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `telefone` | `String` |

### Caminhao (tabela `caminhoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `capacidadeKg` | `Double` |

### Mudanca (tabela `mudancas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `dataMudanca` | `LocalDate` |
| `origem` | `String` |
| `destino` | `String` |
| `valor` | `BigDecimal` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |
| `caminhao` | `Caminhao` (ManyToOne, coluna `caminhao_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/caminhoes` | Lista todos |
| GET | `/caminhoes/{id}` | Busca por id (404 se não existir) |
| POST | `/caminhoes` | Cria (201) |
| PUT | `/caminhoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/caminhoes/{id}` | Exclui (204) |
| GET | `/mudancas` | Lista todos |
| GET | `/mudancas?clienteId=1` | Lista filtrando por Cliente |
| GET | `/mudancas?caminhaoId=1` | Lista filtrando por Caminhao |
| GET | `/mudancas/{id}` | Busca por id (404 se não existir) |
| POST | `/mudancas` | Cria (201) |
| PUT | `/mudancas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/mudancas/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\empresa-mudancas.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/empresa-mudancas`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Caminhao → Mudanca.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "telefone": "(11) 99999-0000" }'
curl -X DELETE http://localhost:8080/clientes/1

# Caminhao
curl -X POST http://localhost:8080/caminhoes -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "capacidadeKg": 12.5 }'
curl http://localhost:8080/caminhoes
curl http://localhost:8080/caminhoes/1
curl -X PUT http://localhost:8080/caminhoes/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "capacidadeKg": 12.5 }'
curl -X DELETE http://localhost:8080/caminhoes/1

# Mudanca
curl -X POST http://localhost:8080/mudancas -H "Content-Type: application/json" -d '{ "dataMudanca": "2026-03-15", "origem": "Origem exemplo", "destino": "Destino exemplo", "valor": 99.90, "cliente": { "id": 1 }, "caminhao": { "id": 1 } }'
curl http://localhost:8080/mudancas
curl http://localhost:8080/mudancas/1
curl -X PUT http://localhost:8080/mudancas/1 -H "Content-Type: application/json" -d '{ "dataMudanca": "2026-03-15", "origem": "Origem exemplo", "destino": "Destino exemplo", "valor": 99.90, "cliente": { "id": 1 }, "caminhao": { "id": 1 } }'
curl -X DELETE http://localhost:8080/mudancas/1
```

## Estrutura

```
src/main/java/com/exemplo/empresamudancas/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
