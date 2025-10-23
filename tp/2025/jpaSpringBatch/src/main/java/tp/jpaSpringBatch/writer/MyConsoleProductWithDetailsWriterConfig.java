package tp.jpaSpringBatch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.jpaSpringBatch.model.ProductWithDetails;


@Configuration
public class MyConsoleProductWithDetailsWriterConfig {

	  @Bean @Qualifier("console")
	  public ItemWriter<ProductWithDetails> productWithDetailsConsoleItemWriter() {		  
		  return new SimpleObjectWriter<ProductWithDetails>();
		}
}
