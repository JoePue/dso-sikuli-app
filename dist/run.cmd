@ECHO OFF
SET START_DIR=D:\sikuli\workspace\dso_1-sikuli-idea\dist

cd %START_DIR%
del *.jar

cd ..
xcopy bins dist\bins /Y
REM call  mvn -o clean package -DskipTests=true

cd target
xcopy *.jar ../dist /Y

cd ../dist
for %%i in (*-jar-with-dependencies.jar) DO (
  java -jar %%i firstDailyRun
  
  for /L %%k in (1, 1, 12) DO (
    echo #%%k Loop
    java -jar %%i preventScreensaver
    sleep 120
  )
  
  java -jar %%i secondDailyRun
)

cd %START_DIR%
REM java -jar dso-automation-0.0.1-SNAPSHOT-jar-with-dependencies.jar secondDailyRun