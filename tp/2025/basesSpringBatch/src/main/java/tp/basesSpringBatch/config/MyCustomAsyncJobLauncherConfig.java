package tp.basesSpringBatch.config;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;

@Configuration
public class MyCustomAsyncJobLauncherConfig {

    @Bean(name = "myAsyncJobLauncher")
    @Primary
    public JobLauncher myAsyncJobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
       jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); //run a new thread (virtual or not) for each task
        //jobLauncher.setTaskExecutor(new SyncTaskExecutor()); //by default
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
