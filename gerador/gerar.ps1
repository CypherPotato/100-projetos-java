#Requires -Version 5.1
<#
  gerar.ps1 — gera os 100 projetos em ..\projetos a partir de spec.json.

  Uso:
    .\gerar.ps1            gera tudo e atualiza o README raiz
    .\gerar.ps1 12 35      gera apenas os projetos informados
#>
param([int[]] $Numeros = @())

$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

$Raiz = Split-Path -Parent $PSScriptRoot
$PastaProjetos = Join-Path $Raiz "projetos"
$PastaTemplates = Join-Path $PSScriptRoot "templates"

$SpringBoot = "3.5.16"
$JavaVersion = "17"
$Utf8SemBom = New-Object System.Text.UTF8Encoding($false)
$Utf8ComBom = New-Object System.Text.UTF8Encoding($true)

$Tipos = @{
    String = $null; Integer = $null; Long = $null; Double = $null; Boolean = $null
    BigDecimal = "java.math.BigDecimal"; LocalDate = "java.time.LocalDate"; LocalDateTime = "java.time.LocalDateTime"
}

$Categorias = @(
    "Comércio", "Educação", "Saúde", "Serviços", "Gestão", "Finanças",
    "Lazer e Cultura", "Alimentação", "Tecnologia", "Comunidade", "Agro e Logística"
)

$ReservadasJava = ("abstract assert boolean break byte case catch char class const continue default do double else enum " +
    "extends final finally float for goto if implements import instanceof int interface long native new package private " +
    "protected public return short static strictfp super switch synchronized this throw throws transient try void volatile while " +
    "true false null id") -split " "

$ReservadasSql = ("user order group plan key file index table view function procedure transaction schema public role " +
    "rule check column database default open close read write print exit return select from where end option set update delete " +
    "insert into values by to on off in is of or and not null top with over percent status level text image date time year month day") -split " "

# ---------- utilidades ----------

function LowerFirst($s) { $s.Substring(0, 1).ToLower() + $s.Substring(1) }
function UpperFirst($s) { $s.Substring(0, 1).ToUpper() + $s.Substring(1) }
function Rota($tabela) { $tabela -replace "_", "-" }
function Snake($campo) { [regex]::Replace($campo, "([A-Z])", '_$1').ToLower() }
function Pacote($slug) { $slug -replace "-", "" }
function NumeroFormatado($n) { "{0:000}" -f $n }
# A vírgula impede o PowerShell de desembrulhar um array de 1 elemento (senão .Count vira lookup de propriedade).
function Relacoes($e) { if ($e.relacoes) { , [array] $e.relacoes } else { , @() } }

function Escrever($arquivo, $conteudo) {
    New-Item -ItemType Directory -Force -Path (Split-Path -Parent $arquivo) | Out-Null
    [IO.File]::WriteAllText($arquivo, ($conteudo -replace "`r?`n", "`n"), $Utf8SemBom)
}

function ValidarProjeto($p, $slugs, $numeros) {
    function Erro($m) { throw "Projeto $($p.numero) ($($p.slug)): $m" }

    if ($p.numero -lt 1 -or $p.numero -gt 100) { Erro "numero inválido" }
    if ($numeros.Contains($p.numero)) { Erro "numero repetido" }
    [void] $numeros.Add($p.numero)
    if ($p.slug -notmatch "^[a-z0-9-]+$") { Erro "slug inválido" }
    if ($slugs.Contains($p.slug)) { Erro "slug repetido" }
    [void] $slugs.Add($p.slug)
    if ($Categorias -notcontains $p.categoria) { Erro "categoria inválida: $($p.categoria)" }
    if (-not $p.nome -or -not $p.descricao) { Erro "nome/descricao obrigatórios" }
    if (@($p.entidades).Count -lt 2 -or @($p.entidades).Count -gt 3) { Erro "precisa de 2 ou 3 entidades" }

    $declaradas = New-Object System.Collections.Generic.HashSet[string]
    $relacoes = 0

    foreach ($e in $p.entidades) {
        if ($e.nome -cnotmatch "^[A-Z][A-Za-z0-9]*$") { Erro "entidade com nome inválido: $($e.nome)" }
        if ($declaradas.Contains($e.nome)) { Erro "entidade repetida: $($e.nome)" }
        if ($e.tabela -cnotmatch "^[a-z][a-z0-9_]*$") { Erro "tabela inválida: $($e.tabela)" }
        if ($ReservadasSql -contains $e.tabela) { Erro "tabela reservada: $($e.tabela)" }
        if (@($e.campos).Count -lt 2 -or @($e.campos).Count -gt 5) { Erro "$($e.nome): 2 a 5 campos" }

        $nomesCampos = New-Object System.Collections.Generic.HashSet[string]
        $checarNome = {
            param($nome)
            if ($nome -cnotmatch "^[a-z][A-Za-z0-9]*$") { Erro "$($e.nome): campo inválido $nome" }
            if ($ReservadasJava -ccontains $nome -or $ReservadasSql -contains $nome.ToLower()) { Erro "$($e.nome): campo reservado $nome" }
            if ($nomesCampos.Contains($nome)) { Erro "$($e.nome): campo repetido $nome" }
            [void] $nomesCampos.Add($nome)
        }

        foreach ($c in $e.campos) {
            & $checarNome $c.nome
            if (-not $Tipos.ContainsKey($c.tipo)) { Erro "$($e.nome).$($c.nome): tipo inválido $($c.tipo)" }
        }

        foreach ($r in (Relacoes $e)) {
            & $checarNome $r.campo
            if (-not $declaradas.Contains($r.entidade)) { Erro "$($e.nome).$($r.campo): entidade $($r.entidade) precisa ser declarada antes" }
            $relacoes++
        }

        [void] $declaradas.Add($e.nome)
    }

    if ($relacoes -eq 0) { Erro "precisa de pelo menos uma relação" }
}

# ---------- geradores de código Java ----------

function GerarPom($p) {
    $lombok = ""
    $excludeLombok = ""
    $compilerLombok = ""

    if ($p.lombok) {
        $lombok = @"

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
"@
        $excludeLombok = @"

                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
"@
        $compilerLombok = @"

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
"@
    }

    @"
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>$SpringBoot</version>
        <relativePath/>
    </parent>

    <groupId>com.exemplo</groupId>
    <artifactId>$($p.slug)</artifactId>
    <version>1.0.0</version>
    <name>$($p.nome)</name>
    <description>$($p.descricao)</description>

    <properties>
        <java.version>$JavaVersion</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.microsoft.sqlserver</groupId>
            <artifactId>mssql-jdbc</artifactId>
            <scope>runtime</scope>
        </dependency>$lombok
    </dependencies>

    <build>
        <finalName>app</finalName>
        <plugins>$compilerLombok
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>$excludeLombok
            </plugin>
        </plugins>
    </build>
</project>

"@
}

function GerarProperties($p) {
    @"
spring.application.name=$($p.slug)
server.port=8080

# Conexão com o SQL Server (valores criados pelo setup-sqlserver.ps1)
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=projeto;encrypt=true;trustServerCertificate=true
spring.datasource.username=projeto
spring.datasource.password=Projeto@123

# O Hibernate cria/atualiza as tabelas automaticamente a partir das entidades
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

"@
}

function GerarApplication($p) {
    @"
package com.exemplo.$(Pacote $p.slug);

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

"@
}

function GerarEntidade($p, $e) {
    $imports = New-Object System.Collections.Generic.HashSet[string]
    [void] $imports.Add("jakarta.persistence.*")
    foreach ($c in $e.campos) { if ($Tipos[$c.tipo]) { [void] $imports.Add($Tipos[$c.tipo]) } }
    if ($p.lombok) {
        [void] $imports.Add("lombok.Getter")
        [void] $imports.Add("lombok.NoArgsConstructor")
        [void] $imports.Add("lombok.Setter")
    }
    $importsOrdenados = [string[]] $imports | Sort-Object { $_ } -Culture ""

    $atributos = @(@"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
"@)
    foreach ($c in $e.campos) { $atributos += "    private $($c.tipo) $($c.nome);" }
    foreach ($r in (Relacoes $e)) {
        $atributos += @"
    @ManyToOne
    @JoinColumn(name = "$(Snake $r.campo)_id")
    private $($r.entidade) $($r.campo);
"@
    }

    $todos = @(@{ nome = "id"; tipo = "Long" }) + @($e.campos | ForEach-Object { @{ nome = $_.nome; tipo = $_.tipo } })
    foreach ($r in (Relacoes $e)) { $todos += @{ nome = $r.campo; tipo = $r.entidade } }

    $acessores = ""
    if (-not $p.lombok) {
        $acessores = "`n" + (($todos | ForEach-Object {
            @"

    public $($_.tipo) get$(UpperFirst $_.nome)() {
        return $($_.nome);
    }

    public void set$(UpperFirst $_.nome)($($_.tipo) $($_.nome)) {
        this.$($_.nome) = $($_.nome);
    }
"@
        }) -join "`n")
    }

    $anotacoesLombok = if ($p.lombok) { "@Getter`n@Setter`n@NoArgsConstructor`n" } else { "" }

    @"
package com.exemplo.$(Pacote $p.slug).model;

$(($importsOrdenados | ForEach-Object { "import $_;" }) -join "`n")

$anotacoesLombok@Entity
@Table(name = "$($e.tabela)")
public class $($e.nome) {

$($atributos -join "`n`n")$acessores
}

"@
}

function GerarRepository($p, $e) {
    $relacoes = Relacoes $e
    $imports = @("org.springframework.data.jpa.repository.JpaRepository")
    if ($relacoes.Count) { $imports = @("java.util.List") + $imports }

    $metodos = ($relacoes | ForEach-Object {
        "`n    List<$($e.nome)> findBy$(UpperFirst $_.campo)Id(Long $($_.campo)Id);`n"
    }) -join ""

    @"
package com.exemplo.$(Pacote $p.slug).repository;

import com.exemplo.$(Pacote $p.slug).model.$($e.nome);
$(($imports | ForEach-Object { "import $_;" }) -join "`n")

public interface $($e.nome)Repository extends JpaRepository<$($e.nome), Long> {
$metodos}

"@
}

function ParametrosListar($e) {
    $relacoes = Relacoes $e
    if (-not $relacoes.Count) { return @{ assinatura = ""; corpo = "        return repository.findAll();" } }

    $assinatura = ($relacoes | ForEach-Object { "@RequestParam(required = false) Long $($_.campo)Id" }) -join ", "
    $corpo = (($relacoes | ForEach-Object {
        @"
        if ($($_.campo)Id != null) {
            return repository.findBy$(UpperFirst $_.campo)Id($($_.campo)Id);
        }
"@
    }) -join "`n") + "`n        return repository.findAll();"

    @{ assinatura = $assinatura; corpo = $corpo }
}

function GerarService($p, $e) {
    $nome = $e.nome
    $variavel = LowerFirst $nome
    $listar = ParametrosListar $e
    $assinaturaService = $listar.assinatura -replace "@RequestParam\(required = false\) ", ""

    @"
package com.exemplo.$(Pacote $p.slug).service;

import com.exemplo.$(Pacote $p.slug).model.$nome;
import com.exemplo.$(Pacote $p.slug).repository.${nome}Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ${nome}Service {

    private final ${nome}Repository repository;

    public ${nome}Service(${nome}Repository repository) {
        this.repository = repository;
    }

    public List<$nome> listar($assinaturaService) {
$($listar.corpo)
    }

    public Optional<$nome> buscar(Long id) {
        return repository.findById(id);
    }

    public $nome criar($nome $variavel) {
        return repository.save($variavel);
    }

    public Optional<$nome> atualizar(Long id, $nome $variavel) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        $variavel.setId(id);
        return Optional.of(repository.save($variavel));
    }

    public boolean excluir(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}

"@
}

function GerarController($p, $e) {
    $nome = $e.nome
    $variavel = LowerFirst $nome
    $pkg = Pacote $p.slug
    $listar = ParametrosListar $e
    $relacoes = Relacoes $e

    $imports = @(
        "com.exemplo.$pkg.model.$nome",
        $(if ($p.service) { "com.exemplo.$pkg.service.${nome}Service" } else { "com.exemplo.$pkg.repository.${nome}Repository" }),
        "java.util.List",
        "org.springframework.http.HttpStatus",
        "org.springframework.http.ResponseEntity",
        "org.springframework.web.bind.annotation.*"
    )

    $dependencia = if ($p.service) { "${nome}Service service" } else { "${nome}Repository repository" }
    $campoDependencia = if ($p.service) { "service" } else { "repository" }

    if ($p.service) {
        $corpoListar = "        return service.listar($(($relacoes | ForEach-Object { "$($_.campo)Id" }) -join ", "));"
        $corpoBuscar = @"
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
"@
        $corpoCriar = "        return service.criar($variavel);"
        $corpoAtualizar = @"
        return service.atualizar(id, $variavel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
"@
        $corpoExcluir = @"
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
"@
    } else {
        $corpoListar = $listar.corpo
        $corpoBuscar = @"
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
"@
        $corpoCriar = "        return repository.save($variavel);"
        $corpoAtualizar = @"
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        $variavel.setId(id);
        return ResponseEntity.ok(repository.save($variavel));
"@
        $corpoExcluir = @"
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
"@
    }

    @"
package com.exemplo.$pkg.controller;

$(($imports | ForEach-Object { "import $_;" }) -join "`n")

@RestController
@RequestMapping("/$(Rota $e.tabela)")
public class ${nome}Controller {

    private final $dependencia;

    public ${nome}Controller($dependencia) {
        this.$campoDependencia = $campoDependencia;
    }

    @GetMapping
    public List<$nome> listar($($listar.assinatura)) {
$corpoListar
    }

    @GetMapping("/{id}")
    public ResponseEntity<$nome> buscar(@PathVariable Long id) {
$corpoBuscar
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public $nome criar(@RequestBody $nome $variavel) {
$corpoCriar
    }

    @PutMapping("/{id}")
    public ResponseEntity<$nome> atualizar(@PathVariable Long id, @RequestBody $nome $variavel) {
$corpoAtualizar
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
$corpoExcluir
    }
}

"@
}

# ---------- README por projeto ----------

$ExemplosString = @{
    nome = "Maria Silva"; titulo = "Exemplo"; descricao = "Descrição de exemplo"; email = "maria@exemplo.com"
    telefone = "(11) 99999-0000"; cpf = "123.456.789-00"; cnpj = "12.345.678/0001-00"; endereco = "Rua das Flores, 100"
    cidade = "São Paulo"; estado = "SP"; cep = "01000-000"; placa = "ABC1D23"; marca = "Marca X"; modelo = "Modelo Y"
    categoria = "Geral"; observacao = "Sem observações"; observacoes = "Sem observações"; codigo = "COD-001"
}

function ValorExemplo($campo) {
    switch ($campo.tipo) {
        "String" {
            $texto = if ($ExemplosString.ContainsKey($campo.nome)) { $ExemplosString[$campo.nome] } else { "$(UpperFirst $campo.nome) exemplo" }
            return '"' + $texto + '"'
        }
        "Integer" { return "10" }
        "Long" { return "100" }
        "Double" { return "12.5" }
        "BigDecimal" { return "99.90" }
        "Boolean" { return "true" }
        "LocalDate" { return '"2026-03-15"' }
        "LocalDateTime" { return '"2026-03-15T10:30:00"' }
    }
    "null"
}

function JsonExemplo($e) {
    $partes = @($e.campos | ForEach-Object { """$($_.nome)"": $(ValorExemplo $_)" })
    $partes += @((Relacoes $e) | ForEach-Object { """$($_.campo)"": { ""id"": 1 }" })
    "{ $($partes -join ', ') }"
}

function GerarReadmeProjeto($p) {
    $entidades = ($p.entidades | ForEach-Object {
        $e = $_
        $campos = ($e.campos | ForEach-Object { "| ``$($_.nome)`` | ``$($_.tipo)`` |" }) -join "`n"
        $relacoes = ((Relacoes $e) | ForEach-Object { "| ``$($_.campo)`` | ``$($_.entidade)`` (ManyToOne, coluna ``$(Snake $_.campo)_id``) |" }) -join "`n"
        if ($relacoes) { $relacoes = "`n" + $relacoes }
        @"
### $($e.nome) (tabela ``$($e.tabela)``)

| Campo | Tipo |
|---|---|
| ``id`` | ``Long`` (gerado) |
$campos$relacoes
"@
    }) -join "`n`n"

    $endpoints = ($p.entidades | ForEach-Object {
        $e = $_
        $r = Rota $e.tabela
        $filtros = ((Relacoes $e) | ForEach-Object { "| GET | ``/${r}?$($_.campo)Id=1`` | Lista filtrando por $($_.entidade) |" }) -join "`n"
        if ($filtros) { $filtros += "`n" }
        @"
| GET | ``/$r`` | Lista todos |
$filtros| GET | ``/$r/{id}`` | Busca por id (404 se não existir) |
| POST | ``/$r`` | Cria (201) |
| PUT | ``/$r/{id}`` | Atualiza (404 se não existir) |
| DELETE | ``/$r/{id}`` | Exclui (204) |
"@
    }) -join "`n"

    $curls = ($p.entidades | ForEach-Object {
        $e = $_
        $r = Rota $e.tabela
        $json = JsonExemplo $e
        @"
# $($e.nome)
curl -X POST http://localhost:8080/$r -H "Content-Type: application/json" -d '$json'
curl http://localhost:8080/$r
curl http://localhost:8080/$r/1
curl -X PUT http://localhost:8080/$r/1 -H "Content-Type: application/json" -d '$json'
curl -X DELETE http://localhost:8080/$r/1
"@
    }) -join "`n`n"

    $ordem = ($p.entidades | ForEach-Object { $_.nome }) -join " → "
    $primeira = $p.entidades[0]
    $rotaPrimeira = Rota $primeira.tabela
    $lombokTexto = if ($p.lombok) { "sim" } else { "não" }
    $serviceTexto = if ($p.service) { "sim" } else { "não" }
    $estruturaService = if ($p.service) { "`n  service/      regras simples entre controller e repositório" } else { "" }

    @"
# $(NumeroFormatado $p.numero) · $($p.nome)

**Categoria:** $($p.categoria)
**Lombok:** $lombokTexto · **Camada Service:** $serviceTexto

$($p.descricao)

## Entidades

$entidades

## Endpoints

| Verbo | Rota | Descrição |
|---|---|---|
$endpoints

## Como rodar

1. Configure o SQL Server uma vez com ``..\..\setup-sqlserver.ps1`` (cria o banco ``projeto`` vazio).
2. Execute ``run.bat`` nesta pasta. Na primeira vez ele baixa o Maven e as dependências.
3. Acesse http://localhost:8080/$rotaPrimeira

Ordem sugerida para cadastrar (as relações precisam do registro "pai" criado antes): $ordem.

Nos filtros de listagem (``?xxxId=``), apenas um filtro é aplicado por vez.

## Exemplos com curl

Os comandos abaixo são para Git Bash. No PowerShell use ``curl.exe`` e escape as aspas do JSON (``-d '{\"nome\":\"Maria\"}'``) ou use ``Invoke-RestMethod``:

``````powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/$rotaPrimeira -ContentType "application/json" -Body '$(JsonExemplo $primeira)'
``````

``````bash
$curls
``````

## Estrutura

``````
src/main/java/com/exemplo/$(Pacote $p.slug)/
  Application.java
  model/        entidades JPA
  repository/   interfaces JpaRepository$estruturaService
  controller/   endpoints REST
src/main/resources/application.properties
``````

"@
}

# ---------- README raiz ----------

function GerarReadmeRaiz($projetos) {
    $secoes = foreach ($c in $Categorias) {
        $doGrupo = @($projetos | Where-Object { $_.categoria -eq $c })
        if (-not $doGrupo.Count) { continue }

        $linhas = ($doGrupo | ForEach-Object {
            $entidades = ($_.entidades | ForEach-Object { $_.nome }) -join ", "
            $pasta = "$(NumeroFormatado $_.numero)-$($_.slug)"
            $lombok = if ($_.lombok) { "✔" } else { "" }
            $service = if ($_.service) { "✔" } else { "" }
            "| $(NumeroFormatado $_.numero) | [$($_.nome)](projetos/$pasta/) | $($_.descricao) | $entidades | $lombok | $service |"
        }) -join "`n"

        @"
### $c ($($doGrupo.Count))

| # | Projeto | Descrição | Entidades | Lombok | Service |
|---|---|---|---|---|---|
$linhas
"@
    }

    $totalLombok = @($projetos | Where-Object { $_.lombok }).Count
    $totalService = @($projetos | Where-Object { $_.service }).Count
    $intro = [IO.File]::ReadAllText((Join-Path $PastaTemplates "README-intro.md"), $Utf8SemBom)
    $intro = $intro.Replace("{{TOTAL}}", "$($projetos.Count)").Replace("{{LOMBOK}}", "$totalLombok").Replace("{{SERVICE}}", "$totalService").Replace("{{SPRING_BOOT}}", $SpringBoot)

    "$intro`n## Catálogo dos projetos`n`n$($secoes -join "`n`n")`n"
}

# ---------- geração ----------

function GerarProjeto($p) {
    $pasta = Join-Path $PastaProjetos "$(NumeroFormatado $p.numero)-$($p.slug)"
    $java = Join-Path $pasta "src\main\java\com\exemplo\$(Pacote $p.slug)"

    if (Test-Path $pasta) { Remove-Item $pasta -Recurse -Force }

    Escrever (Join-Path $pasta "pom.xml") (GerarPom $p)
    Escrever (Join-Path $pasta "README.md") (GerarReadmeProjeto $p)
    Escrever (Join-Path $pasta ".gitignore") "target/`n*.log`n"
    Escrever (Join-Path $pasta "src\main\resources\application.properties") (GerarProperties $p)
    Escrever (Join-Path $java "Application.java") (GerarApplication $p)

    foreach ($e in $p.entidades) {
        Escrever (Join-Path $java "model\$($e.nome).java") (GerarEntidade $p $e)
        Escrever (Join-Path $java "repository\$($e.nome)Repository.java") (GerarRepository $p $e)
        Escrever (Join-Path $java "controller\$($e.nome)Controller.java") (GerarController $p $e)
        if ($p.service) { Escrever (Join-Path $java "service\$($e.nome)Service.java") (GerarService $p $e) }
    }

    # Windows PowerShell 5.1 lê .ps1 sem BOM como ANSI e quebra nos acentos; por isso o BOM.
    $runPs1 = [IO.File]::ReadAllText((Join-Path $PastaTemplates "run.ps1"), $Utf8SemBom)
    [IO.File]::WriteAllText((Join-Path $pasta "run.ps1"), ($runPs1 -replace "`r?`n", "`r`n"), $Utf8ComBom)
    Copy-Item (Join-Path $PastaTemplates "run.bat") (Join-Path $pasta "run.bat")
}

$specs = [IO.File]::ReadAllText((Join-Path $PSScriptRoot "spec.json"), $Utf8SemBom) | ConvertFrom-Json
$specs = @($specs | Sort-Object numero)

$slugs = New-Object System.Collections.Generic.HashSet[string]
$numerosVistos = New-Object System.Collections.Generic.HashSet[int]
foreach ($p in $specs) { ValidarProjeto $p $slugs $numerosVistos }

$selecionados = if ($Numeros.Count) { @($specs | Where-Object { $Numeros -contains $_.numero }) } else { $specs }

foreach ($p in $selecionados) {
    GerarProjeto $p
    Write-Host "gerado $(NumeroFormatado $p.numero)-$($p.slug)"
}

if (-not $Numeros.Count) {
    Escrever (Join-Path $Raiz "README.md") (GerarReadmeRaiz $specs)
    Write-Host "README.md raiz atualizado"
}

Write-Host "$(@($selecionados).Count) projeto(s) gerado(s); $($specs.Count) na especificação."
