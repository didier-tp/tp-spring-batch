package tp.restartableBatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.restartableBatch.listener.MyStoppedForRestartExecutionListener;
import tp.restartableBatch.model.ProductWithDetails;

@Configuration
@Slf4j
public class ProductsWithDetailsCsvToConsoleJobRestartableConfig extends MyAbstractJobConfig{
    public static final String JOB_NAME = "fromCsvToConsoleJobRestartable";
    public static final String MAIN_STEP_NAME = "stepCsvToConsoleRestartable";

  @Bean(name=JOB_NAME)
  public Job stepCsvToConsoleRestartable(@Qualifier(MAIN_STEP_NAME) Step step1) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  public Step stepDetailsCsvToDbWithRestartable(@Qualifier("csv") ItemReader<ProductWithDetails> productItemReader,
		            @Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter
		            ) {
    var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return stepBuilder
        .<ProductWithDetails, ProductWithDetails>chunk(2, batchTxManager)
        .reader(productItemReader)
        .startLimit(3)
        .listener(new MyStoppedForRestartExecutionListener())
        .writer(productItemWriter)
        .build();
  }
 

}