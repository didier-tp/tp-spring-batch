set PROJECT_ROOT=..\..\..
set JAVA_HOME=C:\Program Files\Java\jdk-21
set PATH=%JAVA_HOME%/bin;%PATH%
REM java -version
set inputFilePath=data/input/csv/products.csv
set outputFilePath=data/output/xml/products_3.xml
set JOB_NAME=fromCsvToXmlJob
set MAIN_CLASS=tp.tpSpringBatch.TpSpringBatchApplication
set CP=target/tpSpringBatch.jar
cd /d %~dp0/%PROJECT_ROOT%
java -DinputFilePath=%inputFilePath% -DoutputFilePath=%outputFilePath% -jar %CP% %JOB_NAME%
pause