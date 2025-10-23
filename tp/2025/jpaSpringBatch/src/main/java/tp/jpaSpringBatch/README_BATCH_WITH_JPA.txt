
SpringBatch with JPA/hibernate (entityManager):
===============================

JpaPagingItemReader/JpaCursorItemReader et JpaItemWriter

https://docs.spring.io/spring-batch/docs/current/api/org/springframework/batch/item/database/JpaPagingItemReader.html
https://www.geeksforgeeks.org/springboot/spring-boot-batch-processing-using-spring-data-jpa-to-csv-file/

https://www.baeldung.com/spring-batch-one-reader-multiple-processors-writers


SpringBatch with Spring_Data_JPA:
================================

RepositoryItemReader et RepositoryItemWriter
https://reintech.io/blog/batch-processing-spring-data-jpa-spring-batch

https://medium.com/@shashibheemanapally/using-a-jparepository-to-read-and-write-records-in-a-database-using-spring-batch-fb8aeb40ec57
https://medium.com/@shashibheemanapally/using-a-crudrepository-to-read-records-in-spring-batch-repositoryitemreader-51eca4361e9b

===============================
Attention (à peufiner au sein de ce projet):
public static final String DB_ACCESS_TYPE = "db-jpa"; //"db-jpa" or "db-repository"
au sein de certains jobs de manière à switcher entre les 2 approches.

Configuration fragile:
- automatismes de spring-boot-starter-batch et de spring-boot-starter-data-jpa (sans conflits/incohérences ?)
 --> quels entityManagerFactory , quels transactionManager sont utilisés par défaut ?
 --> deux bases de données (jobRepository et productDB) avec deux configurations JPA différentes
 La configuration de tp.jpaSpringBatch.config est fonctionnelle mais certainement améliorable !!!!