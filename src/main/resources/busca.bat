@echo off
setlocal enabledelayedexpansion

set "searchWord=8095"

for /r %%F in (*.js) do (
    findstr /i /c:"%searchWord%" "%%F" >nul && (
        echo %%~nxF
    )
)

pause
