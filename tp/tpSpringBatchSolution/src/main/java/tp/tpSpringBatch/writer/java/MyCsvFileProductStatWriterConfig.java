package tp.tpSpringBatch.writer.java;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.WritableResource;

import tp.tpSpringBatch.model.ProductStat;

@Configuration
@Profile("!xmlJobConfig")
public class MyCsvFileProductStatWriterConfig {
	  
	  @Value("file:data/output/csv/productStats.csv") //to write in project root directory
	  private WritableResource productStatsCsvResource;
	  
	  @Bean @Qualifier("csv")
	  FlatFileItemWriter<ProductStat> csvFileProductStatWriter() {
		  
		  return new FlatFileItemWriterBuilder<ProductStat>()
				  .name("csvFileProductStatWriter")
				  .resource(productStatsCsvResource)
				  .delimited()
				  .delimiter(";")
				  .names("category","number_of_products", "min_price", "max_price", "avg_price")
				  .headerCallback((writer)-> {writer.write("category;number_of_products;min_price;max_price;avg_price");})
				  .build();
	  }
}
