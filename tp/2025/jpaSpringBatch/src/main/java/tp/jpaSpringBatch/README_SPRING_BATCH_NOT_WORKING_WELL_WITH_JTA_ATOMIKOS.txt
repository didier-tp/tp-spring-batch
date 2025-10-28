Pour obtenir une bonne transaction globale avec SpringBatch (job repository) et une autre base de données,
on serait tenter d'utiliser JTA et Atomikos mais malheureusement SpringBatch ne semble pas bien fonctionner avec JTA/Atomikos.
===
* problèmes d'incompatibilité entre SpringBoot3 et JTA/Atomikos
================
         <dependency>
            <groupId>com.atomikos</groupId>
            <artifactId>transactions-spring-boot3-starter</artifactId>
            <version>6.0.0</version>
        </dependency>

        <!-- NB: com.atomikos:transactions-spring-boot3:6.0.x compatible with Spring Boot 3.x and Java 17+
       -->

       et
       @EnableAutoConfiguration(exclude = {com.atomikos.spring.AtomikosAutoConfiguration.class}) //because com.atomikos/transactions-spring-boot3/6.0.0. incompatible with spring boot >= 3.4
       et

       package tp.jpaSpringBatch.config;

       import com.atomikos.icatch.config.UserTransactionService;
       import com.atomikos.icatch.config.UserTransactionServiceImp;
       import com.atomikos.icatch.jta.UserTransactionManager;
       import com.atomikos.spring.*;
       import org.springframework.beans.factory.ObjectProvider;
       import org.springframework.boot.autoconfigure.AutoConfigureBefore;
       import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
       import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
       import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
       import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
       import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
       import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
       import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
       import org.springframework.boot.context.properties.EnableConfigurationProperties;
       import org.springframework.boot.jdbc.XADataSourceWrapper;
       import org.springframework.context.annotation.Bean;
       import org.springframework.context.annotation.Configuration;
       import org.springframework.transaction.jta.JtaTransactionManager;
       import jakarta.transaction.UserTransaction;
       import java.util.Properties;

       @Configuration
       @EnableConfigurationProperties({SpringJtaAtomikosProperties.class, AtomikosProperties.class})
       @ConditionalOnClass({JtaTransactionManager.class, UserTransactionManager.class})
       //@EnableAutoConfiguration(exclude = {com.atomikos.spring.AtomikosAutoConfiguration.class}) //because incompatible with spring boot >= 3.4
       public class MyTransactionConfig {

           @Bean( initMethod = "init",   destroyMethod = "shutdownWait"  )
           UserTransactionServiceImp userTransactionService(SpringJtaAtomikosProperties springJtaAtomikosProperties, AtomikosProperties atomikosProperties) {
               Properties properties = new Properties();
               properties.putAll(springJtaAtomikosProperties.asProperties());
               properties.putAll(atomikosProperties.asProperties());
               return new UserTransactionServiceImp(properties);
           }

           @Bean(initMethod = "init", destroyMethod = "close" )
           UserTransactionManager atomikosTransactionManager(UserTransactionService userTransactionService) throws Exception {
               UserTransactionManager manager = new UserTransactionManager();
               manager.setStartupTransactionService(false);
               manager.setForceShutdown(true);
               return manager;
           }

           @Bean
           @ConditionalOnMissingBean({XADataSourceWrapper.class})
           AtomikosXADataSourceWrapper xaDataSourceWrapper() {
               return new AtomikosXADataSourceWrapper();
           }

           @Bean
           @ConditionalOnMissingBean
           static AtomikosDependsOnBeanFactoryPostProcessor atomikosDependsOnBeanFactoryPostProcessor() {
               return new AtomikosDependsOnBeanFactoryPostProcessor();
           }

           @Bean
           JtaTransactionManager transactionManager(UserTransaction userTransaction, jakarta.transaction.TransactionManager transactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
               JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, transactionManager);
              // transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(jtaTransactionManager));
               jtaTransactionManager.setAllowCustomIsolationLevels(true);
               return jtaTransactionManager;
           }

       }

       et


@Configuration
public class MyProductDbDataSourceConfig {

    @Value("${spring.productdb.datasource.xa-data-source-class-name}") //ex: com.mysql.cj.jdbc.MysqlXADataSource
    private String xaDataSourceClassName;

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
	public DataSource productdbDataSource(@Qualifier("productdb") DataSourceProperties productdbDataSourceProperties)
                          throws Exception {
	   /* return productdbDataSourceProperties
	      .initializeDataSourceBuilder()
	      .build();*/
        //Atomikos DataSource (XA):

        Class <DataSource> xaDataSourceClass = (Class<DataSource>)
                Class.forName(this.xaDataSourceClassName);

        XADataSource xaDs = (XADataSource)
                productdbDataSourceProperties
                        .initializeDataSourceBuilder()
                        .type(xaDataSourceClass)
                        .build();

        AtomikosDataSourceBean atomikosXaDataSource = new AtomikosDataSourceBean();
        atomikosXaDataSource.setXaDataSource(xaDs);
        atomikosXaDataSource.setUniqueResourceName("xa_job_productdb");
        atomikosXaDataSource.setMaxPoolSize(30);
        return atomikosXaDataSource;
	}



}

et

	 @Primary
	 @BatchDataSource
	 @Bean(value = "dataSource")
	    public DataSource datasource(@Qualifier("jobRepositoryDb") DataSourceProperties dbDataSourceProperties)
                throws Exception {
		  /*return dbDataSourceProperties
			      .initializeDataSourceBuilder()
			      .build();*/

         //Atomikos DataSource (XA):

         Class <DataSource> xaDataSourceClass = (Class<DataSource>)
                             Class.forName(this.xaDataSourceClassName);

          XADataSource xaDs = (XADataSource)
          dbDataSourceProperties
                    .initializeDataSourceBuilder()
                    .type(xaDataSourceClass)
                    .build();

         AtomikosDataSourceBean atomikosXaDataSource = new AtomikosDataSourceBean();
         atomikosXaDataSource.setXaDataSource(xaDs);
         atomikosXaDataSource.setUniqueResourceName("xa_job_repository");
         atomikosXaDataSource.setMaxPoolSize(30);
         return atomikosXaDataSource;

	    }

et
spring.productdb.datasource.xa-data-source-class-name=com.mysql.cj.jdbc.MysqlXADataSource

spring.productdb.jpa.hibernate.dialect=org.hibernate.dialect.MySQLDialect

jta.enabled=true
jta.atomikos.properties.max-timeout=300000
jta.atomikos.properties.default-jta-timeout=60000

jta.atomikos.datasource.borrow-connection-timeout=30
jta.atomikos.datasource.login-timeout=30
jta.atomikos.datasource.maintenance-interval=60
jta.atomikos.datasource.max-idle-time=60

logging.level.com.atomikos=DEBUG
logging.level.org.springframework.transaction=DEBUG
logging.level.org.springframework.batch=DEBUG
logging.level.org.springframework.jdbc=DEBUG

* problèmes comportemental (Table 'batch_job_instance' already exists)

* problème technique suivant:
org.springframework.dao.DataAccessResourceFailureException: Unable to commit new sequence value changes for BATCH_JOB_SEQ

	at org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer.getNextKey(MySQLMaxValueIncrementer.java:183)
