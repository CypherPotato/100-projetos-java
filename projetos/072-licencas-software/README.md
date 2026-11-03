# 072 · Licenças de Software

**Categoria:** Tecnologia
**Banco:** SQL Server · **Lombok:** não · **Camada Service:** sim

Controle das licenças adquiridas para cada software da empresa.

## Entidades

### Software (tabela `softwares`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `fabricante` | `String` |
| `versao` | `String` |

### Licenca (tabela `licencas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `chaveLicenca` | `String` |
| `quantidadeUsuarios` | `Integer` |
| `validade` | `LocalDate` |
| `custo` | `BigDecimal` |
| `software` | `Software` (ManyToOne, coluna `software_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/softwares` | Lista todos |
| GET | `/softwares/{id}` | Busca por id (404 se não existir) |
| POST | `/softwares` | Cria (201) |
| PUT | `/softwares/{id}` | Atualiza (404 se não existir) |
| DELETE | `/softwares/{id}` | Exclui (204) |
| GET | `/licencas` | Lista todos |
| GET | `/licencas?softwareId=1` | Lista filtrando por Software |
| GET | `/licencas/{id}` | Busca por id (404 se não existir) |
| POST | `/licencas` | Cria (201) |
| PUT | `/licencas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/licencas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/softwares

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Software → Licenca.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/softwares -ContentType "application/json" -Body '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo", "versao": "Versao exemplo" }'
```

```bash
# Software
curl -X POST http://localhost:8080/softwares -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo", "versao": "Versao exemplo" }'
curl http://localhost:8080/softwares
curl http://localhost:8080/softwares/1
curl -X PUT http://localhost:8080/softwares/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "fabricante": "Fabricante exemplo", "versao": "Versao exemplo" }'
curl -X DELETE http://localhost:8080/softwares/1

# Licenca
curl -X POST http://localhost:8080/licencas -H "Content-Type: application/json" -d '{ "chaveLicenca": "ChaveLicenca exemplo", "quantidadeUsuarios": 10, "validade": "2026-03-15", "custo": 99.90, "software": { "id": 1 } }'
curl http://localhost:8080/licencas
curl http://localhost:8080/licencas/1
curl -X PUT http://localhost:8080/licencas/1 -H "Content-Type: application/json" -d '{ "chaveLicenca": "ChaveLicenca exemplo", "quantidadeUsuarios": 10, "validade": "2026-03-15", "custo": 99.90, "software": { "id": 1 } }'
curl -X DELETE http://localhost:8080/licencas/1
```

## Estrutura

```
src/main/java/com/exemplo/licencassoftware/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
