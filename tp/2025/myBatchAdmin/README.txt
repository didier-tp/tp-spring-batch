application "myBatchAdmin":
-------------------

Cette application utilise la même base de données "jobRepository" qu'une appli SpringBatch
----
entity principale "BatchEssential" avec
   - "title" lié à "nomJob" + "quelques jobParemeters" et pouvant être lancer régulièrement (via scheduler)
   - "jobName"
   - "appName"
   - "appURI" (path or ..., ex: "file:///c:/.../...xxx.jar")
   - "mainJobParameters"
   - "scheduling"
   - ...

ceci stocké dans fichiers data/launch/xyz.batch.json  où xyz est le .title

et
entity annexe "BatchMonitoring" avec
   - "title" (même que MyBatchEssential)
   - "jobName"
   - "lastStartDateTime"
   - "lastJobInstanceId"
   - "lastJobExecutionId"
   - "lastStatus" (COMPLETED or ...)

ceci stocké dans fichiers data/monitoring/xyz.monitoring.json  où xyz est le .title

Le scheduler basé sur Quartz peut régulièrement:
   - lancer des batchs (nouveau process)
   - récupérer le statut des batchs (process en cours, terminés, échoués, etc.)
----
Avec petite api-REST + frontEnd  pour piloter tout ça.