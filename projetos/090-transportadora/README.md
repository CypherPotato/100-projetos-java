# 090 · Transportadora

**Categoria:** Agro e Logística
**Banco:** SQL Server · **Lombok:** sim · **Camada Service:** sim

Gerenciamento de motoristas, veículos e entregas de uma transportadora.

## Entidades

### Motorista (tabela `motoristas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `nome` | `String` |
| `cnh` | `String` |
| `telefone` | `String` |
| `ativo` | `Boolean` |

### Veiculo (tabela `veiculos`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `placa` | `String` |
| `modelo` | `String` |
| `capacidadeKg` | `Integer` |

### Entrega (tabela `entregas`)

| Campo | Tipo |
|---|---|
| `id` | `Long` (gerado) |
| `destino` | `String` |
| `pesoKg` | `Double` |
| `valorFrete` | `BigDecimal` |
| `dataPrevista` | `LocalDate` |
| `entregue` | `Boolean` |
| `motorista` | `Motorista` (ManyToOne, coluna `motorista_id`) |
| `veiculo` | `Veiculo` (ManyToOne, coluna `veiculo_id`) |

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
| GET | `/motoristas` | Lista todos |
| GET | `/motoristas/{id}` | Busca por id (404 se não existir) |
| POST | `/motoristas` | Cria (201) |
| PUT | `/motoristas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/motoristas/{id}` | Exclui (204) |
| GET | `/veiculos` | Lista todos |
| GET | `/veiculos/{id}` | Busca por id (404 se não existir) |
| POST | `/veiculos` | Cria (201) |
| PUT | `/veiculos/{id}` | Atualiza (404 se não existir) |
| DELETE | `/veiculos/{id}` | Exclui (204) |
| GET | `/entregas` | Lista todos |
| GET | `/entregas?motoristaId=1` | Lista filtrando por Motorista |
| GET | `/entregas?veiculoId=1` | Lista filtrando por Veiculo |
| GET | `/entregas/{id}` | Busca por id (404 se não existir) |
| POST | `/entregas` | Cria (201) |
| PUT | `/entregas/{id}` | Atualiza (404 se não existir) |
| DELETE | `/entregas/{id}` | Exclui (204) |

## Como rodar

1. Configure o SQL Server uma vez com `..\..\setup-sqlserver.ps1` (cria o banco `projeto` vazio).
2. Execute `run.bat` nesta pasta. Na primeira vez ele baixa o Maven, o Tomcat e as dependências, gera o `ROOT.war` e o implanta em um Tomcat isolado em `target\tomcat`. Para outra porta: `.\run.ps1 -Porta 9090`.
3. Acesse http://localhost:8080/motoristas

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): Motorista → Veiculo → Entrega.

Nos filtros de listagem (`?xxxId=`), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use `curl.exe` e escape as aspas do JSON (`-d '{\"nome\":\"Maria\"}'`) ou use `Invoke-RestMethod`:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/motoristas -ContentType "application/json" -Body '{ "nome": "Maria Silva", "cnh": "Cnh exemplo", "telefone": "(11) 99999-0000", "ativo": true }'
```

```bash
# Motorista
curl -X POST http://localhost:8080/motoristas -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cnh": "Cnh exemplo", "telefone": "(11) 99999-0000", "ativo": true }'
curl http://localhost:8080/motoristas
curl http://localhost:8080/motoristas/1
curl -X PUT http://localhost:8080/motoristas/1 -H "Content-Type: application/json" -d '{ "nome": "Maria Silva", "cnh": "Cnh exemplo", "telefone": "(11) 99999-0000", "ativo": true }'
curl -X DELETE http://localhost:8080/motoristas/1

# Veiculo
curl -X POST http://localhost:8080/veiculos -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "capacidadeKg": 10 }'
curl http://localhost:8080/veiculos
curl http://localhost:8080/veiculos/1
curl -X PUT http://localhost:8080/veiculos/1 -H "Content-Type: application/json" -d '{ "placa": "ABC1D23", "modelo": "Modelo Y", "capacidadeKg": 10 }'
curl -X DELETE http://localhost:8080/veiculos/1

# Entrega
curl -X POST http://localhost:8080/entregas -H "Content-Type: application/json" -d '{ "destino": "Destino exemplo", "pesoKg": 12.5, "valorFrete": 99.90, "dataPrevista": "2026-03-15", "entregue": true, "motorista": { "id": 1 }, "veiculo": { "id": 1 } }'
curl http://localhost:8080/entregas
curl http://localhost:8080/entregas/1
curl -X PUT http://localhost:8080/entregas/1 -H "Content-Type: application/json" -d '{ "destino": "Destino exemplo", "pesoKg": 12.5, "valorFrete": 99.90, "dataPrevista": "2026-03-15", "entregue": true, "motorista": { "id": 1 }, "veiculo": { "id": 1 } }'
curl -X DELETE http://localhost:8080/entregas/1
```

## Estrutura

```
src/main/java/com/exemplo/transportadora/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository
  service/      regras simples entre controller e repositório
  controller/   endpoints REST
src/main/resources/application.properties
```
