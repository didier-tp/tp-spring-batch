package tp.tpSpringBatch.job;

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
import tp.tpSpringBatch.decider.NbLineDecider;
import tp.tpSpringBatch.model.Product;
import tp.tpSpringBatch.processor.SimpleUppercaseProductProcessor;
import tp.tpSpringBatch.tasklet.PrintMessageTasklet;

@Configuration
@Slf4j
public class ProductsCsvToJsonJobConfig extends MyAbstractJobConfig{

    public static final String JOB_NAME = "fromCsvToJsonJob";
    public static final String MAIN_STEP_NAME = "stepCsvToJson";

    @Bean
    public NbLineDecider myNbLineDecider() {
        return new NbLineDecider();
    }

  @Bean(name=JOB_NAME)
  public Job fromCsvToJsonJob( NbLineDecider nbLineDecider,
                              @Qualifier(MAIN_STEP_NAME) Step step1,
                              @Qualifier("messageStep") Step messageStep) {
    var jobBuilder = new JobBuilder(JOB_NAME, jobRepository);
    return jobBuilder
            .start(step1)
            .next(nbLineDecider).on("COMPLETED_BIG").to(messageStep)
            .end()
            //.next(messageStep)
    		//.listener(new JobCompletionNotificationListener())
    		.build();
  }

  @Bean Step messageStep(){
      String stepName="messageStep";
      var stepBuilder = new StepBuilder(stepName, jobRepository);
      return stepBuilder.tasklet(
              new PrintMessageTasklet(">>>> etape1 bien executee avec nbLines >= "+NbLineDecider.BIG_SIZE),
              batchTxManager
      ).build();

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