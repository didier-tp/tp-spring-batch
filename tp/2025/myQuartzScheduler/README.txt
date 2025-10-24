Si cette application utilise la même base de données "jobRepository" qu'une appli SpringBatch
Le scheduler basé sur Quartz peut régulièrement:
   - lancer des batchs (nouveau process)
   - récupérer le statut des batchs (process en cours, terminés, échoués, etc.)
   - ...
 Et on peut même imaginer une petite api-REST + frontEnd  pour piloter tout ça.