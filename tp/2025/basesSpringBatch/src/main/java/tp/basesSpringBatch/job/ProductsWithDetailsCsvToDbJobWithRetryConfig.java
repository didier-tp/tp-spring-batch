package tp.basesSpringBatch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemReaderException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.basesSpringBatch.exception.MyProcessException;
import tp.basesSpringBatch.listener.MySkippedErrorsListener;
import tp.basesSpringBatch.model.ProductWithDetails;
import tp.basesSpringBatch.processor.SimpleUppercaseProductProcessor;
import tp.basesSpringBatch.processor.UppercaseProductWithDetailsProcessorWithRetry;

@Configuration
@Slf4j
public class ProductsWithDetailsCsvToDbJobWithRetryConfig extends MyAbstractJobConfig{
    public static final String JOB_NAME = "fromDetailsCsvToDbWithRetryJob";
    public static final String MAIN_STEP_NAME = "stepDetailsCsvToDbWithRetry";

  @Bean(name=JOB_NAME)
  public Job fromDetailsCsvToDbWithRetryJob(@Qualifier(MAIN_STEP_NAME) Step step1) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  public Step stepDetailsCsvToDbWithRetry(@Qualifier("csv") ItemReader<ProductWithDetails> productItemReader,
		            //@Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter  
		            @Qualifier("insert_in_db") ItemWriter<ProductWithDetails> productItemWriter,
                   UppercaseProductWithDetailsProcessorWithRetry uppercaseProductWithDetailsProcessorWithRetry
		            ) {
    var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return stepBuilder
        .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
        .reader(productItemReader)
        .processor(uppercaseProductWithDetailsProcessorWithRetry) //simulate 3 failures before success
        .faultTolerant()
            //.retryLimit(2) //error if less than number of simulated failures
            .retryLimit(3) //ok
            .retry(MyProcessException.class)
        //.listener(new MySkippedErrorsListener())
        .writer(productItemWriter)
        .build();
  }
 

}