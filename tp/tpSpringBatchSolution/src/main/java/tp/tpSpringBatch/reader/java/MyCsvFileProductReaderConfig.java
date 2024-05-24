package tp.tpSpringBatch.reader.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import tp.tpSpringBatch.model.Product;



@Configuration
@Profile("!xmlJobConfig")
public class MyCsvFileProductReaderConfig { 
	
	public static final Logger logger = LoggerFactory.getLogger(MyCsvFileProductReaderConfig.class);
	
	//@Value("file:data/input/csv/products.csv") //to read in project root directory
	 // private Resource inputCsvResource;

	
	//with FlatFileItemReaderBuilder
	  @Bean @Qualifier("csv")
	  @JobScope
	  public FlatFileItemReader<Product> productCsvFileReader(
			  @Value("#{jobParameters['inputFilePath']}") String inputFilePath
			  ) {
		Resource inputCsvResource = new FileSystemResource(inputFilePath);
		return new FlatFileItemReaderBuilder<Product>()
				.name("productCsvFileReader")
				.resource(inputCsvResource)
				.linesToSkip(1)
				.delimited()
				.delimiter(";")
				.names("id","main_category", "label", "price", "time_stamp" , "features")
				.targetType(Product.class)
				.build();
	  }

}
