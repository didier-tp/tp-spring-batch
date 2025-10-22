package tp.basesSpringBatch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.basesSpringBatch.model.Product;

@Configuration
public class MyConsoleProductWriterConfig {

	  @Bean @Qualifier("console")
	  public ItemWriter<Product> productConsoleItemWriter() {		  
		  return new SimpleObjectWriter<Product>();
		}
}
