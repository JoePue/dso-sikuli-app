@ECHO OFF
TITLE %~nx0
SET START_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist
SET APP_CONFIG_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist\config
SET JAR_NAME=dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar

REM Loop Through Arguments Passed To Batch Script
:argumentsLoop
if "%1" NEQ "" (
  @echo Argument: %1
  if "%1" EQU "build" SET buildFlag=true
  if "%1" EQU "hibernate" SET hibernateFlag=true
  if "%1" EQU "exitDso" SET exitDsoFlag=true
  
)
shift
if not "%~1" == "" goto argumentsLoop

TITLE DsoSikulieApp buildFlag: %buildFlag% hibernateFlag: %hibernateFlag% exitDso: %exitDso%
cd %START_DIR%
ECHO START_DIR=%START_DIR%
ECHO hibernateFlag=%hibernateFlag%
ECHO buildFlag=%buildFlag%
ECHO exitDsoFlag=%exitDsoFlag%
pause

if "%hibernateFlag%" EQU "true" (
  start sikuliStandby.cmd
)
if "%buildFlag%" NEQ "true" GOTO RUN

REM Kopiere Sikuli-Screenshots
xcopy /Y D:\dev-tools\sikuli\workspace\dso-sikuli-app.sikuli D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist\dso-sikuli-app.sikuli

ECHO Baue Jar
cd ..
xcopy /Y bins dist\bins
call mvn -o clean package -DskipTests=true

REM ls && pwd && pause
ECHO Kopiere Jar
cd target
xcopy /Y *.jar ..\dist

cd ../dist

:RUN
if NOT EXIST "%JAR_NAME%" (
  ECHO Missing Jar: %JAR_NAME%
  GOTO END
)
java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% firstDailyRun --configDir=%APP_CONFIG_DIR%
REM if "%exitDsoFlag%" EQU "true" (
REM   java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% exitDso --configDir=%APP_CONFIG_DIR%
REM )
for /L %%k in (1, 1, 6) DO (
  echo #%%k Loop
  java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% preventScreensaver --configDir=%APP_CONFIG_DIR%
  if "%%k" EQU "3" (
    beep 3
    java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% buildAllMines  --configDir=%APP_CONFIG_DIR%
  )
  sleep 120
)

java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% secondDailyRun --configDir=%APP_CONFIG_DIR%
REM if "%exitDsoFlag%" EQU "true" (
REM    java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% exitDso  --configDir=%APP_CONFIG_DIR%
REM )


cd %START_DIR%

if "%exitDsoFlag%" EQU "true" (
  java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% exitDso --configDir=%APP_CONFIG_DIR%
)
if "%hibernateFlag%" EQU "true" (
  shutdown -a
  ECHO 10 Sekunden bis Standby
  sleep 10
  shutdown /h /f
)
:END
SET START_DIR=
SET hibernateFlag=
SET buildFlag=
SET exitDsoFlag=
beep 1