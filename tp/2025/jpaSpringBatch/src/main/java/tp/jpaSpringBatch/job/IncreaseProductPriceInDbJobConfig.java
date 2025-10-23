package tp.jpaSpringBatch.job;

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

import tp.jpaSpringBatch.model.ProductWithDetails;
import tp.jpaSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;

@Configuration
@Slf4j
public class IncreaseProductPriceInDbJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "increaseProductPriceInDbJob";
    public static final String MAIN_STEP_NAME = "stepDbToDbWithPriceIncrease";
    public static final String TEMP_STEP_NAME = "stepDbProdToConsole";
    public static final String DB_ACCESS_TYPE = "db-repository"; //"db-jpa" or "db-repository"

  @Bean(name=JOB_NAME)
  public Job increaseProductPriceInDbJob(
		  @Qualifier(MAIN_STEP_NAME) Step step1,
		  @Qualifier("dbStatToCsv") Step stepStatToCsv) {

    var builder = new JobBuilder(JOB_NAME, jobRepository);

    return builder.start(step1)
    		.next(stepStatToCsv)//generate Stat in .csv file
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  //@Qualifier("dbToDbWithPriceIncrease")
  public Step stepDbToDbWithPriceIncrease(@Qualifier(DB_ACCESS_TYPE) ItemReader<ProductWithDetails> reader,
		            /*@Qualifier("console")*/ @Qualifier(DB_ACCESS_TYPE) ItemWriter<ProductWithDetails> writer,
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