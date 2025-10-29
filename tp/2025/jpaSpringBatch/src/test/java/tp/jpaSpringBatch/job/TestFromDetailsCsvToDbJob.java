package tp.jpaSpringBatch.job;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import tp.jpaSpringBatch.AbstractBasicActiveTestJob;
import tp.jpaSpringBatch.config.*;
import tp.jpaSpringBatch.reader.MyCsvFileProductWithDetailsReaderConfig;
import tp.jpaSpringBatch.step.PrepareProductTableInDbStepConfig;
import tp.jpaSpringBatch.tasklet.InitProductWithDetailsTasklet;
import tp.jpaSpringBatch.writer.MyDbProductWithDetailsJpaWriterConfig; //version jpa
import tp.jpaSpringBatch.writer.MyDbProductWithDetailsRepositoryWriterConfig; //version spring data jpa repository



@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class, MyProductDbEntityManagerFactoryConfig.class, ProductJpaRepositoryConfig.class,
	ProductsWithDetailsCsvToDbJobConfig.class , PrepareProductTableInDbStepConfig.class, InitProductWithDetailsTasklet.class,
	/*MyConsoleProductWithDetailsWriterConfig.class ,*/
	MyCsvFileProductWithDetailsReaderConfig.class, AtomikosConfig.class,
        MyDbProductWithDetailsJpaWriterConfig.class,MyDbProductWithDetailsRepositoryWriterConfig.class
	})
class FromDetailsCsvToDbTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { FromDetailsCsvToDbTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestFromDetailsCsvToDbJob extends AbstractBasicActiveTestJob {
	
	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
		.addString("inputFilePath", "data/input/csv/newDetailsProducts.csv");//used by some Reader/Writer
	}
    //OK en mode db-jpa ou bien db-repository (dans ProductsWithDetailsCsvToDbJobConfig)
}
