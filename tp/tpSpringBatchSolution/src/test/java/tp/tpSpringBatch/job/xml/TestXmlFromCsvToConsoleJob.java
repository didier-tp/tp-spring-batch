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
import tp.tpSpringBatch.job.java.ProductsCsvToConsoleJobConfig;
import tp.tpSpringBatch.reader.java.MyCsvFileProductReaderConfig;
import tp.tpSpringBatch.writer.java.MyConsoleProductWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@ComponentScan(basePackages = "tp.tpSpringBatch.processor")
@ImportResource({"classpath:job/listenersCommonSubConfig.xml",
				"classpath:job/csv_json_xml_CommonSubConfig.xml",
				"classpath:job/fromCsvToConsoleJob.xml"})
class FromCsvToConsoleXmlTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromCsvToConsoleXmlTestConfig.class } )
@ActiveProfiles(profiles = { "xmlJobConfig"})
public class TestXmlFromCsvToConsoleJob extends AbstractBasicActiveTestJob {
	
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
				.addString("inputFilePath", "data/input/csv/products.csv");//used by productCsvFileReader
	}
}
