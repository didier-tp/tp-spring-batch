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
import org.springframework.context.annotation.Profile;

import tp.jtaSpringBatch.model.Product;
import tp.jtaSpringBatch.processor.SimpleUppercaseProductProcessor;

@Configuration
@Slf4j
public class ProductsCsvToJsonJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "fromCsvToJsonJob";
    public static final String MAIN_STEP_NAME = "stepCsvToJson";

  @Bean(name=JOB_NAME)
  public Job fromCsvToJsonJob(@Qualifier(MAIN_STEP_NAME) Step step1) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean(name=MAIN_STEP_NAME)
  //@Qualifier("csvToJson")
  public Step stepCsvToJson(@Qualifier("csv") ItemReader<Product> productItemReader,
		            @Qualifier("json") ItemWriter<Product> productItemWriter  ,
		            SimpleUppercaseProductProcessor simpleUppercaseProductProcessor) {
    var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return stepBuilder
        .<Product, Product>chunk(5, batchTxManager)
        .reader(productItemReader)
        .processor(simpleUppercaseProductProcessor)
        .writer(productItemWriter)
        .build();
  }
 

}