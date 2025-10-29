package tp.jpaSpringBatch.config;

import javax.sql.DataSource;

import com.atomikos.spring.AtomikosDataSourceBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import tp.jpaSpringBatch.config.props.MyXaDataSourceProperties;


@Configuration
public class AutomaticSpringBootBatchJobRepositoryConfig {

    //NOUVELLE VERSION AVEC JTA/XA/Atomikos:

    @Bean @Qualifier("jobRepositoryDb")
    @ConfigurationProperties("spring.datasource")
    public MyXaDataSourceProperties jobRepositoryDbDataSourceProperties() {
        return new MyXaDataSourceProperties();
    }

    //NB: "batchDataSource" name is less important than @Primary
    @Bean(name = {"batchDataSource","dataSource"})
    @Primary
    public DataSource batchDataSource(
            @Qualifier("jobRepositoryDb") MyXaDataSourceProperties myXaDataSourceProperties
    ) {
        //System.out.println("*** batchDataSource() - myXaDataSourceProperties = " + myXaDataSourceProperties);
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        BeanUtils.copyProperties(myXaDataSourceProperties,ds);
        return ds;
    }





	/*

	//ANCIENNE VERSION SANS JTA/XA/Atomikos:

	 @Bean @Qualifier("jobRepositoryDb")
	 @ConfigurationProperties("spring.datasource")
	 //@ConfigurationProperties("spring.batch.datasource") //possible mais moins classique
	 public DataSourceProperties jobRepositoryDbDataSourceProperties() {
	     return new DataSourceProperties();
	 }
		
	//NB: cette configuration explicite du "datasource" principal (@Primary) 
	 //   à utiliser par springBatch et springBoot (@BatchDataSource)
	 // n'est utile que pour préciser la configuration "Datasource" prioritaire (sans conflit avec les autres)
	 @Primary
	 @BatchDataSource
	 @Bean(value = "dataSource")
	    public DataSource datasource(@Qualifier("jobRepositoryDb") DataSourceProperties dbDataSourceProperties) {
		  return dbDataSourceProperties
			      .initializeDataSourceBuilder()
			      .build();
	    }
     

  */
  
}
