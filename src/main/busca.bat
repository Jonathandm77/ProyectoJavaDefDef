@echo off
setlocal enabledelayedexpansion

set "searchWord=EnableWeb"

for /r %%F in (*.java *.html *.js) do (
    findstr /i /c:"%searchWord%" "%%F" >nul && (
        echo %%~nxF
    )
)

pause
