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
import tp.tpSpringBatch.job.java.ProductsWithDetailsCsvToDbJobWithRestartableConfig;
import tp.tpSpringBatch.reader.java.MyCsvFileProductWithDetailsReaderConfig;
import tp.tpSpringBatch.writer.java.MyConsoleProductWithDetailsWriterConfig;
import tp.tpSpringBatch.writer.java.MyDbProductWithDetailsWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	ProductsWithDetailsCsvToDbJobWithRestartableConfig.class ,
	MyConsoleProductWithDetailsWriterConfig.class ,
	MyCsvFileProductWithDetailsReaderConfig.class,
	MyDbProductWithDetailsWriterConfig.class
	})
class FromDetailsCsvToDbWithRestartableTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromDetailsCsvToDbWithRestartableTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestFromDetailsCsvToDbWithRestartableJob extends AbstractBasicActiveTestJob {
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("inputFilePath", "data/input/csv/newDetailsProducts_withOrWithoutErrors.csv");//used by some Reader/Writer
	}

}
