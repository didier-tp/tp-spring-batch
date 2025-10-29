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

import tp.basesSpringBatch.reader.MyCustomProductWithDetailsGeneratorReaderConfig;

import tp.basesSpringBatch.step.PrepareProductTableInDbStepConfig;
import tp.basesSpringBatch.tasklet.InitProductWithDetailsTasklet;
import tp.basesSpringBatch.writer.MyDbProductWithDetailsWriterConfig;

@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class,
	DataSetGeneratorJobConfig.class ,
	MyCustomProductWithDetailsGeneratorReaderConfig.class,
	MyDbProductWithDetailsWriterConfig.class,
        PrepareProductTableInDbStepConfig.class , InitProductWithDetailsTasklet.class
	})
class DataSetGeneratorTestConfig{
}

@SpringBatchTest
@SpringBootTest(classes = { DataSetGeneratorTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestDataSetGeneratorJob extends AbstractBasicActiveTestJob {

	@Override
	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
		return jobParametersBuilder
                .addLong("dataSetSize", 100L);
		//.addLong("dataSetSize", 10000L);//ou 10000L
		//4.5 s avec commitInterval = 1 dans generateDbDataSetJob
		//2.7 s avec commitInterval = 10 dans generateDbDataSetJob
		//1.9 s avec commitInterval = 100 dans generateDbDataSetJob

        //avec mysql et commitInterval = 1 dans generateDbDataSetJob : 41s
        //avec mysql et commitInterval = 10 dans generateDbDataSetJob : 21s
        //avec mysql et commitInterval = 100 dans generateDbDataSetJob : 14s
	}
	
	
}
