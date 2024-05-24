package tp.tpSpringBatch.reader.java;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.db.ProductWithDetailsRowMapper;
import tp.tpSpringBatch.model.ProductWithDetails;

@Configuration
@Profile("!xmlJobConfig")
public class MyDbProductWithDetailsReaderConfig {
	private static final String SELECT_QUERY = "SELECT id,main_category,sub_category,label,price,time_stamp,f_color,f_weight,f_size,f_description FROM product_with_details"	;
	
	 @Bean @Qualifier("db")
	  ItemReader<ProductWithDetails> jdbcProductWithDetailsReader(@Qualifier("productdb") DataSource productdbDataSource) {
			 return new JdbcCursorItemReaderBuilder<ProductWithDetails>()
			  .name("jdbcProductWithDetailsReader")
			  .dataSource(productdbDataSource)
			  .sql(SELECT_QUERY)
			  .rowMapper(new ProductWithDetailsRowMapper())
			  .build();
		}
}
