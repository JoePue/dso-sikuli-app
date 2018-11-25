@ECHO OFF
TITLE dso_1-sikuli-idea
SET START_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist
SET JAR_NAME=dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar

REM Loop Through Arguments Passed To Batch Script
:argumentsLoop
if "%1" NEQ "" (
  @echo Argument: %1
  if "%1" EQU "build" SET buildFlag=true
  if "%1" EQU "hibernate" SET hibernateFlag=true
)
shift
if not "%~1" == "" goto argumentsLoop

cd %START_DIR%

if "%hibernateFlag%" EQU "true" (
  start sikuliStandby.cmd
)
if "%buildFlag%" NEQ "true" GOTO RUN

del *.jar

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
for %%i in (*-jar-with-dependencies.jar) DO (
  java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i firstDailyRun
  REM if "%hibernateFlag%" EQU "true" (
  REM   java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i exitDso
  REM )
  for /L %%k in (1, 1, 6) DO (
    echo #%%k Loop
    java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i preventScreensaver
    if "%%k" EQU "3" (
      java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i buildAllMines
      beep 3
    )
    sleep 120
  )
  
  java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i secondDailyRun
  REM if "%hibernateFlag%" EQU "true" (
  REM   java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i exitDso
  REM )
)

cd %START_DIR%
REM java -jar dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar secondDailyRun

if "%hibernateFlag%" EQU "true" (
  REM cp ../*.json . && java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% playBraveTailorAdventure
  REM java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i exitDso
  
  shutdown -a
  ECHO 10 Sekunden bis Standby
  sleep 10
  shutdown /h /f
)

SET START_DIR=
SET hibernateFlag=
SET buildFlag=
beep 1