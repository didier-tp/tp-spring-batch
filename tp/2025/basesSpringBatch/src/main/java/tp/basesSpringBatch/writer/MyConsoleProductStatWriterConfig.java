package tp.basesSpringBatch.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.basesSpringBatch.model.ProductStat;


@Configuration

public class MyConsoleProductStatWriterConfig {

	  @Bean @Qualifier("console")
	  public ItemWriter<ProductStat> productStatConsoleItemWriter() {		  
		  return new SimpleObjectWriter<ProductStat>();
		}
}
