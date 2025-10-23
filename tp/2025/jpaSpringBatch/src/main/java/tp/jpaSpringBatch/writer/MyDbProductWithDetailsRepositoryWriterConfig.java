package tp.jpaSpringBatch.writer;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.jpaSpringBatch.model.ProductWithDetails;
import tp.jpaSpringBatch.repository.ProductWithDetailsRepository;

@Configuration
public class MyDbProductWithDetailsRepositoryWriterConfig {

    @Autowired
    private ProductWithDetailsRepository productWithDetailsRepository; //spring data jpa repository injection


    @Bean @Qualifier("db-repository")
    public RepositoryItemWriter<ProductWithDetails> repositoryProductWriter() {
        RepositoryItemWriter<ProductWithDetails> writer = new RepositoryItemWriter<>();
        writer.setRepository(productWithDetailsRepository);
        writer.setMethodName("save"); //method called on the repository
        return writer;
    }
}
