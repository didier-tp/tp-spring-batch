package tp.tpSpringBatch.job.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.tasklet.bean.PrintHelloWorldMessageTaskletBean;

@Configuration
@Profile("!xmlJobConfig")
public class HelloWorldJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(HelloWorldJobConfig.class);

  @Bean(name="myHelloWorldJob")
  public Job myHelloWorldJob(
		  @Qualifier("simplePrintMessageStep") Step printMessageStepWithTasklet 
		  ) {
    var name = "myHelloWorldJob";
    return this.buildMySingleStepJob(name, printMessageStepWithTasklet);
  }

  @Bean
  public Step simplePrintMessageStep(PrintHelloWorldMessageTaskletBean printHelloWorldMessageTaskletBean){
    var name = "simplePrintMessageStep";
    var stepBuilder = new StepBuilder(name, jobRepository);
    return stepBuilder
        .tasklet(printHelloWorldMessageTaskletBean, this.batchTxManager)
        .build();
  }


}