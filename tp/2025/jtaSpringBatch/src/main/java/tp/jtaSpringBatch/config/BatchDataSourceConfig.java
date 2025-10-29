package tp.jtaSpringBatch.config;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import tp.jtaSpringBatch.config.props.MyXaDataSourceProperties;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
public class BatchDataSourceConfig {


    @Bean @Qualifier("jobRepositoryDb")
    @ConfigurationProperties("spring.datasource")
    public MyXaDataSourceProperties jobRepositoryDbDataSourceProperties() {
        return new MyXaDataSourceProperties();
    }

    //NB: "batchDataSource" name is less important than @Primary
    @Bean(name = "batchDataSource")
    @Primary
    public DataSource batchDataSource(
            @Qualifier("jobRepositoryDb") MyXaDataSourceProperties myXaDataSourceProperties
            ) {
        //System.out.println("*** batchDataSource() - myXaDataSourceProperties = " + myXaDataSourceProperties);
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        BeanUtils.copyProperties(myXaDataSourceProperties,ds);
        return ds;
    }


}

/*
old basic code:
@Bean(name = "batchDataSource")
    @Primary
    public DataSource batchDataSource() {

        Properties props = new Properties();
        //props.setProperty("url", "jdbc:h2:mem:batchdb;DB_CLOSE_DELAY=-1"); //ok

        props.setProperty("url", "jdbc:h2:~/myjobrepositorydb"); ok
        props.setProperty("user", "sa");
        props.setProperty("password", "");
        //OR

        props.setProperty("url","jdbc:mariadb://localhost:3306/myjobrepositorymariadb?createDatabaseIfNotExist=true");
        props.setProperty("user", "root");
        props.setProperty("password", "root");

AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName("batchDB");
//ds.setXaDataSourceClassName("org.h2.jdbcx.JdbcDataSource"); // OK with H2
//ds.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource"); //xa/atomikos bug with org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer.getNextKey
        ds.setXaDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource"); // no bug with mariadb driver (need to rebuild jobRepository db)
        ds.setXaProperties(props);
        ds.setPoolSize(5);
        return ds;
    }
 */