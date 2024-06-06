Dans pom.xml la dépendance 
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-starter-task</artifactId>
		    <version>3.1.0</version>
		</dependency>
Sert à placer @EnableTask sur la classe principale (là où il y a @SpringBootApplication)
ou bien sur une classe de @Configuration.

Ceci permet normalement une prise en charge de cette application 
via "spring_cloud_dataflow" .

"spring_cloud_dataflow" correspond à un gros écosystème (basé à fond sur "docker") et qui permet
de :
  - déployer des applications sur le cloud
  - de les contrôler à distance (démarrage)
  - de les surveiller à distance (vérif status , ....)
  
  "spring_cloud_dataflow" remplace maintenant l'ancienne application "springBatch-admin" 
  qui n'est plus maintenue depuis de nombreuses années et qui est désormais 
  imcompatible avec la nouvelle structure de la base de données (JobRepository) des versions récentes 
  de springBatch . 