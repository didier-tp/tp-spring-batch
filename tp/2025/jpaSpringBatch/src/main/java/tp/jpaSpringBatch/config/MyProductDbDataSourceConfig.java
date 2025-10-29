package tp.jpaSpringBatch.config;

import javax.sql.DataSource;

import com.atomikos.spring.AtomikosDataSourceBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.jpaSpringBatch.config.props.MyXaDataSourceProperties;

@Configuration
public class MyProductDbDataSourceConfig {

    //NOUVELLE VERSION AVEC JTA/XA/Atomikos:

    @Bean @Qualifier("productdb")
    @ConfigurationProperties("spring.productdb.datasource")
    public MyXaDataSourceProperties productDbDataSourceProperties() {
        return new MyXaDataSourceProperties();
    }

    @Bean(name = "productDataSource")
    @Qualifier("productdb") // NOT @Primary !!!
    public DataSource productDataSource(
            @Qualifier("productdb") MyXaDataSourceProperties myXaDataSourceProperties
    ) {
        //System.out.println("*** productDataSource() - myXaDataSourceProperties = " + myXaDataSourceProperties);
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        BeanUtils.copyProperties(myXaDataSourceProperties,ds);
        return ds;
    }


    /*
    //ANCIENNE VERSION SANS JTA/XA/Atomikos:

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
	*/

	
}
