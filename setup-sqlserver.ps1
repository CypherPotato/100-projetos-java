#Requires -Version 5.1
<#
  setup-sqlserver.ps1 — prepara um SQL Server local para os projetos de exemplo.

  O que faz (precisa rodar como Administrador):
    1. Habilita o protocolo TCP/IP da instância na porta fixa (padrão 1433).
    2. Liga a autenticação mista (Windows + SQL Server).
    3. Reinicia o serviço para aplicar as mudanças.
    4. Cria (ou redefine a senha de) um login SQL para os projetos.
    5. Exclui o banco, se existir, e cria um novo, vazio.
    6. Imprime o passo a passo para configurar o projeto Java.

  Uso:
    .\setup-sqlserver.ps1
    .\setup-sqlserver.ps1 -Instancia SQLEXPRESS -Banco projeto -Usuario projeto -Senha 'Projeto@123' -Porta 1433
#>
param(
    [string] $Instancia = "SQLEXPRESS",
    [string] $Banco = "projeto",
    [string] $Usuario = "projeto",
    [string] $Senha = "Projeto@123",
    [int] $Porta = 1433
)

$ErrorActionPreference = "Stop"
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

function Passo($texto) { Write-Host "`n== $texto" -ForegroundColor Cyan }
function Ok($texto) { Write-Host "   $texto" -ForegroundColor Green }
function Aviso($texto) { Write-Host "   $texto" -ForegroundColor Yellow }

function Falhar($titulo, [string[]] $dicas) {
    Write-Host ""
    Write-Host "ERRO: $titulo" -ForegroundColor Red
    foreach ($d in $dicas) { Write-Host "  - $d" -ForegroundColor Yellow }
    Write-Host ""
    exit 1
}

# Executa T-SQL por autenticação Windows (o usuário que rodou o script, admin da instância).
function Executar-Sql($servidor, $sql) {
    $conexao = New-Object System.Data.SqlClient.SqlConnection
    $conexao.ConnectionString = "Server=$servidor;Database=master;Integrated Security=True;TrustServerCertificate=True;Connect Timeout=15"
    $conexao.Open()
    try {
        $comando = $conexao.CreateCommand()
        $comando.CommandText = $sql
        $comando.CommandTimeout = 120
        [void] $comando.ExecuteNonQuery()
    } finally {
        $conexao.Close()
    }
}

# ---------------------------------------------------------------- pré-checagens

$principal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
if (-not $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Falhar "Este script precisa ser executado como Administrador." @(
        "Clique com o botão direito no PowerShell > 'Executar como administrador' e rode novamente."
    )
}

$nomeServico = if ($Instancia -eq "MSSQLSERVER") { "MSSQLSERVER" } else { "MSSQL`$$Instancia" }
$servico = Get-Service -Name $nomeServico -ErrorAction SilentlyContinue
if (-not $servico) {
    $instalados = Get-Service | Where-Object { $_.Name -like "MSSQL*" } | ForEach-Object { $_.Name -replace "^MSSQL\$", "" }
    Falhar "Instância '$Instancia' não encontrada." @(
        "Instâncias instaladas: $(if ($instalados) { $instalados -join ', ' } else { 'nenhuma' })",
        "Use -Instancia <nome> ou instale o SQL Server Express: https://www.microsoft.com/sql-server/sql-server-downloads"
    )
}

$chaveInstancia = (Get-ItemProperty "HKLM:\SOFTWARE\Microsoft\Microsoft SQL Server\Instance Names\SQL").$Instancia
if (-not $chaveInstancia) {
    Falhar "Não foi possível localizar o registro da instância '$Instancia'." @("Confira o nome em services.msc (SQL Server (NOME))." )
}
$chaveRaiz = "HKLM:\SOFTWARE\Microsoft\Microsoft SQL Server\$chaveInstancia"

Passo "Instância $Instancia ($chaveInstancia)"
Ok "Serviço $nomeServico está '$($servico.Status)'"

# ---------------------------------------------------------------- 1. TCP/IP

Passo "Habilitando TCP/IP na porta $Porta"

$chaveTcp = "$chaveRaiz\MSSQLServer\SuperSocketNetLib\Tcp"
Set-ItemProperty -Path $chaveTcp -Name Enabled -Value 1 -Type DWord
Set-ItemProperty -Path $chaveTcp -Name ListenOnAllIPs -Value 1 -Type DWord

# Porta fixa em todas as interfaces (IPAll) e limpa portas dinâmicas das demais.
foreach ($ip in Get-ChildItem $chaveTcp) {
    Set-ItemProperty -Path $ip.PSPath -Name TcpDynamicPorts -Value "" -Type String
    Set-ItemProperty -Path $ip.PSPath -Name TcpPort -Value "$Porta" -Type String
    if ($ip.PSChildName -ne "IPAll") {
        Set-ItemProperty -Path $ip.PSPath -Name Enabled -Value 1 -Type DWord
    }
}
Ok "TCP/IP habilitado (ListenOnAllIPs, porta fixa $Porta)"

# ---------------------------------------------------------------- 2. autenticação mista

Passo "Habilitando autenticação mista (Windows + SQL Server)"
Set-ItemProperty -Path "$chaveRaiz\MSSQLServer" -Name LoginMode -Value 2 -Type DWord
Ok "LoginMode = 2"

# ---------------------------------------------------------------- 3. reiniciar serviço

Passo "Reiniciando o serviço $nomeServico"
Restart-Service -Name $nomeServico -Force
(Get-Service -Name $nomeServico).WaitForStatus("Running", [TimeSpan]::FromSeconds(60))
Set-Service -Name $nomeServico -StartupType Automatic
Ok "Serviço em execução (inicialização automática)"

# Aguarda a instância aceitar conexões TCP.
$servidor = "localhost,$Porta"
$pronto = $false
for ($i = 0; $i -lt 30 -and -not $pronto; $i++) {
    try { Executar-Sql $servidor "SELECT 1"; $pronto = $true } catch { Start-Sleep -Seconds 2 }
}
if (-not $pronto) {
    Falhar "A instância não respondeu em $servidor após reiniciar." @(
        "Veja o log em $env:ProgramFiles\Microsoft SQL Server\$chaveInstancia\MSSQL\Log\ERRORLOG",
        "Verifique se outra aplicação usa a porta ${Porta}: netstat -ano | findstr :$Porta"
    )
}
Ok "Conexão TCP em $servidor confirmada"

# ---------------------------------------------------------------- 4. login

Passo "Criando o login SQL '$Usuario'"
$senhaSql = $Senha -replace "'", "''"
try {
    Executar-Sql $servidor @"
IF NOT EXISTS (SELECT 1 FROM sys.server_principals WHERE name = N'$Usuario')
    CREATE LOGIN [$Usuario] WITH PASSWORD = N'$senhaSql', CHECK_POLICY = OFF, DEFAULT_DATABASE = master;
ELSE
    ALTER LOGIN [$Usuario] WITH PASSWORD = N'$senhaSql', CHECK_POLICY = OFF, DEFAULT_DATABASE = master;
ALTER LOGIN [$Usuario] ENABLE;
"@
} catch {
    Falhar "Não foi possível criar o login '$Usuario': $($_.Exception.Message)" @(
        "Confira se o seu usuário do Windows é administrador da instância (sysadmin)."
    )
}
Ok "Login '$Usuario' pronto"

# ---------------------------------------------------------------- 5. banco

Passo "Recriando o banco '$Banco' (vazio)"
try {
    Executar-Sql $servidor @"
IF DB_ID(N'$Banco') IS NOT NULL
BEGIN
    ALTER DATABASE [$Banco] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE [$Banco];
END
CREATE DATABASE [$Banco];
"@
    Executar-Sql $servidor @"
ALTER AUTHORIZATION ON DATABASE::[$Banco] TO [$Usuario];
"@
} catch {
    Falhar "Não foi possível recriar o banco '$Banco': $($_.Exception.Message)" @(
        "Feche conexões abertas no banco (SSMS, aplicação Java rodando) e tente de novo."
    )
}
Ok "Banco '$Banco' criado; '$Usuario' é o proprietário"

# Testa a autenticação SQL do novo login, do jeito que o Java vai usar.
$teste = New-Object System.Data.SqlClient.SqlConnection
$teste.ConnectionString = "Server=$servidor;Database=$Banco;User ID=$Usuario;Password=$Senha;TrustServerCertificate=True;Connect Timeout=15"
try { $teste.Open(); $teste.Close() } catch {
    Falhar "O login '$Usuario' não conseguiu conectar em '$Banco': $($_.Exception.Message)" @(
        "Confira se o serviço foi reiniciado (a autenticação mista só vale após reiniciar)."
    )
}
Ok "Login '$Usuario' conectou em '$Banco' via TCP"

# ---------------------------------------------------------------- 6. firewall (apenas local, informativo)

$regra = Get-NetFirewallRule -DisplayName "SQL Server $Porta (spring-crud-exemplos)" -ErrorAction SilentlyContinue
if (-not $regra) {
    New-NetFirewallRule -DisplayName "SQL Server $Porta (spring-crud-exemplos)" -Direction Inbound -Protocol TCP -LocalPort $Porta -Action Allow -Profile Private,Domain | Out-Null
    Ok "Regra de firewall criada para a porta $Porta (perfis Privado/Domínio)"
}

# ---------------------------------------------------------------- passo a passo

$url = "jdbc:sqlserver://localhost:$Porta;databaseName=$Banco;encrypt=true;trustServerCertificate=true"

Write-Host ""
Write-Host "================================================================" -ForegroundColor Green
Write-Host " SQL Server pronto! Como usar no projeto Java" -ForegroundColor Green
Write-Host "================================================================" -ForegroundColor Green
Write-Host @"

 1. Abra o arquivo do projeto:
      src\main\resources\application.properties

 2. Confira (ou cole) estas linhas:
      spring.datasource.url=$url
      spring.datasource.username=$Usuario
      spring.datasource.password=$Senha
      spring.jpa.hibernate.ddl-auto=update

    Os projetos desta coleção já vêm com esses valores padrão
    (banco 'projeto', usuário 'projeto', senha 'Projeto@123', porta 1433).
    Se você mudou algum parâmetro deste script, ajuste o arquivo.

 3. Rode o projeto:
      .\run.ps1        (ou duplo clique em run.bat)

    O Hibernate cria as tabelas sozinho na primeira subida (ddl-auto=update).

 4. Teste no navegador ou com curl:
      http://localhost:8080/<rota-da-entidade>

 Para inspecionar o banco pelo terminal:
      sqlcmd -S localhost,$Porta -d $Banco -U $Usuario -P "$Senha" -C
 ou pelo SSMS / VS Code: servidor "localhost,$Porta", autenticação SQL Server.

 Para zerar o banco, basta rodar este script de novo.
"@
