@echo off
rem Atalho para quem prefere dois cliques: chama o run.ps1 sem exigir mudar a política de execução.
chcp 65001 >nul
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0run.ps1" %*
set CODIGO=%ERRORLEVEL%
if not "%CODIGO%"=="0" (
    echo.
    pause
)
exit /b %CODIGO%
