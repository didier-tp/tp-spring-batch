package tp.jtaSpringBatch.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.jtaSpringBatch.model.ProductWithDetails;
import tp.jtaSpringBatch.reader.custom.ProductWithDetailsGeneratorReader;


@Configuration
public class MyCustomProductWithDetailsGeneratorReaderConfig {
	
	  @Bean @StepScope @Qualifier("fromMyGeneratorProductWithDetailsReader")
	  public ItemReader<ProductWithDetails> fromMyGeneratorProductWithDetailsReader(
			  @Value("#{jobParameters[dataSetSize]}") Long dataSetSize
			  ) {
		  if(dataSetSize==null)
			  dataSetSize=100L;
		  return new ProductWithDetailsGeneratorReader(dataSetSize);
	
	  }

}
