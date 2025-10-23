package tp.jpaSpringBatch.job;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import tp.jpaSpringBatch.AbstractBasicActiveTestJob;
import tp.jpaSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.jpaSpringBatch.config.MyProductDbDataSourceConfig;
import tp.jpaSpringBatch.config.MyProductDbEntityManagerFactoryConfig;
import tp.jpaSpringBatch.config.ProductJpaRepositoryConfig;
import tp.jpaSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;
import tp.jpaSpringBatch.reader.MyDbProductStatJpaReaderConfig;
import tp.jpaSpringBatch.reader.MyDbProductWithDetailsJpaReaderConfig;
import tp.jpaSpringBatch.reader.MyDbProductWithDetailsRepositoryReaderConfig;
import tp.jpaSpringBatch.step.ProductStatDbToCsvStepConfig;
import tp.jpaSpringBatch.writer.MyConsoleProductStatWriterConfig;
import tp.jpaSpringBatch.writer.MyConsoleProductWithDetailsWriterConfig;
import tp.jpaSpringBatch.writer.MyCsvFileProductStatWriterConfig;
import tp.jpaSpringBatch.writer.MyDbProductWithDetailsJpaWriterConfig; //version jpa
import tp.jpaSpringBatch.writer.MyDbProductWithDetailsRepositoryWriterConfig; //version spring data jpa repository



@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,MyProductDbEntityManagerFactoryConfig.class, ProductJpaRepositoryConfig.class,
	ProductStatDbToCsvStepConfig.class,
	IncreaseProductPriceInDbJobConfig.class ,
        MyDbProductWithDetailsJpaReaderConfig.class,MyDbProductWithDetailsRepositoryReaderConfig.class,
	MyConsoleProductWithDetailsWriterConfig.class,
        MyDbProductWithDetailsJpaWriterConfig.class,MyDbProductWithDetailsRepositoryWriterConfig.class,
	IncreasePriceOfProductWithDetailsProcessor.class,
	MyConsoleProductStatWriterConfig.class ,
        MyDbProductStatJpaReaderConfig.class,
	MyCsvFileProductStatWriterConfig.class
	})
class IncreaseProductPriceInDbTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { IncreaseProductPriceInDbTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestIncreaseProductPriceInDbJob extends AbstractBasicActiveTestJob {	
	
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
        .addDouble("increaseRatePct", 1.0)//used by IncreasePriceOfProductWithDetailsProcessor (1% d'augmentation)
		.addString("productCategoryToIncrease", "aliment")//used by IncreasePriceOfProductWithDetailsProcessor (categorie de produit à augmenter)
		.addLong("slowProcessorDelay",200L) //200ms de pause pour simuler traitement long dans processeur
		.addLong("minManyUpdated",2L);//used by MyUpdatedCountCheckingDecider
        
	}
	//ok en mode db-jpa ou bien db-repository (dans IncreaseProductPriceInDbJobConfig)

	//4.5s , 4.5s sans partition avec 16 enregistrements et pause de 200ms
}
