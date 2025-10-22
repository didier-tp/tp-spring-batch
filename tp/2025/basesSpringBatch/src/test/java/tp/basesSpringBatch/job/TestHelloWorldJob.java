package tp.basesSpringBatch.job;

import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import tp.basesSpringBatch.AbstractBasicActiveTestJob;
//import tp.basesSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.basesSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.basesSpringBatch.job.HelloWorldJobConfig;
import tp.basesSpringBatch.tasklet.PrintMessageTasklet;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	     HelloWorldJobConfig.class,
		PrintMessageTasklet.class})
class HelloWorldJobTestConfig{
	
}

@SpringBatchTest
@SpringBootTest(classes = { HelloWorldJobTestConfig.class } )
//@SpringBootTest
//@ActiveProfiles(profiles = {})
public class TestHelloWorldJob extends AbstractBasicActiveTestJob{
}
