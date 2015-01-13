@ECHO OFF

if "%1"=="eclipse" GOTO:eclipse
if "%1"=="install" GOTO:install
if "%1"=="assembly" GOTO:assembly

ECHO Invalid argument: %1
ECHO.
ECHO Usage:   %~n0 [action]
ECHO.
ECHO Actions: install  - to clean install pom.xml 
ECHO          eclipse  - to eclipse:clean eclipse:eclipse pom.xml
ECHO          assembly - to assembly:assembly pom.xml
ECHO.
GOTO:EOF

:install
call mvn clean install -f pom.xml
GOTO:End

:eclipse
call mvn eclipse:clean eclipse:eclipse -f pom.xml
GOTO:End

:assembly
call mvn assembly:assembly -DdescriptorId=jar-with-dependencies -f pom.xml
GOTO:End

:End
ECHO ... done! :)