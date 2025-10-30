package tp.basesSpringBatch.job;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import tp.basesSpringBatch.AbstractBasicActiveTestJob;
import tp.basesSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.basesSpringBatch.config.MyProductDbDataSourceConfig;
import tp.basesSpringBatch.reader.MyDbProductStatReaderConfig;
import tp.basesSpringBatch.step.ProductStatDbToCsvStepConfig;
import tp.basesSpringBatch.writer.MyConsoleProductStatWriterConfig;
import tp.basesSpringBatch.writer.MyCsvFileProductStatWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	ProductStatDbToCsvStepConfig.class,
	ProductStatDbToCsvJobConfig.class ,
	MyConsoleProductStatWriterConfig.class ,
	MyDbProductStatReaderConfig.class,
	MyCsvFileProductStatWriterConfig.class
	})
class FromProductStatDbToCsvTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromProductStatDbToCsvTestConfig.class } )
@ActiveProfiles(profiles = {"h2"})
public class TestFromProductStatDbToCsvJob extends AbstractBasicActiveTestJob {

	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("outputFilePath", "data/output/csv/productStats.csv");//used by some Reader/Writer in a future version
		
	}
	
	
}
