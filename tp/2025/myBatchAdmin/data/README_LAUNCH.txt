exemple de fichier launch/xyz.batch.json:
----------------------------------------
{
  "title" : "xyz",
  "description" : "read a csv file and write a xml file , outputFilePath is interpret and necessary",
  "jobName" : "fromCsvToXmlJob",
  "appName" : "basesSpringBatch",
  "appURI" : "file:///C:/tp/local-git-didier-tp-repositories/tp-spring-batch/tp/2025/basesSpringBatch/target/basesSpringBatch.jar",
  "mainJobParameters" : {
       "inputFilePath" :  "data/input/csv/products.csv",
        "outputFilePath" :  "data/output/xml/products.xml",
        "enableUpperCase" :  "false"
  },
  "scheduling" : null
}

or "scheduling" : {
       "minutes": "*",
       "hours" : "8-18",
       "daysOfMonth" : "?",
       "months" : "*",
       "daysOfWeek" : "MON-FRI"
     }

daysOfWeek="1-7";
//linux-cron: 0-7 (1=Monday , 5=Friday , 6=Saturday , 0or 7 = Sunday)
//quartz-cron : 1-7 (1 : sunday , 7 : saturday)
//prefered syntax: MON-FRI

NB: this App use  "quartz-cron : 1-7"  , (1 : sunday , 7 : saturday) or SUN-SAT