@ECHO OFF
TITLE dso_1-sikuli-idea
SET START_DIR=D:\sikuli\workspace\dso_1-sikuli-idea\dist

cd %START_DIR%
REM del *.jar

cd ..
xcopy bins dist\bins /Y
call mvn -o clean package -DskipTests=true

REM ls && pwd && pause
cd target
xcopy *.jar ../dist /Y

cd ../dist
REM ls && pwd && pause
for %%i in (*-jar-with-dependencies.jar) DO (
  java -jar %%i firstDailyRun
  
  for /L %%k in (1, 1, 12) DO (
    echo #%%k Loop
    java -jar %%i preventScreensaver
    sleep 120
  )
  
  java -jar %%i secondDailyRun
  
  REM java -jar %%i standby
)

cd %START_DIR%
REM java -jar dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar secondDailyRun

SET START_DIR=