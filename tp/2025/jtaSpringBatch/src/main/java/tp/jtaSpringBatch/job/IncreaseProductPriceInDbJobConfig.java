package tp.jtaSpringBatch.job;

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

import tp.jtaSpringBatch.model.ProductWithDetails;
import tp.jtaSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;

@Configuration
@Slf4j
public class IncreaseProductPriceInDbJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "increaseProductPriceInDbJob";
    public static final String MAIN_STEP_NAME = "stepDbToDbWithPriceIncrease";
 

  @Bean(name=JOB_NAME)
  public Job increaseProductPriceInDbJob(
		  @Qualifier(MAIN_STEP_NAME) Step step1,
		  @Qualifier("dbStatToCsv") Step stepStatToCsv) {
    var messageStepName = "updateProductMessageStep";
    var builder = new JobBuilder(JOB_NAME, jobRepository);
    var stepBuilder = new StepBuilder(messageStepName, jobRepository);

    
    return builder.start(step1)
    		 .next(stepStatToCsv)//generate Stat in .csv file if many updated
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  //@Qualifier("dbToDbWithPriceIncrease")
  public Step stepDbToDbWithPriceIncrease(@Qualifier("db") ItemReader<ProductWithDetails> reader,
		            /*@Qualifier("console")*/ @Qualifier("update_price_in_db") ItemWriter<ProductWithDetails> writer,
		            IncreasePriceOfProductWithDetailsProcessor increasePriceProcessor ) {
    var builder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return builder
        .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
        .reader(reader)
        .processor(increasePriceProcessor)
        .writer(writer)
        .build();
  }

  // stepDbStatToCsv is defined in step.ProductStatDbToCsvStepConfig
}