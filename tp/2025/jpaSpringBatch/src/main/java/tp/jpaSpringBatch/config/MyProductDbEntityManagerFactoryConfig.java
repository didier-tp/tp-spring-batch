package tp.jpaSpringBatch.config;


import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class MyProductDbEntityManagerFactoryConfig {

    @Value("${spring.productdb.jpa.hibernate.dialect}")
    private String productdbHibernateDialect = "org.hibernate.dialect.H2Dialect"; //default value

    //not used but required by default with spring-jpa-starter
    @Value("${spring.jpa.database-platform}")
    private String springJpaDatabasePlatform = "org.hibernate.dialect.H2Dialect"; //default value


    @Bean(name="productEntityManagerFactory")
    @Qualifier("productdb")
    @Primary
    public EntityManagerFactory productEntityManagerFactory(
            @Qualifier("productdb") DataSource dataSource
    )
    {
        LocalContainerEntityManagerFactoryBean emf
                = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("tp.jpaSpringBatch.model");
        emf.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect",this.productdbHibernateDialect);
        emf.setJpaProperties(jpaProperties);
        emf.afterPropertiesSet();
        return emf.getObject();
    }


    // Transaction Manager for JPA or ...
    //@Bean(name = "productTransactionManager")
    @Bean(name = "transactionManager")
    public PlatformTransactionManager productTransactionManager(
            @Qualifier("productEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }



    //not used but required by default with spring-jpa-starter
    @Bean(name="entityManagerFactory")
    //@Primary
    public EntityManagerFactory entityManagerFactory(
            @Qualifier("dataSource") DataSource dataSource
    )
    {
        LocalContainerEntityManagerFactoryBean emf
                = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("tp.jpaSpringBatch.model");
        emf.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.dialect",this.springJpaDatabasePlatform);
        emf.setJpaProperties(jpaProperties);
        emf.afterPropertiesSet();
        return emf.getObject();
    }
/*
    // Transaction Manager for JPA or ...
    @Bean(name = "transactionManager")
    //@Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
*/
}
