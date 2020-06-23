
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

set APP_HOME=%DIRNAME%

DIRNAME/gradlew.bat :bootRun
