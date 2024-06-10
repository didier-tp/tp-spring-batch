package tp.tpSpringBatch.job;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import tp.tpSpringBatch.AbstractBasicActiveTestJob;
import tp.tpSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.tpSpringBatch.datasource.MyProductDbDataSourceConfig;
import tp.tpSpringBatch.job.java.IncreaseProductPriceInDbCategoryDrivenJobConfig;
import tp.tpSpringBatch.processor.IncreasePriceOfProductCategoryDrivenProcessor;
import tp.tpSpringBatch.reader.java.CategoryDrivenProductToIncreaseReaderConfig;
import tp.tpSpringBatch.reader.java.MyCsvFileIncreaseForCategoryReaderConfig;
import tp.tpSpringBatch.writer.java.MyConsoleProductWithDetailsWriterConfig;
import tp.tpSpringBatch.writer.java.MyDbProductWithDetailsWriterConfig;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	IncreaseProductPriceInDbCategoryDrivenJobConfig.class ,
	MyCsvFileIncreaseForCategoryReaderConfig.class,
	CategoryDrivenProductToIncreaseReaderConfig.class,
	MyConsoleProductWithDetailsWriterConfig.class,
	MyDbProductWithDetailsWriterConfig.class,
	IncreasePriceOfProductCategoryDrivenProcessor.class
	})
class IncreaseProductPriceInDbCategoryDrivenTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { IncreaseProductPriceInDbCategoryDrivenTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestIncreaseProductPriceInDbCategoryDrivenJob extends AbstractBasicActiveTestJob {	
	
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
        .addString("inputFilePath", "data/input/csv/category_driven_increase.csv");//used by increaseForCategoryCsvFileReader 
		
        
	}
}
