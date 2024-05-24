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

import tp.tpSpringBatch.model.ProductWithDetails;
import tp.tpSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;

@Configuration
@Profile("!xmlJobConfig")
public class IncreaseProductPriceInDbJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(IncreaseProductPriceInDbJobConfig.class);
 
 

  @Bean(name="increaseProductPriceInDbJob")
  public Job increaseProductPriceInDbJob(@Qualifier("dbToDbWithPriceIncrease") Step step1) {
    var name = "insertIntoCsvFromDbJob";
    var builder = new JobBuilder(name, jobRepository);
    return builder.start(step1)
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

}