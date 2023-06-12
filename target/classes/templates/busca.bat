@echo off
setlocal enabledelayedexpansion

set "searchWord=palabra_a_buscar"

for /r %%F in (*.java) do (
    findstr /i /c:"%searchWord%" "%%F" >nul && (
        echo %%~nxF
    )
)

pause
