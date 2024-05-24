package tp.tpSpringBatch.job;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import tp.tpSpringBatch.AbstractBasicActiveTestJob;
import tp.tpSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.tpSpringBatch.datasource.MyProductDbDataSourceConfig;
import tp.tpSpringBatch.job.java.ProductStatDbToCsvJobConfig;
import tp.tpSpringBatch.reader.java.MyDbProductStatReaderConfig;
import tp.tpSpringBatch.step.java.ProductStatDbToCsvStepConfig;
import tp.tpSpringBatch.writer.java.MyConsoleProductStatWriterConfig;
import tp.tpSpringBatch.writer.java.MyCsvFileProductStatWriterConfig;


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
@ActiveProfiles(profiles = {})
public class TestFromProductStatDbToCsvJob extends AbstractBasicActiveTestJob {

	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("outputFilePath", "data/output/csv/productStat.csv");//used by some Reader/Writer in a future version
		
	}
	
	
}
