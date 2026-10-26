@echo off
setlocal

rem Keep Java/Gradle local sockets under a short Windows temp path.
set "TEMP=C:\Temp"
set "TMP=C:\Temp"

set "ANDROID_STUDIO_EXE=%ProgramFiles%\Android\Android Studio\bin\studio64.exe"
if not exist "%ANDROID_STUDIO_EXE%" (
    echo Android Studio was not found at:
    echo "%ANDROID_STUDIO_EXE%"
    pause
    exit /b 1
)

start "" "%ANDROID_STUDIO_EXE%" "%~dp0"
endlocal
