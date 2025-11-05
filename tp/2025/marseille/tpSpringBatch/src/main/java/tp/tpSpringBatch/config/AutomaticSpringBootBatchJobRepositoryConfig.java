package tp.tpSpringBatch.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

public class AutomaticSpringBootBatchJobRepositoryConfig {

    @Bean
    @Qualifier("jobRepositoryDb")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties jobRepositoryDbDataSourceProperties() {
        return new DataSourceProperties();
    }

    //NB: cette configuration explicite du "datasource" principal (@Primary)
    // à utiliser par springBatch et springBoot (@BatchDataSource) n'est utile
    // que pour préciser la configuration "Datasource" prioritaire (sans conflit avec les autres)
    @Primary
    @BatchDataSource
    @Bean(value = "dataSource")
    public DataSource datasource(@Qualifier("jobRepositoryDb") DataSourceProperties dbDataSourceProperties) {
        return dbDataSourceProperties.initializeDataSourceBuilder().build();
    }
}
