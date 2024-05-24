package tp.tpSpringBatch.writer.java;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.model.ProductStat;
import tp.tpSpringBatch.writer.custom.SimpleObjectWriter;

@Configuration
@Profile("!xmlJobConfig")
public class MyConsoleProductStatWriterConfig {

	  @Bean @Qualifier("console")
	  public ItemWriter<ProductStat> productStatConsoleItemWriter() {		  
		  return new SimpleObjectWriter<ProductStat>();
		}
}
