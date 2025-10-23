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

import tp.basesSpringBatch.decider.MyUpdatedCountCheckingDecider;
import tp.basesSpringBatch.model.ProductWithDetails;
import tp.basesSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;
import tp.basesSpringBatch.tasklet.PrintMessageTasklet;

@Configuration
@Slf4j
public class IncreaseProductPriceInDbJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "increaseProductPriceInDbJob";
    public static final String MAIN_STEP_NAME = "stepDbToDbWithPriceIncrease";
 
  @Bean
  public MyUpdatedCountCheckingDecider myUpdatedCountCheckingDecider() {
	  return new MyUpdatedCountCheckingDecider();
  }

  @Bean(name=JOB_NAME)
  public Job increaseProductPriceInDbJob(
		  MyUpdatedCountCheckingDecider updatedCountCheckingDecider ,
		  @Qualifier(MAIN_STEP_NAME) Step step1,
		  @Qualifier("dbStatToCsv") Step stepStatToCsv) {
    var messageStepName = "updateProductMessageStep";
    var builder = new JobBuilder(JOB_NAME, jobRepository);
    var stepBuilder = new StepBuilder(messageStepName, jobRepository);
    
    Step stepWhenFew = stepBuilder.tasklet(new PrintMessageTasklet(">>>>> few updated products"), this.batchTxManager).build();
    Step stepWhenMany = stepBuilder.tasklet(new PrintMessageTasklet(">>>>> MANY updated products"), this.batchTxManager).build();
    
    
    return builder.start(step1)
    		 .next(updatedCountCheckingDecider).on("COMPLETED_WITH_MANY_UPDATED").to(stepWhenMany)
             .from(updatedCountCheckingDecider).on("COMPLETED").to(stepWhenFew)
             .from(stepWhenMany).next(stepStatToCsv)//generate Stat in .csv file if many updated
             .end() //end of FlowBuilder and return to JobBuilder
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