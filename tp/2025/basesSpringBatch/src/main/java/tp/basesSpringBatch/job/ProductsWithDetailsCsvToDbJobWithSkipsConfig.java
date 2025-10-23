package tp.basesSpringBatch.job;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.context.annotation.Profile;

import tp.basesSpringBatch.listener.MySkippedErrorsListener;
import tp.basesSpringBatch.model.ProductWithDetails;

@Configuration
@Slf4j
public class ProductsWithDetailsCsvToDbJobWithSkipsConfig extends MyAbstractJobConfig{
    public static final String JOB_NAME = "fromDetailsCsvToDbWithSkipsJob";
    public static final String MAIN_STEP_NAME = "stepDetailsCsvToDbWithSkips";

  @Bean(name=JOB_NAME)
  public Job fromDetailsCsvToDbWithSkipsJob(@Qualifier(MAIN_STEP_NAME) Step step1) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  //@Qualifier("csvToDbWithSkips")
  public Step stepDetailsCsvToDbWithSkips(@Qualifier("csv") ItemReader<ProductWithDetails> productItemReader,
		            //@Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter  
		            @Qualifier("insert_in_db") ItemWriter<ProductWithDetails> productItemWriter  
		            ) {
    var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return stepBuilder
        .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
        .reader(productItemReader)
        .faultTolerant().skipLimit(5).skip(ItemReaderException.class)
        .listener(new MySkippedErrorsListener())
        .writer(productItemWriter)
        .build();
  }
 

}