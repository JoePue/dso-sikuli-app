@ECHO OFF
TITLE dso_1-sikuli-idea
CLS
SET START_DIR=D:\sikuli\workspace\dso_1-sikuli-idea\dist

REM Loop Through Arguments Passed To Batch Script
:argumentsLoop
if "%1" NEQ "" (
  @echo Argument: %1
  if "%1" EQU "build" SET buildFlag=true
  if "%1" EQU "standby" SET standbyFlag=true
)
shift
if not "%~1" == "" goto argumentsLoop

if "%buildFlag%" EQU "true" ECHO buildFlag: %buildFlag%
if "%standbyFlag%" EQU "true" ECHO standbyFlag: %standbyFlag%

SET START_DIR=