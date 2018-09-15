@ECHO OFF
TITLE dso_1-sikuli-idea
SET START_DIR=D:\sikuli\workspace\dso_1-sikuli-idea\dist

cd %START_DIR%
REM del *.jar

start sikuliStandby.cmd

xcopy /Y D:\sikuli\workspace\dso_1.sikuli D:\sikuli\workspace\dso_1-sikuli-idea\dist\dso_1.sikuli

ECHO Baue Jar
cd ..
xcopy /Y bins dist\bins
call mvn -o clean package -DskipTests=true

REM ls && pwd && pause
ECHO Kopiere Jar
cd target
xcopy /Y *.jar ..\dist

cd ../dist
REM ls && pwd && pause
for %%i in (*-jar-with-dependencies.jar) DO (
  java -cp ".;./;./dso_1.sikuli" -jar %%i firstDailyRun
  
  for /L %%k in (1, 1, 5) DO (
    echo #%%k Loop
    java -cp ".;./;./dso_1.sikuli" -jar %%i preventScreensaver
    sleep 120
  )
  
  java -cp ".;./;./dso_1.sikuli" -jar %%i secondDailyRun
  
  REM java -jar %%i standby
)

cd %START_DIR%
REM java -jar dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar secondDailyRun

shutdown -a
sleep 1
shutdown /h /f

SET START_DIR=