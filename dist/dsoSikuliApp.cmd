@ECHO OFF
SET BATCH_FILE_NAME=%~n0
SET START_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist
SET APP_CONFIG_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist\config
SET JAR_NAME=dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar
SET EXE_CMD=java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% --configDir=%APP_CONFIG_DIR%

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

TITLE %BATCH_FILE_NAME% buildFlag: %buildFlag% hibernateFlag: %hibernateFlag% exitDsoFlag: %exitDsoFlag%

cd %START_DIR%
ECHO START_DIR=%START_DIR%
ECHO hibernateFlag=%hibernateFlag%
ECHO buildFlag=%buildFlag%
ECHO exitDsoFlag=%exitDsoFlag%

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
%EXE_CMD% firstDailyRun
REM if "%exitDsoFlag%" EQU "true" (
REM   java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% exitDso --configDir=%APP_CONFIG_DIR%
REM )
for /L %%k in (1, 1, 6) DO (
  echo #%%k Loop
  %EXE_CMD% preventScreensaver
  if "%%k" EQU "3" (
    beep 3
    %EXE_CMD% buildAllMines
  )
  sleep 120
)

%EXE_CMD% secondDailyRun
REM if "%exitDsoFlag%" EQU "true" (
REM    java -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% exitDso  --configDir=%APP_CONFIG_DIR%
REM )


cd %START_DIR%

if "%exitDsoFlag%" EQU "true" (
  %EXE_CMD% exitDso
  sleep 3
)
if "%hibernateFlag%" EQU "true" (
  shutdown -a
  ECHO 10 Sekunden bis Standby
  sleep 10
  shutdown /h /f
)
:END
SET BATCH_FILE_NAME=
SET START_DIR=
SET hibernateFlag=
SET buildFlag=
SET exitDsoFlag=
beep 1