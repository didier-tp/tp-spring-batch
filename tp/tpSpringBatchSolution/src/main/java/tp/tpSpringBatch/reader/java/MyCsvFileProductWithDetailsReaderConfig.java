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

import tp.tpSpringBatch.model.ProductWithDetails;

@Configuration
@Profile("!xmlJobConfig")
public class MyCsvFileProductWithDetailsReaderConfig { 
	
	public static final Logger logger = LoggerFactory.getLogger(MyCsvFileProductWithDetailsReaderConfig.class);
	
	//V1 (without jobParameter):
	//@Value("file:data/input/csv/newDetailsProducts.csv") //to read in project root directory
	  //NB: by default @Value(path) is @Value("classpath:path) //to read in src/main/resource or other classpath part
	//  private Resource inputCsvResource;

	
	//@Value("file:data/input/csv/newProductsWithOrWithoutErrors.csv") //to read in project root directory
	 // private Resource inputCsvWithErrorsResource;

	
	//V2 with jobParameters
	  @Bean @Qualifier("csv")
	  @JobScope
	  public FlatFileItemReader<ProductWithDetails> productWithDetailsCsvFileReader(
			  @Value("#{jobParameters['inputFilePath']}") String inputFilePath
			  ) {
		Resource inputCsvResource = new FileSystemResource(inputFilePath);
		var specificComplexLineMapper = new ProductWithDetailsLineMapper();
	
		return new FlatFileItemReaderBuilder<ProductWithDetails>()
				.name("personCsvFileReader")
				.resource(inputCsvResource)
				.linesToSkip(1)
				.lineMapper(specificComplexLineMapper)
				.build();
	  }
	  
}
