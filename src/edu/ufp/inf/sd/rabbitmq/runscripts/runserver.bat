@REM ************************************************************************************
@REM Description: run
@REM Author: Rui S. Moreira
@REM Date: 10/04/2018
@REM ************************************************************************************
@REM Script usage: runsetup <role> (where role should be: producer / consumer)
call setenv chatgui

cd %ABSPATH2CLASSES%
java -cp %CLASSPATH% %JAVAPACKAGEROLE%.%SERVER_CLASS_PREFIX% %BROKER_HOST% %BROKER_PORT% %BROKER_EXCHANGE%

cd %ABSPATH2SRC%/%JAVASCRIPTSPATH%