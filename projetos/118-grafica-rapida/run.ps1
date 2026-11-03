#Requires -Version 5.1
<#
  run.ps1 — compila este projeto Spring Boot em WAR e o executa em um Tomcat.

  Pré-requisitos na máquina: apenas um JDK 17+ (java + javac) e o SQL Server.
  O Maven e o Tomcat são baixados automaticamente na primeira execução para
  %LOCALAPPDATA%\spring-crud-exemplos\ e reaproveitados por todos os projetos.
  Cada execução usa um Tomcat isolado em target\tomcat (CATALINA_BASE próprio),
  então nada de outros projetos fica implantado junto.

  Uso:
    .\run.ps1                 compila e executa
    .\run.ps1 -Porta 9090     usa outra porta HTTP (padrão 8080)
    .\run.ps1 -SoCompilar     apenas compila (gera target\ROOT.war)
    .\run.ps1 -Limpar         apaga a pasta target antes de compilar
#>
param(
    [int] $Porta = 8080,
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
$TomcatVersao = "10.1.61"
$TomcatUrls = @(
    "https://dlcdn.apache.org/tomcat/tomcat-10/v$TomcatVersao/bin/apache-tomcat-$TomcatVersao-windows-x64.zip",
    "https://archive.apache.org/dist/tomcat/tomcat-10/v$TomcatVersao/bin/apache-tomcat-$TomcatVersao-windows-x64.zip"
)
$PastaCache = Join-Path $env:LOCALAPPDATA "spring-crud-exemplos"
$PastaMaven = Join-Path $PastaCache "apache-maven-$MavenVersao"
$PastaTomcat = Join-Path $PastaCache "apache-tomcat-$TomcatVersao"
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

# ---------------------------------------------------------------- Maven e Tomcat

function Instalar-Zip($nome, [string[]] $urls, $arquivoChave, $tamanho) {
    if (Test-Path $arquivoChave) { return }

    Aviso "$nome ainda não baixado (cerca de $tamanho). Baixando..."
    New-Item -ItemType Directory -Force -Path $PastaCache | Out-Null
    $zip = Join-Path $PastaCache ([IO.Path]::GetFileName($urls[0]))
    $baixou = $false

    foreach ($url in $urls) {
        try {
            Invoke-WebRequest -Uri $url -OutFile $zip -UseBasicParsing -ErrorAction Stop
            $baixou = $true
            break
        } catch {
            Aviso "Falha em $url ($($_.Exception.Message))"
        }
    }

    if (-not $baixou) {
        Falhar "Não foi possível baixar o $nome." @(
            "Verifique a conexão com a internet ou o proxy.",
            "Alternativa manual: baixe $($urls[0]) e extraia em $PastaCache",
            "(depois de extrair, o arquivo $arquivoChave precisa existir)."
        )
    }

    Expand-Archive -Path $zip -DestinationPath $PastaCache -Force -ErrorAction Stop
    Remove-Item $zip -Force
}

Titulo "Verificando o Maven e o Tomcat"

$mvn = Join-Path $PastaMaven "bin\mvn.cmd"
$catalina = Join-Path $PastaTomcat "bin\catalina.bat"

Instalar-Zip "Maven $MavenVersao" $MavenUrls $mvn "9 MB"
Instalar-Zip "Tomcat $TomcatVersao" $TomcatUrls $catalina "16 MB"

Ok "Maven $MavenVersao em $PastaMaven"
Ok "Tomcat $TomcatVersao em $PastaTomcat"

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

Ok "Compilado com sucesso: target\ROOT.war"
if ($SoCompilar) { exit 0 }

# ---------------------------------------------------------------- Implantação no Tomcat

Titulo "Implantando no Tomcat"

$ocupada = Get-NetTCPConnection -LocalPort $Porta -State Listen -ErrorAction SilentlyContinue | Select-Object -First 1
if ($ocupada) {
    $processo = Get-Process -Id $ocupada.OwningProcess -ErrorAction SilentlyContinue
    Falhar "A porta $Porta já está em uso (processo: $($processo.ProcessName), PID $($ocupada.OwningProcess))." @(
        "Feche a outra aplicação (provavelmente outro projeto rodando) ou",
        "use outra porta: .\run.ps1 -Porta 9090"
    )
}

# CATALINA_BASE próprio: conf copiada do Tomcat instalado, webapps/logs/temp/work só deste projeto.
$base = Join-Path $Projeto "target\tomcat"
if (Test-Path $base) { Remove-Item $base -Recurse -Force }
foreach ($pasta in "logs", "temp", "webapps", "work") {
    New-Item -ItemType Directory -Force -Path (Join-Path $base $pasta) | Out-Null
}
Copy-Item (Join-Path $PastaTomcat "conf") (Join-Path $base "conf") -Recurse
Copy-Item "target\ROOT.war" (Join-Path $base "webapps\ROOT.war")

# Porta HTTP configurável e porta de shutdown (8005) desligada, para não colidir com outro Tomcat.
$serverXml = Join-Path $base "conf\server.xml"
$xml = [IO.File]::ReadAllText($serverXml)
$xml = $xml.Replace('<Connector port="8080"', "<Connector port=""$Porta""").Replace('<Server port="8005"', '<Server port="-1"')
[IO.File]::WriteAllText($serverXml, $xml, (New-Object System.Text.UTF8Encoding($false)))

$env:CATALINA_HOME = $PastaTomcat
$env:CATALINA_BASE = $base
$env:JAVA_OPTS = "-Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8"

# Saída do Tomcat vai para um arquivo que lemos em tempo real: com o Tomcat externo o processo
# continua vivo mesmo quando a aplicação Spring falha ao subir, então o erro precisa ser detectado no log.
$arquivoSaida = Join-Path $base "logs\console.log"
$comando = "/c `"`"$catalina`" run > `"$arquivoSaida`" 2>&1`""
$tomcat = Start-Process -FilePath "cmd.exe" -ArgumentList $comando -NoNewWindow -PassThru

for ($i = 0; $i -lt 50 -and -not (Test-Path $arquivoSaida); $i++) { Start-Sleep -Milliseconds 100 }
$fluxo = New-Object IO.FileStream($arquivoSaida, [IO.FileMode]::OpenOrCreate, [IO.FileAccess]::Read, [IO.FileShare]::ReadWrite)
$leitor = New-Object IO.StreamReader($fluxo, [System.Text.Encoding]::UTF8)

$saidaApp = New-Object System.Collections.Generic.List[string]
$falhou = $false
$iniciou = $false

try {
    while ($true) {
        $linha = $leitor.ReadLine()

        if ($null -eq $linha) {
            if ($tomcat.HasExited) { break }
            Start-Sleep -Milliseconds 200
            continue
        }

        $saidaApp.Add($linha)
        Write-Host $linha

        if ($linha -match "startup failed due to previous errors|Application run failed|Failed to start component|Failed to initialize component|Protocol handler start failed") {
            $falhou = $true
        }

        if ($linha -match "Server startup in \[") {
            if ($falhou) { break }
            $iniciou = $true
            Write-Host ""
            Write-Host "   Acesse http://localhost:$Porta/  (Ctrl+C para parar)" -ForegroundColor Green
            Write-Host ""
        }
    }
} finally {
    if (-not $tomcat.HasExited) { & taskkill /T /F /PID $tomcat.Id 2>&1 | Out-Null }
    $leitor.Dispose()
}

if ($iniciou -and -not $falhou) { exit 0 }

# ---------------------------------------------------------------- Diagnóstico

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
if ($texto -match "PKIX path building failed|\(SSL\) encryption|SSLHandshakeException|unable to find valid certification") {
    $dicas += "Problema de certificado. A URL do datasource precisa de ;encrypt=true;trustServerCertificate=true"
}
if ($texto -match "Address already in use|BindException") {
    $dicas += "A porta $Porta já está em uso. Pare a outra aplicação ou use .\run.ps1 -Porta 9090"
}
if ($texto -match "Database may be already in use|The file is locked") {
    $dicas += "O arquivo do banco H2 em data\ está aberto por outro processo (outro Tomcat deste projeto ou um cliente SQL). Feche-o e rode novamente."
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
if ($texto -match "UnsupportedClassVersionError|NoClassDefFoundError: jakarta") {
    $dicas += "Incompatibilidade de versões: este projeto exige Tomcat 10.1+ (Jakarta EE 10) e JDK 17+."
}
if (-not $dicas) {
    $dicas += "Procure acima a primeira linha com 'Caused by:' — ela costuma indicar a causa raiz."
    $dicas += "Log completo do Tomcat em target\tomcat\logs\"
}

$causa = $saidaApp | Where-Object { $_ -match "Caused by:" } | Select-Object -Last 1
if ($causa) { $dicas += "Causa raiz reportada: $($causa.Trim())" }

Falhar "A aplicação não conseguiu iniciar no Tomcat." $dicas
