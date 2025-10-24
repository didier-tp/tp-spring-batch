package tp.restartableBatch.job;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tp.restartableBatch.AbstractBasicActiveTestJob;
import tp.restartableBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.restartableBatch.reader.MyCsvFileProductWithDetailsReaderConfig;
import tp.restartableBatch.writer.MyConsoleProductWithDetailsWriterConfig;



@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	ProductsWithDetailsCsvToConsoleJobRestartableConfig.class ,
	MyConsoleProductWithDetailsWriterConfig.class ,
	MyCsvFileProductWithDetailsReaderConfig.class
	})
class FromDetailsCsvToDbWithRestartableTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromDetailsCsvToDbWithRestartableTestConfig.class } )
public class TestFromDetailsCsvToDbWithRestartableJob extends AbstractBasicActiveTestJob {
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("inputFilePath", "data/input/csv/newDetailsProducts_withOrWithoutErrors.csv");//used by some Reader/Writer
	}

}
