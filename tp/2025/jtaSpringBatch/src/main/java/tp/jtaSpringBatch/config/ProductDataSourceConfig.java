package tp.jtaSpringBatch.config;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.jtaSpringBatch.config.props.MyXaDataSourceProperties;

import javax.sql.DataSource;


@Configuration
public class ProductDataSourceConfig {

    @Bean @Qualifier("productdb")
    @ConfigurationProperties("spring.productdb.datasource")
    public MyXaDataSourceProperties productDbDataSourceProperties() {
        return new MyXaDataSourceProperties();
    }

    @Bean(name = "productDataSource")
    @Qualifier("productdb")
    public DataSource productDataSource(
            @Qualifier("productdb") MyXaDataSourceProperties myXaDataSourceProperties
    ) {
        //System.out.println("*** productDataSource() - myXaDataSourceProperties = " + myXaDataSourceProperties);
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        BeanUtils.copyProperties(myXaDataSourceProperties,ds);
        return ds;
    }

}