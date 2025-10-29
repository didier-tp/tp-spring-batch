package tp.jpaSpringBatch.reader;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.jpaSpringBatch.model.ProductWithDetails;

/*
NB: ce Reader est basé sur JPA/Hibernate.JPQL (JpaPagingItemReader)
     et non sur Spring Data JPA (RepositoryItemReader)
     -----
     La variante RepositoryItemReader (avec SpringData Jpa) est dans le fichier
     MyDbProductWithDetailsRepositoryReaderConfig.java
 */

@Configuration
public class MyDbProductWithDetailsJpaReaderConfig {
	private static final String SELECT_QUERY = "SELECT p FROM ProductWithDetails p"	;
	
	 @Bean @Qualifier("db-jpa")
	  ItemReader<ProductWithDetails> jpaProductWithDetailsReader(
              @Qualifier("productdb") EntityManagerFactory entityManagerFactory
          ) {
         JpaPagingItemReader<ProductWithDetails> jpaReader = new JpaPagingItemReader<>();
         jpaReader.setEntityManagerFactory(entityManagerFactory);
         jpaReader.setQueryString(SELECT_QUERY);//JPQL syntax
         jpaReader.setPageSize(3);
         return jpaReader;
		}
}
