package tp.basesSpringBatch.tasklet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class InitProductWithDetailsTasklet extends AbstractResourceDatabasePopulatorTasklet{

    private static final String RESOURCE_PATH = "sql/init-product-with-details.sql";

    @Autowired
    public InitProductWithDetailsTasklet(@Qualifier("productdb") DataSource dataSource) {
        super(dataSource, RESOURCE_PATH);
    }
}
