package tp.jpaSpringBatch.reader;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import tp.jpaSpringBatch.model.ProductWithDetails;
import tp.jpaSpringBatch.repository.ProductWithDetailsRepository;

import java.util.Collections;

/*
NB: ce Reader est basé sur sur Spring Data JPA (RepositoryItemReader)
     et non seulement sur JPA/Hibernate.JPQL (JpaPagingItemReader)
     -----
     La variante JPA/Hibernate.JPQL (JpaPagingItemReader) est dans le fichier
     MyDbProductWithDetailsJpaReaderConfig.java
 */

@Configuration
public class MyDbProductWithDetailsRepositoryReaderConfig {

    @Autowired
    private ProductWithDetailsRepository productWithDetailsRepository; //spring data jpa repository injection
	
	 @Bean
     @Qualifier("db-repository")
	  ItemReader<ProductWithDetails> repositoryProductWithDetailsReader() {
         RepositoryItemReader<ProductWithDetails> repositoryReader = new RepositoryItemReader<>();
         repositoryReader.setRepository(productWithDetailsRepository);
         repositoryReader.setMethodName("findAll"); //method called on the repositor
         repositoryReader.setPageSize(3);
         repositoryReader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));//NB: a sort is required
         return repositoryReader;
		}
}
