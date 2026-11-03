# 118 · Gráfica Rápida

**Categoria:** Serviços
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** não

Registro de clientes e dos serviços de impressão solicitados.

## Entidades

### Cliente (tabela `clientes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `empresa` | `String` |
| `email` | `String` |

### Impressao (tabela `impressoes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `descricao` | `String` |
| `copias` | `Integer` |
| `colorida` | `Boolean` |
| `valor` | `BigDecimal` |
| `cliente` | `Cliente` (ManyToOne, coluna `cliente_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/clientes` | Lista todos |
| GET | `/clientes/{id}` | Busca por id (404 se não existir) |
| POST | `/clientes` | Cria (201) |
| PUT | `/clientes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/clientes/{id}` | Exclui (204) |
| GET | `/impressoes` | Lista todos |
| GET | `/impressoes?clienteId=1` | Lista filtrando por Cliente |
| GET | `/impressoes/{id}` | Busca por id (404 se não existir) |
| POST | `/impressoes` | Cria (201) |
| PUT | `/impressoes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/impressoes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\grafica-rapida.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/clientes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/grafica-rapida`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Cliente → Impressao.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/clientes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "empresa": "Empresa exemplo", "email": "maria@exemplo.com" }'
```

```bash
# Cliente
curl -X POST http://localhost:8080/clientes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "empresa": "Empresa exemplo", "email": "maria@exemplo.com" }'
curl http://localhost:8080/clientes
curl http://localhost:8080/clientes/1
curl -X PUT http://localhost:8080/clientes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "empresa": "Empresa exemplo", "email": "maria@exemplo.com" }'
curl -X DELETE http://localhost:8080/clientes/1

# Impressao
curl -X POST http://localhost:8080/impressoes -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "copias": 10, "colorida": true, "valor": 99.90, "cliente": { "id": 1 } }'
curl http://localhost:8080/impressoes
curl http://localhost:8080/impressoes/1
curl -X PUT http://localhost:8080/impressoes/1 -H "Content-Type: application/json" -d '{ "descricao": "Descrição de exemplo", "copias": 10, "colorida": true, "valor": 99.90, "cliente": { "id": 1 } }'
curl -X DELETE http://localhost:8080/impressoes/1
```

## Estrutura

```
src/main/java/com/exemplo/graficarapida/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
