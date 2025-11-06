1) vérifier dans pom.xml la présence de 
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
2) si Tp reader/writer en version Jpa ou Repository , ajouter ceci dans pom.xml:
                  <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
3) dans application.properties ajouter spring.profiles.default=postgresql
 et ajouter le fichier application-postgresql.properties ayant un contenu proche de ceci:

spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/myjobrepositorydb
spring.datasource.username=postgres
spring.datasource.password=root


spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

#secondary DataBases for some Jobs:
spring.productdb.datasource.url=jdbc:postgresql://localhost:5432/productdb
spring.productdb.datasource.driverClassName=org.postgresql.Driver
spring.productdb.datasource.username=postgres
spring.productdb.datasource.password=root

spring.productdb.jpa.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

4)selon le mode d'accès prévu à la base de données ( "jdbc" , "jpa" ou "repository" ), recopier et si besoin ajuster
  une, deux ou trois des classes en ...Product...Config.java du package tp.tpSpringBatch.config 
  (à partir du référentiel https://github.com/didier-tp/tp-spring-batch , partie tp/2025/Marseille/tpSpringBatch)

5) vérifier la présence des classes ProductFeatures et ProductWithDetails dans tp.tpSpringBatch.model et si besoin recopier les classes manquantes

6) ajouter si besoin des annotations jpa dans les classes de données selon l'exemple tp/2025/jpaSpringBatch
7) coder un reader de ProductWithDetails en s'appuyant sur jdbc ou jpa ou repository
   (en utilisant JdbcCursorItemReaderBuilder ou JpaPagingItemReader ou RepositoryItemReader)
   avec par exemple @Qualifier("db-jdbc") ou @Qualifier("db-jpa") ou @Qualifier("db-repository") .
8) coder un job productWithDetailsFromDbToConsoleOuAutre
   et lancer le .
9) ... 