package tp.jpaSpringBatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "tp.jpaSpringBatch.repository", //package of the repositories
        entityManagerFactoryRef = "productEntityManagerFactory"
        //transactionManagerRef = "productTransactionManager" //specify transaction manager
)
public class ProductJpaRepositoryConfig {
}
