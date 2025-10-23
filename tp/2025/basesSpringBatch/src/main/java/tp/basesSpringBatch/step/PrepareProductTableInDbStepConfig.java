package tp.basesSpringBatch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp.basesSpringBatch.tasklet.InitProductWithDetailsTasklet;

@Configuration
public class PrepareProductTableInDbStepConfig extends MyAbstractStepConfig {

    public static final String PREPARE_STEP_NAME = "prepareProductWithDetailsTableInDbStep";

    @Bean(name=PREPARE_STEP_NAME)
    public Step prepareProductTableInDbStep(InitProductWithDetailsTasklet initProductWithDetailsTaskletBean){
        var stepBuilder = new StepBuilder(PREPARE_STEP_NAME, jobRepository);
        return stepBuilder
                .tasklet(initProductWithDetailsTaskletBean, this.batchTxManager)
                .build();
    }
}
