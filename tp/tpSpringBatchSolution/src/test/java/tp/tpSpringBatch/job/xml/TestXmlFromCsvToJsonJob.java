package tp.tpSpringBatch.job.xml;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ActiveProfiles;

import tp.tpSpringBatch.AbstractBasicActiveTestJob;
import tp.tpSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.tpSpringBatch.job.java.ProductsCsvToJsonJobConfig;
import tp.tpSpringBatch.reader.java.MyCsvFileProductReaderConfig;
import tp.tpSpringBatch.writer.java.MyJsonFileProductWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class
	})
@ComponentScan(basePackages = "tp.tpSpringBatch.processor")
@ImportResource({"classpath:job/listenersCommonSubConfig.xml",
				"classpath:job/csv_json_xml_CommonSubConfig.xml",
				"classpath:job/fromCsvToJsonJob.xml"})
class FromCsvToJsonXmlTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromCsvToJsonXmlTestConfig.class } )
@ActiveProfiles(profiles = {"xmlJobConfig"})
public class TestXmlFromCsvToJsonJob extends AbstractBasicActiveTestJob {

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
