package tp.jpaSpringBatch.job;

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

import tp.jpaSpringBatch.model.ProductWithDetails;
import tp.jpaSpringBatch.tasklet.InitProductWithDetailsTasklet;

@Configuration
@Slf4j
public class DataSetGeneratorJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "generateDbDataSetJob";
    public static final String MAIN_STEP_NAME = "generatorToDb";
    public static final String PREPARE_STEP_NAME = "prepareProductWithDetailsTableInDbStep";
    public static final String DB_ACCESS_TYPE = "db-jpa"; //"db-jpa" or "db-repository"

  @Bean(name=JOB_NAME)
  public Job generateDbDataSetJob(@Qualifier(MAIN_STEP_NAME) Step mainStep,
                                  @Qualifier(PREPARE_STEP_NAME) Step prepareStep) {
    var builder = new JobBuilder(JOB_NAME, jobRepository);
    return builder.start(prepareStep)
            .next(mainStep)
            /*.listener(new JobCompletionNotificationListener())*/.build();
  }



  @Bean(name=MAIN_STEP_NAME) @Qualifier("generatorToDb")
  public Step generatorToDb(@Qualifier("fromMyGeneratorProductWithDetailsReader") ItemReader<ProductWithDetails> reader,
		                    @Qualifier(DB_ACCESS_TYPE) ItemWriter<ProductWithDetails> writer) {
    var builder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
    return builder
        .<ProductWithDetails, ProductWithDetails>chunk(100, batchTxManager)
        .reader(reader)
        .writer(writer)
        .build();
  }
 


}