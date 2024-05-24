set PROJECT_ROOT=..\..\..
set CP=target\tpSpringBatchSolution-0.0.1-SNAPSHOT.jar
REM set MAIN_CLASS=tp.tpSpringBatch.TpSpringBatchApplication
set JOB_NAME=fromCsvToXmlJob
set inputFilePath=data/input/csv/products.csv
set outputFilePath=data/output/xml/products.xml
REM set enableUpperCase="true"

REM set PATH="C:\Prog\java\eclipse-jee-2023-12\eclipse\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_17.0.9.v20231028-0858\jre\bin"
REM set PATH="C:\Program Files\Java\jdk-17\bin"

cd /d %~dp0/%PROJECT_ROOT%
REM DIR
REM NB: -D options before -cp
REM java  -DinputFilePath=%inputFilePath% -DoutputFilePath=%outputFilePath% -DjobName=%JOB_NAME% -jar %CP%  
java  -DinputFilePath=%inputFilePath% -DoutputFilePath=%outputFilePath% -Dspring.profiles.default=xmlJobConfig -jar %CP% %JOB_NAME%
REM java  -DinputFilePath=%inputFilePath% -DoutputFilePath=%outputFilePath% -jar %CP%  %JOB_NAME%
pause