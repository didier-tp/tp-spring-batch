package tp.jpaSpringBatch.tasklet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractResourceDatabasePopulatorTasklet implements Tasklet {

    private DataSource dataSource;
    private String resourcePath="sql/batch-schema.sql";//by default

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
        rdp.addScript(new ClassPathResource(resourcePath));
        rdp.setContinueOnError(false);
        rdp.setIgnoreFailedDrops(false);
        rdp.execute(dataSource);
        return RepeatStatus.FINISHED;
    }

	public AbstractResourceDatabasePopulatorTasklet(DataSource dataSource, String resourcePath) {
        this.dataSource=dataSource;
        this.resourcePath = resourcePath;
    }


}