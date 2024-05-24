package tp.tpSpringBatch.step.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import tp.tpSpringBatch.model.ProductStat;

@Configuration
@Profile("!xmlJobConfig")
public class ProductStatDbToCsvStepConfig extends MyAbstractStepConfig{

  public static final Logger logger = LoggerFactory.getLogger(ProductStatDbToCsvStepConfig.class);


  @Bean @Qualifier("dbStatToCsv")
  public Step stepDbStatToCsv(@Qualifier("db") ItemReader<ProductStat> productStatItemReader,
		            //@Qualifier("console") ItemWriter<ProductStat> productStatItemWriter 
		            @Qualifier("csv") ItemWriter<ProductStat> productStatItemWriter 
		            ) {
    var name = "stepDbStatToCsv";//same as method name for simple mind developper
    var stepBuilder = new StepBuilder(name, jobRepository);
    return stepBuilder
        .<ProductStat, ProductStat>chunk(5, batchTxManager)
        .reader(productStatItemReader)
        .writer(productStatItemWriter)
        .build();
  }
 

}