package tp.tpSpringBatch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import tp.tpSpringBatch.model.ProductWithDetails;
import tp.tpSpringBatch.repository.ProductWithDetailsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
NB: ce Reader est basé sur sur Spring Data JPA (RepositoryItemReader)
     et non seulement sur JPA/Hibernate.JPQL (JpaPagingItemReader)
     -----
     La variante JPA/Hibernate.JPQL (JpaPagingItemReader) est dans le fichier
     MyDbProductWithDetailsJpaReaderConfig.java
 */

@Configuration
public class MyDbProductWithDetailsRepositoryReaderWithPartitionConfig {

    @Autowired
    private ProductWithDetailsRepository productWithDetailsRepository; //spring data jpa repository injection
	
	 @Bean @StepScope
     @Qualifier("db-repository-with-partition")
	  ItemReader<ProductWithDetails> repositoryProductWithDetailsReaderWithPartition(
             @Value("#{stepExecutionContext[from]}") String from,
             @Value("#{stepExecutionContext[to]}") String to
     ) {
         RepositoryItemReader<ProductWithDetails> repositoryReader = new RepositoryItemReader<>();
         repositoryReader.setRepository(productWithDetailsRepository);
         repositoryReader.setMethodName("findFromMinIdToMaxId"); //method called on the repositor
         Integer minId=Integer.parseInt(from);
         Integer maxId=Integer.parseInt(to);
         List<Integer> args = new ArrayList<>();
         args.add(minId); args.add(maxId);
         repositoryReader.setArguments(args);
         repositoryReader.setPageSize(8);
         repositoryReader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));//NB: a sort is required
         return repositoryReader;
		}
}
