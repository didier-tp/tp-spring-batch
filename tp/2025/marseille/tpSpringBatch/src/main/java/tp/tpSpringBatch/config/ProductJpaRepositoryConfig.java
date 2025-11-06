package tp.tpSpringBatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "tp.tpSpringBatch.repository", //package of the repositories
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "transactionManager" //specify transaction manager
)
public class ProductJpaRepositoryConfig {
}
