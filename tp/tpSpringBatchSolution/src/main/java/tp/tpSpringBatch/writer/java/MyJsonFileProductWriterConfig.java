package tp.tpSpringBatch.writer.java;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.WritableResource;

import tp.tpSpringBatch.model.Product;

@Configuration
@Profile("!xmlJobConfig")
public class MyJsonFileProductWriterConfig {


	  @Value("file:data/output/json/products.json") //to write in project root directory
	  private WritableResource outputJsonResource;
	
	  //avec builder:
	  @Bean @Qualifier("json")
	  public ItemWriter<Product> productJsonFileItemWriter() {		  
		  return new JsonFileItemWriterBuilder<Product>()
				     .name("productJsonFileItemWriter")
	                 .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
	                 .resource(outputJsonResource)
	                 .build();
		}
	  
}
