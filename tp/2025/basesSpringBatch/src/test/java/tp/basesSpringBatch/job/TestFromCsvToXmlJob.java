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
import tp.basesSpringBatch.writer.MyXmlFileProductWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	ProductsCsvToXmlJobConfig.class ,
	MyXmlFileProductWriterConfig.class ,
	MyCsvFileProductReaderConfig.class,
	SimpleUppercaseProductProcessor.class
	})
class FromCsvToXmlTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromCsvToXmlTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestFromCsvToXmlJob extends AbstractBasicActiveTestJob {

	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("inputFilePath", "data/input/csv/products.csv")//used by some Reader/Writer
		.addString("outputFilePath", "data/output/xml/products.xml")//used by some Reader/Writer
        .addString("enableUpperCase", "true");//used by SimpleUppercaseProductProcessor
		//.addString("enableUpperCase", "false");//used by SimpleUppercaseProductProcessor
		
	}
	
	@Override
	public void postJobCheckings(){
	   this.verifSameContentExceptedResultFile(
			   "data/expected_output/xml/products.xml", 
			   "data/output/xml/products.xml");
	}
	
}
