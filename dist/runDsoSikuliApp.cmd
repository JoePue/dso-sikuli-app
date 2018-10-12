@ECHO OFF
TITLE dso_1-sikuli-idea
SET START_DIR=D:\dev-tools\sikuli\workspace\dso-sikuli-app-idea\dist

cd %START_DIR%

if "%1" EQU "run" GOTO RUN
start sikuliStandby.cmd
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
  
  for /L %%k in (1, 1, 6) DO (
    echo #%%k Loop
    java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i preventScreensaver
    sleep 120
  )
  
  java -cp ".;./;./dso-sikuli-app.sikuli" -jar %%i secondDailyRun
)

cd %START_DIR%
REM java -jar dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar secondDailyRun

shutdown -a
ECHO 10 Sekunden bis Standby
sleep 10
shutdown /h /f

SET START_DIR=