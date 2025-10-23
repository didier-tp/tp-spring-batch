package tp.jpaSpringBatch.step;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.jpaSpringBatch.model.ProductStat;
import tp.jpaSpringBatch.model.ProductWithDetails;

@Configuration
@Slf4j
public class ProductStatDbToCsvStepConfig extends MyAbstractStepConfig{

    public static final String MAIN_STEP_NAME = "stepDbStatToCsv";
    public static final String TEMP_STEP_NAME = "stepDbProdToConsole";

  @Bean(name=MAIN_STEP_NAME)
  @Qualifier("dbStatToCsv")
  public Step stepDbStatToCsv(@Qualifier("db-jpa") ItemReader<ProductStat> productStatItemReader,
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

    @Bean(name=TEMP_STEP_NAME)
    @Qualifier("temp")
    public Step stepDbProdToConsole(@Qualifier("db-jpa") ItemReader<ProductWithDetails> productWithDetailsItemReader,
                                     @Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter
    ) {
        var stepBuilder = new StepBuilder(TEMP_STEP_NAME, jobRepository);
        return stepBuilder
                .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
                .reader(productWithDetailsItemReader)
                .writer(productItemWriter)
                .build();
    }
 

}