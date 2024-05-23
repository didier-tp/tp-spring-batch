package tp.tpSpringBatch.writer.java;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.model.Product;
import tp.tpSpringBatch.writer.custom.SimpleObjectWriter;

@Configuration
@Profile("!xmlJobConfig")
public class MyConsoleProductWriterConfig {

	  @Bean @Qualifier("console")
	  public ItemWriter<Product> productConsoleItemWriter() {		  
		  return new SimpleObjectWriter<Product>();
		}
}
