package tp.jpaSpringBatch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyProductDbDataSourceConfig {

	/*
	 NB: in application.properties
	 NOT spring.datasource.url=jdbc:h2:mem:outputDb
	 BUT spring.productdb.datasource.url=jdbc:h2:~/outputDb
	 and spring.productdb.datasource.driverClassName=org.h2.Driver
	 and spring.productdb.datasource.username=sa
        spring.productdb.datasource.password=
	 */
	@Bean @Qualifier("productdb")
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
