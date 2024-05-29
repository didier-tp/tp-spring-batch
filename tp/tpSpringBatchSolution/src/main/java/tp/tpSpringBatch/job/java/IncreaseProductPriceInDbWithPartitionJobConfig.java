package tp.tpSpringBatch.job.java;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;

import tp.tpSpringBatch.decider.MyUpdatedCountCheckingDecider;
import tp.tpSpringBatch.model.ProductWithDetails;
import tp.tpSpringBatch.partitionner.NumericColumnRangePartitioner;
import tp.tpSpringBatch.processor.IncreasePriceOfProductWithDetailsProcessor;
import tp.tpSpringBatch.tasklet.PrintMessageTasklet;

@Configuration
@Profile("!xmlJobConfig")
public class IncreaseProductPriceInDbWithPartitionJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(IncreaseProductPriceInDbWithPartitionJobConfig.class);
 
  

  @Bean(name="increaseProductPriceInDbWithPartitionJob")
  public Job increaseProductPriceInDbWithPartitionJob(
		  @Qualifier("dbToDbWithPriceIncreasePartitionManager") Step step1,
		  @Qualifier("dbStatToCsv") Step stepStat) {
    var name = "insertIntoCsvFromDbWithPartitionJob";
    var builder = new JobBuilder(name, jobRepository);
    
    return builder.start(step1)
    		 .next(stepStat)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }
  
  
  @Bean
  public Partitioner myPartitioner(@Qualifier("productdb") DataSource dataSource) {
	  NumericColumnRangePartitioner p = new NumericColumnRangePartitioner();
	  p.setTable("product_with_details");
	  p.setColumn("id");
	  p.setDataSource(dataSource);
	  return p;
  }
  @Bean
  public TaskExecutor myTaskExecutor() {
	  return new org.springframework.core.task.SimpleAsyncTaskExecutor();
  }

  @Bean @Qualifier("dbToDbWithPriceIncreasePartitionManager")
  public Step managerStep(
		  @Qualifier("myTaskExecutor")TaskExecutor taskExecutor,
		  @Qualifier("myPartitioner") Partitioner partitioner,
		  @Qualifier("dbToDbWithPriceIncreasePartitionWorker") Step workerStep) {
	  var builder = new StepBuilder("managerStep", jobRepository);
      return builder
          .partitioner("workerStep", partitioner)
          .step(workerStep)
          .gridSize(4)
          .taskExecutor(taskExecutor)
          .build();
  }

  @Bean @Qualifier("dbToDbWithPriceIncreasePartitionWorker")
  public Step stepDbToDbWithPriceIncreaseWorker(@Qualifier("db_with_partition") ItemReader<ProductWithDetails> reader,
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