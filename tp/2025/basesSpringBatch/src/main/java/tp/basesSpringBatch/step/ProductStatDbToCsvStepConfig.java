package tp.basesSpringBatch.step;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.basesSpringBatch.model.ProductStat;

@Configuration
@Slf4j
public class ProductStatDbToCsvStepConfig extends MyAbstractStepConfig{

    public static final String MAIN_STEP_NAME = "stepDbStatToCsv";

  @Bean(name=MAIN_STEP_NAME)
  @Qualifier("dbStatToCsv")
  public Step stepDbStatToCsv(@Qualifier("db") ItemReader<ProductStat> productStatItemReader,
		            //@Qualifier("console") ItemWriter<ProductStat> productStatItemWriter 
		            @Qualifier("csv") ItemWriter<ProductStat> productStatItemWriter 
		            ) {
    var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return stepBuilder
        .<ProductStat, ProductStat>chunk(5, batchTxManager)
        .reader(productStatItemReader)
        .writer(productStatItemWriter)
        .build();
  }
 

}