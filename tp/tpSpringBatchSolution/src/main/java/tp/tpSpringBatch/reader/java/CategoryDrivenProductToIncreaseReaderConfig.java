package tp.tpSpringBatch.reader.java;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.model.IncreaseForCategory;
import tp.tpSpringBatch.model.wrapper.ProductToUpdate;
import tp.tpSpringBatch.reader.custom.CategoryDrivenProductToIncreaseReader;

@Configuration
@Profile("!xmlJobConfig")
public class CategoryDrivenProductToIncreaseReaderConfig {
	
	@Bean
	public ItemReader<ProductToUpdate> categoryDrivenProductToIncreaseReader(
			  FlatFileItemReader<IncreaseForCategory> increaseForCategoryCsvMainDelegateReader,
			  @Qualifier("productdb") DataSource productdbDataSource 
			  ) {
	return new CategoryDrivenProductToIncreaseReader(
			increaseForCategoryCsvMainDelegateReader,
			productdbDataSource);
	}

}
