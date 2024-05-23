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

import tp.tpSpringBatch.model.Product;
import tp.tpSpringBatch.processor.SimpleUppercaseProductProcessor;

@Configuration
@Profile("!xmlJobConfig")
public class ProductsCsvToJsonJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(ProductsCsvToJsonJobConfig.class);


  @Bean
  public Job fromCsvToJsonJob(@Qualifier("csvToJson") Step step1) {
    var name = "fromCsvToJsonJob";//same as method name for simple mind developper
    var jobBuilder = new JobBuilder(name, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean @Qualifier("csvToJson")
  public Step stepCsvToJson(@Qualifier("csv") ItemReader<Product> productItemReader,
		            @Qualifier("json") ItemWriter<Product> productItemWriter  ,
		            SimpleUppercaseProductProcessor simpleUppercaseProductProcessor) {
    var name = "stepCsvToJson";//same as method name for simple mind developper
    var stepBuilder = new StepBuilder(name, jobRepository);
    return stepBuilder
        .<Product, Product>chunk(5, batchTxManager)
        .reader(productItemReader)
        .processor(simpleUppercaseProductProcessor)
        .writer(productItemWriter)
        .build();
  }
 

}