package tp.tpSpringBatch.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import tp.tpSpringBatch.model.ProductWithDetails;
import tp.tpSpringBatch.partitioner.NumericColumnRangePartitioner;

import javax.sql.DataSource;

@Configuration
public class FromDBToConsoleProductWithDetailsJobWithPartitionConfig extends MyAbstractJobConfig{
    public static final String JOB_NAME="fromDBToConsoleJobWithPartition";
    public static final String MAIN_STEP_NAME="fromDBToConsoleStepWithPartition";

    public static final Logger logger = LoggerFactory.getLogger(FromDBToConsoleProductWithDetailsJobWithPartitionConfig.class);

    @Bean(name=JOB_NAME)
    public Job fromCsvToConsoleJobWithPartition(
            @Qualifier(MAIN_STEP_NAME) Step fromCsvToConsoleStep
    ) {
        return this.buildMySingleStepJob(JOB_NAME, fromCsvToConsoleStep);
    }

    @Bean(name=MAIN_STEP_NAME)
    public Step managerStep(
            @Qualifier("myTaskExecutor")TaskExecutor taskExecutor,
            @Qualifier("myPartitioner") Partitioner partitioner,
            @Qualifier("stepDbToConsoleWorker") Step workerStep) {
        var builder = new StepBuilder("managerStep", jobRepository);
        return builder
                .partitioner("workerStep", partitioner)
                .step(workerStep)
                //.gridSize(4)
                .gridSize(Runtime.getRuntime().availableProcessors())
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean(name="stepDbToConsoleWorker")
    public Step stepDbToConsoleWorker(@Qualifier("db-repository-with-partition")ItemReader<ProductWithDetails> productItemReader,
                                      @Qualifier("console") ItemWriter<ProductWithDetails> productItemWriter) {
        var name = "stepDbToConsoleWorker";
        var builder = new StepBuilder(name, jobRepository);
        return builder
                .<ProductWithDetails, ProductWithDetails>chunk(5, batchTxManager)
                .reader(productItemReader)
                .writer(productItemWriter)
                .build();
    }

    @Bean
    public Partitioner myPartitioner(@Qualifier("productdb") DataSource dataSource) {
        NumericColumnRangePartitioner p = new NumericColumnRangePartitioner();
        p.setTable("product_with_details");
        p.setColumn("id");
        p.setDataSource(dataSource);
        return p;
    }
    @Bean
    public TaskExecutor myTaskExecutor() {
        return new org.springframework.core.task.SimpleAsyncTaskExecutor();
    }
}
