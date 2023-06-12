@echo off
setlocal enabledelayedexpansion

set "searchWord=script"

for /r %%F in (*.html) do (
    findstr /i /c:"%searchWord%" "%%F" >nul && (
        echo %%~nxF
    )
)

pause
