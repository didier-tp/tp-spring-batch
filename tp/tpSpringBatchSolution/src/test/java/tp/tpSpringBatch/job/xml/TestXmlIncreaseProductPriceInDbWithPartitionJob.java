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
import tp.tpSpringBatch.datasource.MyProductDbDataSourceConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@ComponentScan(basePackages = "tp.tpSpringBatch.processor")//IncreasePriceOfProductWithDetailsProcessor
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	})
@ImportResource({"classpath:job/jdbcCommonSubConfig.xml",
	             "classpath:job/csv_json_xml_CommonSubConfig.xml",
				"classpath:job/increaseProductPriceInDbWithPartitionJob.xml"})
class IncreaseProductPriceInDbWithPartitionXmlTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { IncreaseProductPriceInDbWithPartitionXmlTestConfig.class } )
@ActiveProfiles(profiles = {"xmlJobConfig"})
public class TestXmlIncreaseProductPriceInDbWithPartitionJob extends AbstractBasicActiveTestJob {	
	
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
        .addDouble("increaseRatePct", 1.0)//used by IncreasePriceOfProductWithDetailsProcessor (1% d'augmentation)
		.addString("productCategoryToIncrease", "all")//used by IncreasePriceOfProductWithDetailsProcessor (categorie de produit à augmenter)
		.addLong("slowProcessorDelay",200L); //200ms de pause pour simuler traitement long dans processeur
	
        
	}
	
	//4.5s , 4.5s sans partition avec 16 enregistrements et pause de 200ms
}
