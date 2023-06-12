@echo off

set "palabraAnterior=modal-body"
set "palabraNueva=modal-body text-break"

powershell -Command "Get-ChildItem -Filter *.html | ForEach-Object { $contenido = Get-Content $_.FullName; $contenido = $contenido -replace $env:palabraAnterior, $env:palabraNueva; Set-Content $_.FullName $contenido; $_.FullName }"
pause