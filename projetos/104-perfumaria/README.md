# 104 · Perfumaria

**Categoria:** Comércio
**Banco:** H2 (arquivo) · **Lombok:** não · **Camada Service:** sim

Controle de fabricantes e perfumes de uma perfumaria.

## Entidades

### Fabricante (tabela `fabricantes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `pais` | `String` |

### Perfume (tabela `perfumes`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `volumeMl` | `Integer` |
| `preco` | `BigDecimal` |
| `importado` | `Boolean` |
| `fabricante` | `Fabricante` (ManyToOne, coluna `fabricante_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/fabricantes` | Lista todos |
| GET | `/fabricantes/{id}` | Busca por id (404 se não existir) |
| POST | `/fabricantes` | Cria (201) |
| PUT | `/fabricantes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/fabricantes/{id}` | Exclui (204) |
| GET | `/perfumes` | Lista todos |
| GET | `/perfumes?fabricanteId=1` | Lista filtrando por Fabricante |
| GET | `/perfumes/{id}` | Busca por id (404 se não existir) |
| POST | `/perfumes` | Cria (201) |
| PUT | `/perfumes/{id}` | Atualiza (404 se não existir) |
| DELETE | `/perfumes/{id}` | Exclui (204) |

## Como rodar

1. Não precisa instalar banco: o H2 vem como dependência e grava os dados em `data\perfumaria.mv.db` nesta pasta. Para zerar, pare o projeto e apague a pasta `data`.
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/fabricantes
4. Para ver as tabelas, abra http://localhost:8080/h2-console com JDBC URL `jdbc:h2:file:./data/perfumaria`, usuário `sa` e senha vazia.

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Fabricante → Perfume.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/fabricantes -ContentType "application/json" -Body '{ "nome": "Maria Silva", "pais": "Pais exemplo" }'
```

```bash
# Fabricante
curl -X POST http://localhost:8080/fabricantes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pais": "Pais exemplo" }'
curl http://localhost:8080/fabricantes
curl http://localhost:8080/fabricantes/1
curl -X PUT http://localhost:8080/fabricantes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "pais": "Pais exemplo" }'
curl -X DELETE http://localhost:8080/fabricantes/1

# Perfume
curl -X POST http://localhost:8080/perfumes -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "volumeMl": 10, "preco": 99.90, "importado": true, "fabricante": { "id": 1 } }'
curl http://localhost:8080/perfumes
curl http://localhost:8080/perfumes/1
curl -X PUT http://localhost:8080/perfumes/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "volumeMl": 10, "preco": 99.90, "importado": true, "fabricante": { "id": 1 } }'
curl -X DELETE http://localhost:8080/perfumes/1
```

## Estrutura

```
src/main/java/com/exemplo/perfumaria/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
data/                    banco H2 (criado na primeira execução, ignorado pelo Git)
```
