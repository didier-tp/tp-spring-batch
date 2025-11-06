package tp.tpSpringBatch.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class MyProductDbDataSourceConfig {

	@Bean
    @Qualifier("productdb")
  @ConfigurationProperties("spring.productdb.datasource")
  public DataSourceProperties productdbDataSourceProperties() {
      return new DataSourceProperties();
  }
	
	@Bean(name="productdbDataSource") @Qualifier("productdb")
    // NOT @Primary !!!
	public DataSource productdbDataSource(@Qualifier("productdb") DataSourceProperties productdbDataSourceProperties) {
	    return productdbDataSourceProperties
	      .initializeDataSourceBuilder()
	      .build();
	}


	
}
