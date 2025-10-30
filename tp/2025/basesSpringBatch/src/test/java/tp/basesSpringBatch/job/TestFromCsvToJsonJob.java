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
import tp.basesSpringBatch.processor.SimpleUppercaseProductProcessor;
import tp.basesSpringBatch.reader.MyCsvFileProductReaderConfig;
import tp.basesSpringBatch.writer.MyJsonFileProductWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	ProductsCsvToJsonJobConfig.class ,
	MyJsonFileProductWriterConfig.class ,
	MyCsvFileProductReaderConfig.class,
	SimpleUppercaseProductProcessor.class
	})
class FromCsvToJsonTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromCsvToJsonTestConfig.class } )
@ActiveProfiles(profiles = {"h2"})
public class TestFromCsvToJsonJob extends AbstractBasicActiveTestJob {

	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("inputFilePath", "data/input/csv/products.csv");//used by productCsvFileReader
	}
	
	@Override
	public void postJobCheckings(){
	   this.verifSameContentExceptedResultFile(
			   "data/expected_output/json/products.json", 
			   "data/output/json/products.json");
	}
	
}
