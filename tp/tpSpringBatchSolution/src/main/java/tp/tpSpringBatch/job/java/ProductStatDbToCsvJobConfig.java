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
import tp.tpSpringBatch.model.ProductStat;
import tp.tpSpringBatch.processor.SimpleUppercaseProductProcessor;

@Configuration
@Profile("!xmlJobConfig")
public class ProductStatDbToCsvJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(ProductStatDbToCsvJobConfig.class);


  @Bean
  public Job fromDbExtractStatToCsvJob(@Qualifier("dbStatToCsv") Step step1) {
    var name = "fromDbExtractStatToCsvJob";//same as method name for simple mind developper
    var jobBuilder = new JobBuilder(name, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  // stepDbStatToCsv now in step.java.ProductStatDbToCsvStepConfig

}