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
import tp.basesSpringBatch.reader.MyDbProductWithDetailsReaderWithPartitionConfig;
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
	IncreaseProductPriceInDbWithPartitionJobConfig.class ,
	MyDbProductWithDetailsReaderWithPartitionConfig.class,
	MyConsoleProductWithDetailsWriterConfig.class,
	MyDbProductWithDetailsWriterConfig.class,
	IncreasePriceOfProductWithDetailsProcessor.class,
	MyConsoleProductStatWriterConfig.class ,
	MyDbProductStatReaderConfig.class,
	MyCsvFileProductStatWriterConfig.class
	})
class IncreaseProductPriceInDbWithPartitionTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { IncreaseProductPriceInDbWithPartitionTestConfig.class } )
@ActiveProfiles(profiles = {"h2"})
public class TestIncreaseProductPriceInDbWithPartitionJob extends AbstractBasicActiveTestJob {	
	
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
        .addDouble("increaseRatePct", 1.0)//used by IncreasePriceOfProductWithDetailsProcessor (1% d'augmentation)
        //.addLong("slowProcessorDelay",200L) //200ms de pause pour simuler traitement long dans processeur
		.addString("productCategoryToIncrease", "all");//used by IncreasePriceOfProductWithDetailsProcessor (categorie de produit à augmenter)
		//4.5s
	}

    @Override
    public void postJobCheckings() {
        System.out.println("nbProcessors=" + Runtime.getRuntime().availableProcessors());
    }

    //H2 : 2,3s avec partition avec 16 enregistrements et pause de 200ms

    //Sur pc avec 8 processeurs:
    //MySql : 23s avec partition avec 10000 enregistrements et pause de 0ms (gridSize=1)
    //MySql : 14,5s avec partition avec 10000 enregistrements et pause de 0ms (gridSize=2)
    //MySql : 8,9s avec partition avec 10000 enregistrements et pause de 0ms (gridSize=4)
    //MySql : 7,1s avec partition avec 10000 enregistrements et pause de 0ms (gridSize=6)
    //MySql : 6,3s avec partition avec 10000 enregistrements et pause de 0ms (gridSize=8)
    //MySql : 5,8s avec partition avec 10000 enregistrements et pause de 0ms (gridSize=10)
}
