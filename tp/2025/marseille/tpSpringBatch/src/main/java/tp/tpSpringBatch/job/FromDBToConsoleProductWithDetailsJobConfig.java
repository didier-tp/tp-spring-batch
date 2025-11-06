package tp.tpSpringBatch.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.tpSpringBatch.model.ProductWithDetails;

@Configuration
public class FromDBToConsoleProductWithDetailsJobConfig extends MyAbstractJobConfig{
    public static final String JOB_NAME="fromDBToConsoleJob";
    public static final String MAIN_STEP_NAME="fromDBToConsoleStep";

    public static final Logger logger = LoggerFactory.getLogger(FromDBToConsoleProductWithDetailsJobConfig.class);

    @Bean(name=JOB_NAME)
    public Job fromCsvToConsoleJob(
            @Qualifier(MAIN_STEP_NAME) Step fromCsvToConsoleStep
    ) {
        return this.buildMySingleStepJob(JOB_NAME, fromCsvToConsoleStep);
    }

    @Bean(name=MAIN_STEP_NAME)
    public Step fromDBToConsoleStep(@Qualifier("db-repository")ItemReader<ProductWithDetails> productItemReader,
                                     @Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter){
        var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
        return stepBuilder
                .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
                .reader(productItemReader)
                .writer(productItemWriter)
                .build();
    }
}
