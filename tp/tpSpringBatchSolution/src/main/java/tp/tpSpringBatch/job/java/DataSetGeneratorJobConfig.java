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

@Configuration
@Profile("!xmlJobConfig")
public class DataSetGeneratorJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(DataSetGeneratorJobConfig.class);
 

  @Bean(name="generateDbDataSetJob")
  public Job generateDbDataSetJob(@Qualifier("generatorToDb") Step step1) {
    var name = "generateDbDataSetJob";
    var builder = new JobBuilder(name, jobRepository);
    return builder.start(step1)/*.listener(new JobCompletionNotificationListener())*/.build();
  }


  @Bean @Qualifier("generatorToDb")
  public Step generatorToDb(@Qualifier("fromMyGeneratorProductWithDetailsReader") ItemReader<ProductWithDetails> reader,
		                    @Qualifier("insert_in_db") ItemWriter<ProductWithDetails> writer) {
    var name = "generatorToDb";
    var builder = new StepBuilder(name, jobRepository);
    return builder
        .<ProductWithDetails, ProductWithDetails>chunk(100, batchTxManager)
        .reader(reader)
        .writer(writer)
        .build();
  }
 


}