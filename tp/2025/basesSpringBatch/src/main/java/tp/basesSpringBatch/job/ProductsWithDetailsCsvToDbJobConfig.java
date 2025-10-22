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

import tp.basesSpringBatch.model.ProductWithDetails;

@Configuration
@Slf4j
public class ProductsWithDetailsCsvToDbJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "fromDetailsCsvToDbJob";
    public static final String MAIN_STEP_NAME = "stepDetailsCsvToDb";

  @Bean(name=JOB_NAME)
  public Job fromDetailsCsvToDbJob(@Qualifier(MAIN_STEP_NAME) Step step1) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  //@Qualifier("csvToDb")
  public Step stepDetailsCsvToDb(@Qualifier("csv") ItemReader<ProductWithDetails> productItemReader,
		            //@Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter  
		            @Qualifier("insert_in_db") ItemWriter<ProductWithDetails> productItemWriter  
		            ) {
    var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return stepBuilder
        .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
        .reader(productItemReader)
        .writer(productItemWriter)
        .build();
  }
 

}