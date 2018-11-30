@ECHO OFF & setlocal
REM JAR_NAME=dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar
SET JAR_NAME=dso-automation-0.0.2-SNAPSHOT-jar-with-dependencies.jar
SET BATCH_FILE_NAME=%~n0
SET START_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist
SET APP_CONFIG_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\config
SET LOG_PROP_FILE=%APP_CONFIG_DIR%\logging.properties
SET EXE_CMD=java -Djava.util.logging.config.file=%LOG_PROP_FILE% -cp ".;./;./dso-sikuli-app.sikuli" -jar %JAR_NAME% --configDir=%APP_CONFIG_DIR% 

REM Loop Through Arguments Passed To Batch Script
:argumentsLoop
IF "%1" NEQ "" (
  @echo Argument: %1
  IF "%1" EQU "clean" SET cleanFlag=true
  IF "%1" EQU "run" SET runFlag=true
  IF "%1" EQU "build" SET buildFlag=true
  IF "%1" EQU "hibernate" SET hibernateFlag=true
  IF "%1" EQU "exitDso" SET exitDsoFlag=true
  IF "%1" EQU "unset" SET unsetFlag=true
)
shift
IF not "%~1" == "" goto argumentsLoop

TITLE %BATCH_FILE_NAME% buildFlag: %buildFlag% hibernateFlag: %hibernateFlag% exitDsoFlag: %exitDsoFlag%

cd %START_DIR%
ECHO START_DIR=%START_DIR%
ECHO runFlag=%runFlag%
ECHO buildFlag=%buildFlag%
ECHO unsetFlag=%unsetFlag%
ECHO exitDsoFlag=%exitDsoFlag%
ECHO hibernateFlag=%hibernateFlag%
ECHO cleanFlag=%cleanFlag%

IF "%cleanFlag%" EQU "true" (
  cd %START_DIR%
  del %JAR_NAME%
  del %START_DIR%\bins /S /Q
)

IF "%hibernateFlag%" EQU "true" (
  start sikuliStandby.cmd
)
IF "%buildFlag%" EQU "true" (
  cd %START_DIR%
  REM Kopiere Sikuli-Screenshots
  xcopy /Y D:\dev-tools\sikuli\workspace\dso-sikuli-app.sikuli D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist\dso-sikuli-app.sikuli

  ECHO Baue Jar
  cd ..
  xcopy /Y bins dist\bins
  call mvn -o clean package -DskipTests=true

  REM ls && pwd && pause
  ECHO Kopiere Jar
  cd target
  IF NOT EXIST "%JAR_NAME%" (
    ECHO Missing file: %JAR_NAME%
    GOTO END
  )
  xcopy /Y %JAR_NAME% ..\dist

  cd ../dist
)

IF "%runFlag%" EQU "true" (
  cd %START_DIR%
  
  IF NOT EXIST "%JAR_NAME%" (
    ECHO Missing Jar: %JAR_NAME%
    GOTO END
  )
  %EXE_CMD% firstDailyRun

  for /L %%k in (1, 1, 6) DO (
    echo #%%k Loop
    %EXE_CMD% preventScreensaver
    IF "%%k" EQU "3" (
      beep 3
      %EXE_CMD% buildAllMines
    )
    sleep 120
  )

  %EXE_CMD% secondDailyRun
  
  beep 1
)

IF "%exitDsoFlag%" EQU "true" (
  cd %START_DIR%
  %EXE_CMD% exitDso
  sleep 3
)
IF "%hibernateFlag%" EQU "true" (
  shutdown -a
  ECHO 10 Sekunden bis Standby
  sleep 10
  shutdown /h /f
)
:END
:UNSET_FLAGS
SET BATCH_FILE_NAME=
SET START_DIR=
SET hibernateFlag=
SET buildFlag=
SET exitDsoFlag=
SET EXE_CMD=
SET APP_CONFIG_DIR=
SET JAR_NAME=
SET unsetFlag=
SET runFlag=
SET cleanFlag=