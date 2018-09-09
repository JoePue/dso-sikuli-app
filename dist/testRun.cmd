@ECHO OFF
TITLE dso_1-sikuli-idea
CLS
SET START_DIR=D:\sikuli\workspace\dso_1-sikuli-idea\dist

cd %START_DIR%
REM del *.jar

REM xcopy /Y D:\sikuli\workspace\dso_1-sikuli-idea\dist\dso_1.sikuli D:\sikuli\workspace\dso_1-sikuli-idea\dist\dso_1.sikuli

cd ..
xcopy /Y bins dist\bins
REM call mvn -o clean package -DskipTests=true

REM ls && pwd && pause
ECHO Kopiere Jars
cd target
REM xcopy /Y *.jar ..\dist


cd ../dist
for %%i in (*-jar-with-dependencies.jar) DO (
   java -cp ".;./;./dso_1.sikuli" -jar %%i preventScreensaver
)

SET START_DIR=