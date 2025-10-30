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
import tp.basesSpringBatch.reader.MyCsvFileProductWithDetailsReaderConfig;
import tp.basesSpringBatch.writer.MyConsoleProductWithDetailsWriterConfig;
import tp.basesSpringBatch.writer.MyDbProductWithDetailsWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	ProductsWithDetailsCsvToDbJobWithSkipsConfig.class ,
	MyConsoleProductWithDetailsWriterConfig.class ,
	MyCsvFileProductWithDetailsReaderConfig.class,
	MyDbProductWithDetailsWriterConfig.class
	})
class FromDetailsCsvToDbWithSkipsTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromDetailsCsvToDbWithSkipsTestConfig.class } )
@ActiveProfiles(profiles = {"h2"})
public class TestFromDetailsCsvToDbWithSkipsJob extends AbstractBasicActiveTestJob {
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("inputFilePath", "data/input/csv/newDetailsProducts_withErrors.csv");//used by some Reader/Writer
	}

}
