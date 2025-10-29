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

import tp.jpaSpringBatch.reader.MyCustomProductWithDetailsGeneratorReaderConfig;

import tp.jpaSpringBatch.step.PrepareProductTableInDbStepConfig;
import tp.jpaSpringBatch.tasklet.InitProductWithDetailsTasklet;
import tp.jpaSpringBatch.writer.MyDbProductWithDetailsJpaWriterConfig; //version jpa
import tp.jpaSpringBatch.writer.MyDbProductWithDetailsRepositoryWriterConfig; //version spring data jpa repository

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	MyProductDbDataSourceConfig.class, MyProductDbEntityManagerFactoryConfig.class, ProductJpaRepositoryConfig.class,
	DataSetGeneratorJobConfig.class , PrepareProductTableInDbStepConfig.class, InitProductWithDetailsTasklet.class,
	MyCustomProductWithDetailsGeneratorReaderConfig.class,AtomikosConfig.class,
        MyDbProductWithDetailsJpaWriterConfig.class,MyDbProductWithDetailsRepositoryWriterConfig.class
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
		//4.5 s avec commitInterval = 1 dans generateDbDataSetJob
		//2.7 s avec commitInterval = 10 dans generateDbDataSetJob
		//1.9 s avec commitInterval = 100 dans generateDbDataSetJob
	}
	//OK en mode db-jpa ou bien db-repository (dans DataSetGeneratorJobConfig)
	
}
