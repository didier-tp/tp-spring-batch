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

import tp.tpSpringBatch.model.IncreaseForCategory;
import tp.tpSpringBatch.model.Product;



@Configuration
@Profile("!xmlJobConfig")
public class MyCsvFileIncreaseForCategoryReaderConfig { 
	
	public static final Logger logger = LoggerFactory.getLogger(MyCsvFileIncreaseForCategoryReaderConfig.class);
	
	//with FlatFileItemReaderBuilder
	  @Bean @Qualifier("csv")
	  @JobScope
	  public FlatFileItemReader<IncreaseForCategory> increaseForCategoryCsvFileReader(
			  @Value("#{jobParameters['inputFilePath']}") String inputFilePath
			  ) {
		Resource inputCsvResource = new FileSystemResource(inputFilePath);
		return new FlatFileItemReaderBuilder<IncreaseForCategory>()
				.name("increaseForCategoryCsvFileReader")
				.resource(inputCsvResource)
				.linesToSkip(1)
				.delimited()
				.delimiter(";")
				.names("category","increaseRatePct")
				.targetType(IncreaseForCategory.class)
				.build();
	  }

}
