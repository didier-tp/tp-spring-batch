package tp.tpSpringBatch.job.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemReaderException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.listener.MySkippedErrorsListener;
import tp.tpSpringBatch.model.ProductWithDetails;

@Configuration
@Profile("!xmlJobConfig")
public class ProductsWithDetailsCsvToDbJobWithSkipsConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(ProductsWithDetailsCsvToDbJobWithSkipsConfig.class);


  @Bean
  public Job fromDetailsCsvToDbWithSkipsJob(@Qualifier("csvToDbWithSkips") Step step1) {
    var name = "fromDetailsCsvToDbWithSkipsJob";//same as method name for simple mind developper
    var jobBuilder = new JobBuilder(name, jobRepository);
    return jobBuilder.start(step1)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean @Qualifier("csvToDbWithSkips")
  public Step stepDetailsCsvToDbWithSkips(@Qualifier("csv") ItemReader<ProductWithDetails> productItemReader,
		            //@Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter  
		            @Qualifier("insert_in_db") ItemWriter<ProductWithDetails> productItemWriter  
		            ) {
    var name = "stepDetailsCsvToDbWithSkips";//same as method name for simple mind developper
    var stepBuilder = new StepBuilder(name, jobRepository);
    return stepBuilder
        .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
        .reader(productItemReader)
        .faultTolerant().skipLimit(5).skip(ItemReaderException.class)
        .listener(new MySkippedErrorsListener())
        .writer(productItemWriter)
        .build();
  }
 

}