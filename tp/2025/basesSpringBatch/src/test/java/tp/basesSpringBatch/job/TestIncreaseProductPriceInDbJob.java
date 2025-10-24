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
import tp.basesSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;
import tp.basesSpringBatch.reader.MyDbProductStatReaderConfig;
import tp.basesSpringBatch.reader.MyDbProductWithDetailsReaderConfig;
import tp.basesSpringBatch.step.ProductStatDbToCsvStepConfig;
import tp.basesSpringBatch.writer.MyConsoleProductStatWriterConfig;
import tp.basesSpringBatch.writer.MyConsoleProductWithDetailsWriterConfig;
import tp.basesSpringBatch.writer.MyCsvFileProductStatWriterConfig;
import tp.basesSpringBatch.writer.MyDbProductWithDetailsWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	ProductStatDbToCsvStepConfig.class,
	IncreaseProductPriceInDbJobConfig.class ,
	MyDbProductWithDetailsReaderConfig.class,
	MyConsoleProductWithDetailsWriterConfig.class,
	MyDbProductWithDetailsWriterConfig.class,
	IncreasePriceOfProductWithDetailsProcessor.class,
	MyConsoleProductStatWriterConfig.class ,
	MyDbProductStatReaderConfig.class,
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
		//.addLong("slowProcessorDelay",200L) //200ms de pause pour simuler traitement long dans processeur
		.addLong("minManyUpdated",2L);//used by MyUpdatedCountCheckingDecider
        
	}
	
	//H2, 4.5s , 4.5s sans partition avec 16 enregistrements et pause de 200ms
    //MySql ,13,5 sans partition avec 10000 enregistrements et pause de 0ms
}
