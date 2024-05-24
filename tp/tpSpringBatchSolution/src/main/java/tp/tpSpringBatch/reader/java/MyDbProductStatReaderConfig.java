package tp.tpSpringBatch.reader.java;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.db.ProductStatRowMapper;
import tp.tpSpringBatch.model.ProductStat;

@Configuration
@Profile("!xmlJobConfig")
public class MyDbProductStatReaderConfig {
	private static final String SELECT_QUERY = """
			SELECT main_category as category , count(*) as nb_prod , min(price) as min_price ,
			 max(price) as max_price , avg(price) as avg_price    
			 FROM PRODUCT_WITH_DETAILS GROUP BY main_category""";
	
	 @Bean @Qualifier("db")
	  ItemReader<ProductStat> jdbcProductStatReader(@Qualifier("productdb") DataSource productdbDataSource) {
			 return new JdbcCursorItemReaderBuilder<ProductStat>()
			  .name("jdbcProductStatReader")
			  .dataSource(productdbDataSource)
			  .sql(SELECT_QUERY)
			  .rowMapper(new ProductStatRowMapper())
			  .build();
		}
}
