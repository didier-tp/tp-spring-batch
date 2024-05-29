package tp.tpSpringBatch.reader.java;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.db.ProductWithDetailsRowMapper;
import tp.tpSpringBatch.model.ProductWithDetails;

@Configuration
@Profile("!xmlJobConfig")
public class MyDbProductWithDetailsReaderWithPartitionConfig {
	private static final String SELECT_CLAUSE = "SELECT id,main_category,sub_category,label,price,time_stamp,f_color,f_weight,f_size,f_description";

	@Bean
	@Qualifier("db_with_partition")
	@StepScope
	ItemReader<ProductWithDetails> jdbcProductWithDetailsReaderWithPartition(
			@Qualifier("productdb") DataSource productdbDataSource,
			@Value("#{stepExecutionContext[from]}") Integer from,
			@Value("#{stepExecutionContext[to]}") Integer to) 
			throws Exception {

		SqlPagingQueryProviderFactoryBean pagingQueryProviderFactory = new SqlPagingQueryProviderFactoryBean();
		pagingQueryProviderFactory.setDataSource(productdbDataSource);
		pagingQueryProviderFactory.setSelectClause(SELECT_CLAUSE);
		pagingQueryProviderFactory.setFromClause("FROM product_with_details");
		pagingQueryProviderFactory.setWhereClause("WHERE id >= :from AND id <= :to");
		pagingQueryProviderFactory.setSortKey("id");
		PagingQueryProvider pagingQueryProvider = pagingQueryProviderFactory.getObject();

		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("from", from);
		parameterValues.put("to", to);

		return new JdbcPagingItemReaderBuilder<ProductWithDetails>()
				.name("jdbcProductWithDetailsReaderWithPartition")
				.dataSource(productdbDataSource)
				.queryProvider(pagingQueryProvider)
				.parameterValues(parameterValues).pageSize(5)
				.rowMapper(new ProductWithDetailsRowMapper()).build();

	}
}
