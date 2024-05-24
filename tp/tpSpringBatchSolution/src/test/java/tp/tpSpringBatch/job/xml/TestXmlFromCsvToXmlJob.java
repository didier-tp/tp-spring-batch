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


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	})
@ComponentScan(basePackages = "tp.tpSpringBatch.processor")
@ImportResource({"classpath:job/listenersCommonSubConfig.xml",
	"classpath:job/csv_json_xml_CommonSubConfig.xml",
	"classpath:job/fromCsvToXmlJob.xml"})
class FromCsvToXmlXmlTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromCsvToXmlXmlTestConfig.class } )
@ActiveProfiles(profiles = {"xmlJobConfig"})
public class TestXmlFromCsvToXmlJob extends AbstractBasicActiveTestJob {

	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder.addString("inputFilePath", "data/input/csv/products.csv")//used by some Reader/Writer
				.addString("outputFilePath", "data/output/xml/products.xml")//used by some Reader/Writer
		        .addString("enableUpperCase", "true");//used by SimpleUppercaseProductProcessor
				//.addString("enableUpperCase", "false");//used by SimpleUppercaseProductProcessor
	}
	
	@Override
	public void postJobCheckings(){
	   this.verifSameContentExceptedResultFile(
			   "data/expected_output/xml/productsV2.xml", 
			   "data/output/xml/products.xml");
	}
	
}
