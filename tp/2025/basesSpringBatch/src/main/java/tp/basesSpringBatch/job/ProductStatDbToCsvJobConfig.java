package tp.basesSpringBatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class ProductStatDbToCsvJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "fromDbExtractStatToCsvJob";
    public static final String MAIN_STEP_NAME = "stepDbStatToCsv";


  @Bean(name=JOB_NAME)
  public Job fromDbExtractStatToCsvJob(@Qualifier(MAIN_STEP_NAME) Step step1) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  // stepDbStatToCsv now in step.ProductStatDbToCsvStepConfig

}