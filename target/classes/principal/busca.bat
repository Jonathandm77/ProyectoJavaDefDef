@echo off
setlocal enabledelayedexpansion

set "searchWord=temaClaro"

for /r %%F in (*.java) do (
    findstr /i /c:"%searchWord%" "%%F" >nul && (
        echo %%~nxF
    )
)

pause
