package tp.tpSpringBatch.writer;

import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import tp.tpSpringBatch.model.Product;

@Configuration
public class MyJsonFileProductWriterConfig {


	  //@Value("file:data/output/json/products.json") //to write in project root directory
	  //private WritableResource outputJsonResource;

    /*
    Attention, double piege :
    pour que @Value("#{jobParameters['outputFilePath']}") puisse fonctionner ,il faut
       @JobScope ou @StepScope
    ET
      que le type de retour soit ItemStreamWriter<Product>
      (plus précis que ItemWriter<Product> mais compatible par héritage)

    SINON erreur de type :
    org.springframework.batch.item.WriterNotOpenException: Writer must be open before it can be written to
     */
	
	  //avec builder:
	  @Bean @Qualifier("json")
      @JobScope
	  public ItemStreamWriter<Product> productJsonFileItemWriter(
              @Value("#{jobParameters['outputFilePath']}") String outputFilePath
      ) {

          //logger.info("in @Bean productJsonFileItemWriter() , outputFilePath="+outputFilePath);
          WritableResource outputJsonResource = new FileSystemResource(outputFilePath);

		  return new JsonFileItemWriterBuilder<Product>()
				     .name("productJsonFileItemWriter")
	                 .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
	                 .resource(outputJsonResource)
	                 .build();
		}
	  
}
