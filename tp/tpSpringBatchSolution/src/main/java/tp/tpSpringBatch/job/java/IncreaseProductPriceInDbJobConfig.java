package tp.tpSpringBatch.job.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import tp.tpSpringBatch.decider.MyUpdatedCountCheckingDecider;
import tp.tpSpringBatch.model.ProductWithDetails;
import tp.tpSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;
import tp.tpSpringBatch.tasklet.PrintMessageTasklet;

@Configuration
@Profile("!xmlJobConfig")
public class IncreaseProductPriceInDbJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(IncreaseProductPriceInDbJobConfig.class);
 
  @Bean
  public MyUpdatedCountCheckingDecider myUpdatedCountCheckingDecider() {
	  return new MyUpdatedCountCheckingDecider();
  }

  @Bean(name="increaseProductPriceInDbJob")
  public Job increaseProductPriceInDbJob(
		  MyUpdatedCountCheckingDecider updatedCountCheckingDecider ,
		  @Qualifier("dbToDbWithPriceIncrease") Step step1,
		  @Qualifier("dbStatToCsv") Step stepStat) {
    var name = "insertIntoCsvFromDbJob";
    var builder = new JobBuilder(name, jobRepository);
    var stepBuilder = new StepBuilder(name, jobRepository);
    
    Step stepWhenFew = stepBuilder.tasklet(new PrintMessageTasklet(">>>>> few updated products"), this.batchTxManager).build();
    Step stepWhenMany = stepBuilder.tasklet(new PrintMessageTasklet(">>>>> MANY updated products"), this.batchTxManager).build();
    
    
    return builder.start(step1)
    		 .next(updatedCountCheckingDecider).on("COMPLETED_WITH_MANY_UPDATED").to(stepWhenMany)
             .from(updatedCountCheckingDecider).on("COMPLETED").to(stepWhenFew)
             .from(stepWhenMany).next(stepStat)//generate Stat in .csv file if many updated
             .end() //end of FlowBuilder and return to JobBuilder
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean @Qualifier("dbToDbWithPriceIncrease")
  public Step stepDbToDbWithPriceIncrease(@Qualifier("db") ItemReader<ProductWithDetails> reader,
		            /*@Qualifier("console")*/ @Qualifier("update_price_in_db") ItemWriter<ProductWithDetails> writer,
		            IncreasePriceOfProductWithDetailsProcessor increasePriceProcessor ) {
    var name = "stepDbToDbWithPriceIncrease";
    var builder = new StepBuilder(name, jobRepository);
    return builder
        .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
        .reader(reader)
        .processor(increasePriceProcessor)
        .writer(writer)
        .build();
  }

  // stepDbStatToCsv is defined in step.java.ProductStatDbToCsvStepConfig
}