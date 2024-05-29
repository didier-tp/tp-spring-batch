package tp.tpSpringBatch.writer.java;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.model.ProductWithDetails;

@Configuration
@Profile("!xmlJobConfig")
public class MyDbProductWithDetailsWriterConfig {


	private static final String INSERT_QUERY = """
		  INSERT INTO product_with_details (main_category,sub_category,label,price,time_stamp,f_color,f_weight,f_size,f_description)
		  VALUES(:main_category,:sub_category,:label,:price,:time_stamp,:f_color,:f_weight,:f_size,:f_description)""";
	
	private static final String UPDATE_PRICE_QUERY = """
		UPDATE product_with_details SET price = :price , time_stamp = :time_stamp WHERE id = :id """;
	 

	 @Bean @Qualifier("insert_in_db")
	  public JdbcBatchItemWriter<ProductWithDetails> jdbcProductWithDetailsItemWriter(
			  @Qualifier("productdb") DataSource productdbDataSource) {
		 
		 ItemSqlParameterSourceProvider<ProductWithDetails> paramProvider = null;
		 
		 //BeanPropertyItemSqlParameterSourceProvider only in simple case where paramName are the same as bean property names
		 //paramProvider = new BeanPropertyItemSqlParameterSourceProvider<ProductWithDetails>();
		 
		 paramProvider = new MyProductWithDetailsParamProvider();
		 
		 return new JdbcBatchItemWriterBuilder<ProductWithDetails>()
		  .itemSqlParameterSourceProvider(paramProvider)
		  .dataSource(productdbDataSource)
		  .sql(INSERT_QUERY)
		  .build();
	  }
	
	 
	 @Bean @Qualifier("update_price_in_db")
	  public ItemWriter<ProductWithDetails> updatePriceOfProductWithDetailsJdbcItemWriter(@Qualifier("productdb") DataSource productdbDataSource) {
		 return new JdbcBatchItemWriterBuilder<ProductWithDetails>()
				  .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ProductWithDetails>())
				  .dataSource(productdbDataSource)
				  .sql(UPDATE_PRICE_QUERY)
				  .build();
	  }
	 	
}
