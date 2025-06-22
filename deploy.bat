@echo off
setlocal

REM === Configuration ===
set WAR_NAME=GestionCafe.war
set APP_NAME=GestionCafe
set PROJECT_DIR=%~dp0
set TARGET_DIR=%PROJECT_DIR%target
set TOMCAT_WEBAPPS_DIR="C:\tomcat\apache-tomcat-10.1.28\webapps"

REM === Vérification de l'existence du fichier WAR ===
IF NOT EXIST "%TARGET_DIR%\%WAR_NAME%" (
    echo Le fichier WAR "%WAR_NAME%" est introuvable dans le dossier target.
    pause
    exit /b 1
)

REM === Suppression du WAR existant ===
echo Suppression de %WAR_NAME% existant dans Tomcat...
del /F /Q %TOMCAT_WEBAPPS_DIR%\%WAR_NAME%

REM === Suppression du dossier de l'application déployée ===
echo Suppression du dossier %APP_NAME% dans Tomcat...
rd /S /Q %TOMCAT_WEBAPPS_DIR%\%APP_NAME%

REM === Copie du WAR dans le dossier webapps de Tomcat ===
echo Copie de %WAR_NAME% vers le dossier webapps de Tomcat...
copy /Y "%TARGET_DIR%\%WAR_NAME%" %TOMCAT_WEBAPPS_DIR%

IF %ERRORLEVEL% EQU 0 (
    echo Deploiement termine avec succes.
) ELSE (
    echo Erreur lors de la copie.
)
