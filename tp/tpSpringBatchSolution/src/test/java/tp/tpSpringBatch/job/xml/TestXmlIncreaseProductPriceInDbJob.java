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
				"classpath:job/increaseProductPriceInDbJob.xml"})
class IncreaseProductPriceInDbXmlTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { IncreaseProductPriceInDbXmlTestConfig.class } )
@ActiveProfiles(profiles = {"xmlJobConfig"})
public class TestXmlIncreaseProductPriceInDbJob extends AbstractBasicActiveTestJob {	
	
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
        .addDouble("increaseRatePct", 1.0)//used by IncreasePriceOfProductWithDetailsProcessor (1% d'augmentation)
		.addString("productCategoryToIncrease", "aliment")//used by IncreasePriceOfProductWithDetailsProcessor (categorie de produit à augmenter)
		//.addLong("slowProcessorDelay",200L) //200ms de pause pour simuler traitement long dans processeur
		.addLong("slowProcessorDelay",1L)
		.addLong("minManyUpdated",2L);//used by MyUpdatedCountCheckingDecider
        
	}
	
	//4.5s , 4.5s sans partition avec 16 enregistrements et pause de 200ms
}
