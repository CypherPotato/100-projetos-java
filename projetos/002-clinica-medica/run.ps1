#Requires -Version 5.1
<#
  run.ps1 — compila e executa este projeto Spring Boot.

  Pré-requisitos na máquina: apenas um JDK 17+ (java + javac) e o SQL Server.
  O Maven é baixado automaticamente na primeira execução para
  %LOCALAPPDATA%\spring-crud-exemplos\ e reaproveitado por todos os projetos.

  Uso:
    .\run.ps1               compila e executa
    .\run.ps1 -SoCompilar   apenas compila (gera target\app.jar)
    .\run.ps1 -Limpar       apaga a pasta target antes de compilar
#>
param(
    [switch] $SoCompilar,
    [switch] $Limpar
)

# "Continue": java/mvn escrevem no stderr e, com "Stop", o PowerShell 5.1 abortaria o script.
$ErrorActionPreference = "Continue"
$ProgressPreference = "SilentlyContinue"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$MavenVersao = "3.9.16"
$MavenUrls = @(
    "https://dlcdn.apache.org/maven/maven-3/$MavenVersao/binaries/apache-maven-$MavenVersao-bin.zip",
    "https://archive.apache.org/dist/maven/maven-3/$MavenVersao/binaries/apache-maven-$MavenVersao-bin.zip"
)
$PastaCache = Join-Path $env:LOCALAPPDATA "spring-crud-exemplos"
$PastaMaven = Join-Path $PastaCache "apache-maven-$MavenVersao"
$Projeto = $PSScriptRoot

function Titulo($texto) { Write-Host "`n== $texto" -ForegroundColor Cyan }
function Ok($texto) { Write-Host "   $texto" -ForegroundColor Green }
function Aviso($texto) { Write-Host "   $texto" -ForegroundColor Yellow }

function Falhar($titulo, [string[]] $dicas) {
    Write-Host ""
    Write-Host "ERRO: $titulo" -ForegroundColor Red
    foreach ($d in $dicas) { Write-Host "  - $d" -ForegroundColor Yellow }
    Write-Host ""
    exit 1
}

# ---------------------------------------------------------------- Java

Titulo "Verificando o Java"

$java = Get-Command java -ErrorAction SilentlyContinue
if (-not $java -and $env:JAVA_HOME) {
    $candidato = Join-Path $env:JAVA_HOME "bin\java.exe"
    if (Test-Path $candidato) { $java = Get-Command $candidato }
}
if (-not $java) {
    Falhar "Java não encontrado." @(
        "Instale um JDK 17 ou superior (ex.: https://adoptium.net/temurin/releases/).",
        "Depois, feche e abra o terminal novamente para o PATH ser atualizado."
    )
}

# ForEach + -join evita que o Out-String quebre caminhos longos na largura do console.
$propriedades = (& $java.Source -XshowSettings:properties -version 2>&1 | ForEach-Object { "$_" }) -join "`n"
$versaoJava = [regex]::Match($propriedades, "java\.version = (\S+)").Groups[1].Value
$javaHome = [regex]::Match($propriedades, "java\.home = (.+)").Groups[1].Value.Trim()
$versaoMajor = [int]($versaoJava -replace "^1\.", "" -split "[.\-+]")[0]

if ($versaoMajor -lt 17) {
    Falhar "Java $versaoJava é antigo demais; este projeto precisa de Java 17 ou superior." @(
        "Instale um JDK 17+ e garanta que ele apareça primeiro no PATH (java -version).",
        "Java encontrado em: $($java.Source)"
    )
}

if (-not (Test-Path (Join-Path $javaHome "bin\javac.exe"))) {
    Falhar "Foi encontrado apenas o JRE (sem o compilador javac) em $javaHome." @(
        "Instale um JDK (Java Development Kit) 17+, não apenas o JRE.",
        "Sugestão: https://adoptium.net/temurin/releases/ (escolha JDK)."
    )
}

$env:JAVA_HOME = $javaHome
Ok "Java $versaoJava (JDK) em $javaHome"

# ---------------------------------------------------------------- Maven

Titulo "Verificando o Maven"

$mvn = Join-Path $PastaMaven "bin\mvn.cmd"

if (-not (Test-Path $mvn)) {
    Aviso "Maven ainda não baixado. Baixando a versão $MavenVersao (cerca de 9 MB)..."
    New-Item -ItemType Directory -Force -Path $PastaCache | Out-Null
    $zip = Join-Path $PastaCache "apache-maven-$MavenVersao-bin.zip"
    $baixou = $false

    foreach ($url in $MavenUrls) {
        try {
            Invoke-WebRequest -Uri $url -OutFile $zip -UseBasicParsing -ErrorAction Stop
            $baixou = $true
            break
        } catch {
            Aviso "Falha em $url ($($_.Exception.Message))"
        }
    }

    if (-not $baixou) {
        Falhar "Não foi possível baixar o Maven." @(
            "Verifique a conexão com a internet ou o proxy.",
            "Alternativa manual: baixe $($MavenUrls[0]) e extraia em $PastaCache",
            "(a pasta final deve ser $PastaMaven)."
        )
    }

    Expand-Archive -Path $zip -DestinationPath $PastaCache -Force -ErrorAction Stop
    Remove-Item $zip -Force
}

Ok "Maven $MavenVersao em $PastaMaven"

# ---------------------------------------------------------------- Compilação

Titulo "Compilando o projeto"

Set-Location $Projeto
if ($Limpar -and (Test-Path "target")) { Remove-Item "target" -Recurse -Force }

$repositorioLocal = Join-Path $env:USERPROFILE ".m2\repository\org\springframework\boot"
if (-not (Test-Path $repositorioLocal)) {
    Aviso "Primeira compilação: o Maven vai baixar as dependências do Spring Boot (pode levar alguns minutos)."
}

$saidaMaven = & $mvn --batch-mode --quiet -DskipTests package 2>&1 | ForEach-Object { "$_" }
$codigoMaven = $LASTEXITCODE

if ($codigoMaven -ne 0) {
    $erros = $saidaMaven | Where-Object { $_ -match "^\[ERROR\]" -and $_ -notmatch "^\[ERROR\]\s*$" }
    $temErrosCodigo = $saidaMaven | Where-Object { $_ -match "\.java:\[\d+,\d+\]" }

    Write-Host ""
    if ($temErrosCodigo) {
        Write-Host "Erros de compilação no código Java:" -ForegroundColor Red
        # O Maven lista cada erro duas vezes (bloco de compilação e resumo); $vistos remove a repetição.
        $vistos = @{}
        $ultimoErro = $null
        foreach ($linha in $saidaMaven) {
            $m = [regex]::Match($linha, "^(?:\[ERROR\])?\s*(\S+\.java):\[(\d+),(\d+)\]\s*(.*)$")
            if ($m.Success) {
                $arquivo = ($m.Groups[1].Value -replace "/", "\") -replace "^.*?\\src\\", "src\"
                $ultimoErro = "$arquivo|$($m.Groups[2].Value)|$($m.Groups[4].Value)"
                if ($vistos.ContainsKey($ultimoErro)) { continue }
                $vistos[$ultimoErro] = $true
                Write-Host ("  {0} (linha {1}, coluna {2})" -f $arquivo, $m.Groups[2].Value, $m.Groups[3].Value) -ForegroundColor Yellow
                Write-Host ("     {0}" -f $m.Groups[4].Value)
                continue
            }
            $c = [regex]::Match($linha, "^(?:\[ERROR\])?\s+(symbol|location|required|found|reason):\s*(.*)$")
            if ($c.Success -and $ultimoErro) {
                $chave = "$ultimoErro|$($c.Groups[1].Value)"
                if ($vistos.ContainsKey($chave)) { continue }
                $vistos[$chave] = $true
                Write-Host ("     {0}: {1}" -f $c.Groups[1].Value, $c.Groups[2].Value) -ForegroundColor DarkGray
            }
        }
    } else {
        Write-Host "A compilação falhou. Linhas relevantes do Maven:" -ForegroundColor Red
        $erros | Select-Object -First 15 | ForEach-Object { Write-Host "  $_" -ForegroundColor Yellow }
    }

    $texto = $saidaMaven -join "`n"
    $dicas = @()
    if ($texto -match "Could not transfer|Could not resolve|UnknownHost|Connection timed out|Non-resolvable parent") {
        $dicas += "Parece um problema de rede ao baixar dependências. Verifique a internet/proxy e rode novamente."
    }
    if ($texto -match "invalid target release|release version \d+ not supported|Unsupported class file major version") {
        $dicas += "A versão do Java não é compatível. Use um JDK 17 ou superior."
    }
    if ($texto -match "Non-parseable POM|Malformed POM") {
        $dicas += "O pom.xml está malformado. Verifique se alguma edição deixou uma tag aberta."
    }
    if (-not $dicas) {
        $dicas += "Saída completa do Maven salva em target\maven-saida.log"
    }
    New-Item -ItemType Directory -Force -Path "target" | Out-Null
    $saidaMaven | Set-Content -Path "target\maven-saida.log" -Encoding UTF8
    Falhar "Compilação interrompida." $dicas
}

Ok "Compilado com sucesso: target\app.jar"
if ($SoCompilar) { exit 0 }

# ---------------------------------------------------------------- Execução

Titulo "Executando a aplicação"

$porta = 8080
$conteudoProps = Get-Content "src\main\resources\application.properties" -Raw
$m = [regex]::Match($conteudoProps, "(?m)^server\.port\s*=\s*(\d+)")
if ($m.Success) { $porta = [int]$m.Groups[1].Value }

$ocupada = Get-NetTCPConnection -LocalPort $porta -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1
if ($ocupada) {
    $processo = Get-Process -Id $ocupada.OwningProcess -ErrorAction SilentlyContinue
    Falhar "A porta $porta já está em uso (processo: $($processo.ProcessName), PID $($ocupada.OwningProcess))." @(
        "Feche a outra aplicação (provavelmente outro projeto rodando) ou",
        "altere server.port em src\main\resources\application.properties."
    )
}

Write-Host "   Acesse http://localhost:$porta/  (Ctrl+C para parar)" -ForegroundColor Green
Write-Host ""

$saidaApp = & $java.Source -jar "target\app.jar" 2>&1 | ForEach-Object { "$_"; Write-Host $_ }
$codigoApp = $LASTEXITCODE

if ($codigoApp -eq 0 -or $codigoApp -eq -1073741510 -or $codigoApp -eq 130) { exit 0 }

$texto = $saidaApp -join "`n"
$dicas = @()

if ($texto -match "TCP/IP connection to the host .* has failed|Connection refused|connect timed out") {
    $dicas += "O SQL Server não respondeu em localhost:1433."
    $dicas += "Rode o setup-sqlserver.ps1 (na raiz do repositório) como Administrador: ele habilita o TCP e inicia o serviço."
    $dicas += "Confira se o serviço 'SQL Server (SQLEXPRESS)' está em execução (services.msc)."
}
if ($texto -match "Login failed for user") {
    $dicas += "Usuário/senha do banco recusados. Confira spring.datasource.username/password em application.properties."
    $dicas += "O setup-sqlserver.ps1 cria o login 'projeto' com a senha 'Projeto@123' e habilita a autenticação mista."
}
if ($texto -match "Cannot open database") {
    $dicas += "O banco 'projeto' não existe. Rode o setup-sqlserver.ps1 para criá-lo."
}
if ($texto -match "PKIX path building failed|SSL|certificate") {
    $dicas += "Problema de certificado. A URL do datasource precisa de ;encrypt=true;trustServerCertificate=true"
}
if ($texto -match "Port \d+ was already in use|Address already in use") {
    $dicas += "A porta $porta já está em uso. Pare a outra aplicação ou altere server.port."
}
if ($texto -match "FOREIGN KEY constraint|DataIntegrityViolationException") {
    $dicas += "Conflito de chave estrangeira: provavelmente sobrou tabela de outro projeto no banco. Rode o setup-sqlserver.ps1 para zerar o banco."
}
if ($texto -match "Invalid object name|Incorrect syntax") {
    $dicas += "Erro de SQL. Verifique se spring.jpa.hibernate.ddl-auto=update está no application.properties."
}
if ($texto -match "UnsatisfiedDependencyException|NoSuchBeanDefinitionException") {
    $dicas += "O Spring não conseguiu montar um componente (@Service/@Repository/@RestController). Confira se as classes estão dentro de um subpacote de com.exemplo."
}
if (-not $dicas) {
    $dicas += "Procure acima a primeira linha com 'Caused by:' — ela costuma indicar a causa raiz."
}

$causa = $saidaApp | Where-Object { $_ -match "Caused by:" } | Select-Object -Last 1
if ($causa) { $dicas += "Causa raiz reportada: $($causa.Trim())" }

Falhar "A aplicação encerrou com erro (código $codigoApp)." $dicas
