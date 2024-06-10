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
import tp.tpSpringBatch.model.wrapper.ProductToUpdate;
import tp.tpSpringBatch.processor.IncreasePriceOfProductCategoryDrivenProcessor;

@Configuration
@Profile("!xmlJobConfig")
public class IncreaseProductPriceInDbCategoryDrivenJobConfig extends MyAbstractJobConfig{

  public static final Logger logger = LoggerFactory.getLogger(IncreaseProductPriceInDbCategoryDrivenJobConfig.class);
 

  @Bean(name="increaseProductPriceInDbCategoryDrivenJob")
  public Job increaseProductPriceInDbCategoryDrivenJob(
		  @Qualifier("dbToDbWithPriceIncreaseCategoryDriven") Step step1) {
    var name = "increaseProductPriceInDbCategoryDrivenJob";
    var builder = new JobBuilder(name, jobRepository);
    return builder.start(step1)
    		.build();
  }

  @Bean @Qualifier("dbToDbWithPriceIncreaseCategoryDriven")
  public Step stepDbToDbWithPriceIncreaseCategoryDriven(
		            ItemReader<ProductToUpdate> categoryDrivenReaderWithDelegate,
		            @Qualifier("console") /*@Qualifier("update_price_in_db")*/ ItemWriter<ProductWithDetails> writer,
		            IncreasePriceOfProductCategoryDrivenProcessor increasePriceProcessor ) {
    var name = "stepDbToDbWithPriceIncreaseCategoryDriven";
    var builder = new StepBuilder(name, jobRepository);
    return builder
        .<ProductToUpdate, ProductWithDetails>chunk(5, batchTxManager)
        .reader(categoryDrivenReaderWithDelegate)
        .processor(increasePriceProcessor)
        .writer(writer)
        .build();
  }
}