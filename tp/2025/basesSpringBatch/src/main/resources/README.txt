avec une URL "h2 en mémoire" telle que spring.datasource.url=jdbc:h2:mem:myjobrepositorydb,
la base myjobrepositorydb est réinitialisée à chaque démarrage de l'application.
et le job a tendance à s'exécuter à chaque fois (car pas noté comme "COMPLETED" dans la BDD).

Inversement avec une URL "h2 fichier" telle que spring.datasource.url=jdbc:h2:~/myjobrepositorydb,
ou bien une URL mysql ou postgresql ,
Un second démarrage de l'application trouve la BDD existante, et le job ne s'exécute pas (car noté comme "COMPLETED" dans la BDD).
Exemple de message: Step already complete or not restartable, so no action to execute

----
Avec un run explicite , on peut forcer l'exécution du job à chaque démarrage de l'application,
via l'ajout d'un paramètre de job qui change à chaque fois (ex: un timestamp).