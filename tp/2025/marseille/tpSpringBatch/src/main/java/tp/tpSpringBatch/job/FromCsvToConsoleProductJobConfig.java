package tp.tpSpringBatch.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.tpSpringBatch.model.Product;

@Configuration
public class FromCsvToConsoleProductJobConfig extends MyAbstractJobConfig{
    public static final String JOB_NAME="fromCsvToConsoleJob";
    public static final String MAIN_STEP_NAME="fromCsvToConsoleStep";

    public static final Logger logger = LoggerFactory.getLogger(FromCsvToConsoleProductJobConfig.class);

    @Bean(name=JOB_NAME)
    public Job fromCsvToConsoleJob(
            @Qualifier(MAIN_STEP_NAME) Step fromCsvToConsoleStep
    ) {
        return this.buildMySingleStepJob(JOB_NAME, fromCsvToConsoleStep);
    }

    @Bean(name=MAIN_STEP_NAME)
    public Step fromCsvToConsoleStep(@Qualifier("csv")ItemReader<Product> productItemReader,
                                     @Qualifier("console") ItemWriter<Product> productItemWriter,
                                     ItemProcessor<Product,Product> productProcessor){
        var stepBuilder = new StepBuilder(MAIN_STEP_NAME, jobRepository);
        return stepBuilder
                .<Product, Product>chunk(5, batchTxManager)
                .reader(productItemReader)
                .processor(productProcessor)
                .writer(productItemWriter)
                .build();
    }
}
