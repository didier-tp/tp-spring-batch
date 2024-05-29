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
import tp.tpSpringBatch.job.java.DataSetGeneratorJobConfig;
import tp.tpSpringBatch.reader.java.MyCustomProductWithDetailsGeneratorReaderConfig;
import tp.tpSpringBatch.step.java.ProductStatDbToCsvStepConfig;
import tp.tpSpringBatch.writer.java.MyConsoleProductStatWriterConfig;
import tp.tpSpringBatch.writer.java.MyCsvFileProductStatWriterConfig;
import tp.tpSpringBatch.writer.java.MyDbProductWithDetailsWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	DataSetGeneratorJobConfig.class ,
	MyCustomProductWithDetailsGeneratorReaderConfig.class,
	MyDbProductWithDetailsWriterConfig.class
	})
class DataSetGeneratorTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { DataSetGeneratorTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestDataSetGeneratorJob extends AbstractBasicActiveTestJob {

	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addLong("dataSetSize", 10000L);
		//4.5 s avec commitInterval = 1 dans generateDbDataSetJob
		//2.7 s avec commitInterval = 10 dans generateDbDataSetJob
		//1.9 s avec commitInterval = 100 dans generateDbDataSetJob
	}
	
	
}
