set PROJECT_ROOT=..\..\..
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%/bin;%PATH%
REM java -version
set JOB_NAME=myHelloWorldJob
set MAIN_CLASS=tp.basesSpringBatch.BasesSpringBatchApplication
set CP=target/basesSpringBatch.jar
cd /d %~dp0/%PROJECT_ROOT%
java  -jar %CP% %JOB_NAME%
pause