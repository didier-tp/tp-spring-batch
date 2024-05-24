package tp.tpSpringBatch.writer.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import tp.tpSpringBatch.model.Product;

@Configuration
@Profile("!xmlJobConfig")
public class MyXmlFileProductWriterConfig { 
	
	public static final Logger logger = LoggerFactory.getLogger(MyXmlFileProductWriterConfig.class);
	
	 // @Value("file:data/output/xml/products.xml") //to write in project root directory
	 //  private WritableResource outputXmlResource;

	  //avec builder:
	  @Bean(destroyMethod="") @Qualifier("xml")
	  @StepScope
	   ItemStreamWriter<Product>  productXmlFileItemWriter(
			 @Value("#{jobParameters['outputFilePath']}") String outputFilePath
			  ) {
		  
		  //logger.info("in @Bean productXmlFileItemWriter() , outputFilePath="+outputFilePath);
		  WritableResource outputXmlResource = new FileSystemResource(outputFilePath);
		  			  
		  var productXmlMarshaller = new Jaxb2Marshaller();
		  productXmlMarshaller.setClassesToBeBound(new Class[] { Product.class });
		  
		  return new StaxEventItemWriterBuilder<Product>()
		  .name("productXmlFileItemWriter")
		  .marshaller(productXmlMarshaller)
	      .rootTagName("products")
	      .resource(outputXmlResource)
	      .build();
		}
	  
	 
	
}
