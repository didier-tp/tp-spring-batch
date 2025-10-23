package tp.jpaSpringBatch.writer;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.jpaSpringBatch.model.ProductWithDetails;

@Configuration
public class MyDbProductWithDetailsJpaWriterConfig {

    @Bean @Qualifier("db-jpa")
    public JpaItemWriter<ProductWithDetails> productJpaWriter(
         @Qualifier("productdb") EntityManagerFactory entityManagerFactory
       ) {
        JpaItemWriter<ProductWithDetails> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
