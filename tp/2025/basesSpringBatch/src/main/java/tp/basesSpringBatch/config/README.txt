La classe AutomaticSpringBootBatchJobRepositoryConfig n'est pas toujours nécessaire dans une application Spring Boot utilisant Spring Batch.
========
Cas où c'est nécessaire :
   - datasource "primary" pour le fonctionnement interne de Spring Batch (jobrepository)
    - datasource "secondary" pour les traitements métiers

=========
Cas où ce n'est pas nécessaire :
   - une seule et même datasource dans l'application (à la fois utilisée par Spring Batch pour le jobrepository et pour les traitements métiers)
   - appli SpringBatch ne manipulant que des fichiers (ex: csv, xml, ...).