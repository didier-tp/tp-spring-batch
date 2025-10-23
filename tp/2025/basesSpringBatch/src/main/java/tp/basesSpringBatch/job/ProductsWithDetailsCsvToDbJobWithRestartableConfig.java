package tp.basesSpringBatch.job;

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
import org.springframework.context.annotation.Profile;

import tp.basesSpringBatch.listener.MyStoppedForRestartExecutionListener;
import tp.basesSpringBatch.model.ProductWithDetails;

@Configuration
@Slf4j
public class ProductsWithDetailsCsvToDbJobWithRestartableConfig extends MyAbstractJobConfig{
    public static final String JOB_NAME = "fromDetailsCsvToDbWithRetartableJob";
    public static final String MAIN_STEP_NAME = "stepDetailsCsvToDbWithRetartable";

  @Bean(name=JOB_NAME)
  public Job fromDetailsCsvToDbWithRetartableJob(@Qualifier(MAIN_STEP_NAME) Step step1) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  //@Qualifier("csvToDbWithRetartable")
  public Step stepDetailsCsvToDbWithRetartable(@Qualifier("csv") ItemReader<ProductWithDetails> productItemReader,
		            //@Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter  
		            @Qualifier("insert_in_db") ItemWriter<ProductWithDetails> productItemWriter  
		            ) {
    var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return stepBuilder
        .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
        .reader(productItemReader)
        .startLimit(3)
        .listener(new MyStoppedForRestartExecutionListener())
        .writer(productItemWriter)
        .build();
  }
 

}