package tp.basesSpringBatch.job;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.basesSpringBatch.tasklet.PrintMessageTasklet;


@Configuration
@Slf4j
public class HelloWorldJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "myHelloWorldJob";
    public static final String MAIN_STEP_NAME = "simplePrintMessageStep";

    @Bean(name=MAIN_STEP_NAME)
    public Step simplePrintMessageStep(PrintMessageTasklet printHelloWorldMessageTaskletBean){
        var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
        return stepBuilder
                .tasklet(printHelloWorldMessageTaskletBean, this.batchTxManager)
                .build();
    }

  @Bean(name=JOB_NAME)
  public Job myHelloWorldJob(
		  @Qualifier(MAIN_STEP_NAME) Step printMessageStepWithTasklet
		  ) {
    return this.buildMySingleStepJob(JOB_NAME, printMessageStepWithTasklet);
  }




}