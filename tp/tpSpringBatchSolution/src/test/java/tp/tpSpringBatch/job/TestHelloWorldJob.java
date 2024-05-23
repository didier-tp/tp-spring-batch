package tp.tpSpringBatch.job;

import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import tp.tpSpringBatch.AbstractBasicActiveTestJob;
import tp.tpSpringBatch.config.AutomaticSpringBootBatchJobRepositoryConfig;
import tp.tpSpringBatch.job.java.HelloWorldJobConfig;
import tp.tpSpringBatch.tasklet.bean.PrintHelloWorldMessageTaskletBean;


@Configuration
@EnableAutoConfiguration //springBoot & spring-boot-starter-batch autoConfig (application.properties)
@Import({AutomaticSpringBootBatchJobRepositoryConfig.class,
	     HelloWorldJobConfig.class ,
		PrintHelloWorldMessageTaskletBean.class})
class HelloWorldJobTestConfig{
	
}

@SpringBatchTest
@SpringBootTest(classes = { HelloWorldJobTestConfig.class } )
@ActiveProfiles(profiles = {})
public class TestHelloWorldJob extends AbstractBasicActiveTestJob{
}
