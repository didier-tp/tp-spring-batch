package tp.tpSpringBatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

/*
 * old classes 
 * ResourcelessTransactionManager , MapJobRepositoryFactoryBean 
 * and SimpleJobLauncher are now DEPRECATED.
 * Minimum Batch config = using a in-memory jdbc database like h2
 */


@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource" ,
                       transactionManagerRef = "batchTransactionManager")
public class JobRepositoryBatchConfig {
	
	/*
	 NB: in application.properties
	 NOT spring.datasource.url=jdbc:h2:mem:jobRepositoryDb
	 BUT spring.batch.datasource.url=jdbc:h2:~/jobRepositoryDb
	 and spring.batch.datasource.username=sa
         spring.batch.datasource.password=
	 */
	@Bean @Qualifier("batch")
    @ConfigurationProperties("spring.batch.datasource")
    public DataSourceProperties batchDataSourceProperties() {
        return new DataSourceProperties();
    }
	
	@Bean(name="batchDataSource") @Qualifier("batch")
	@BatchDataSource
	public DataSource batchDataSource(@Qualifier("batch") DataSourceProperties batchDataSourceProperties) {
	    return batchDataSourceProperties
	      .initializeDataSourceBuilder()
	      .build();
	}

  @Bean(name={"batchTransactionManager"}) @Qualifier("batch")
  public PlatformTransactionManager batchTransactionManager(@Qualifier("batch") DataSource batchDataSource) {
    return new DataSourceTransactionManager(batchDataSource);
  }
  
  //@Bean @Qualifier("batch")
  public DataSourceInitializer databasePopulator(
		  @Qualifier("batch") DataSource  batchDataSource) {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    //populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-drop-h2.sql"));
    populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-h2.sql"));
    populator.setContinueOnError(false);
    populator.setIgnoreFailedDrops(false);
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(batchDataSource);
    dataSourceInitializer.setDatabasePopulator(populator);
    return dataSourceInitializer;
  }
  
  @Bean(name = "jobRepository")
  public JobRepository jobRepository(
		  @Qualifier("batch") DataSource  batchDataSource,
		  @Qualifier("batch") PlatformTransactionManager batchTransactionManager) throws Exception {
      JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
      factory.setDataSource(batchDataSource);
      factory.setTransactionManager(batchTransactionManager);
      factory.afterPropertiesSet();
      return factory.getObject();
  }
  
 /*
  //THIS CONFIG IS THE SAME AS THE DEFAULT JobLauncher configuration (in spring-boot-batch) :
  @Bean()
  public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
     TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
     jobLauncher.setJobRepository(jobRepository);
     jobLauncher.afterPropertiesSet();
     return jobLauncher;
  }
  */

}
