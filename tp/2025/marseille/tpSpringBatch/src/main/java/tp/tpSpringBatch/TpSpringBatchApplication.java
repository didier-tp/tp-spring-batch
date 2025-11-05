package tp.tpSpringBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TpSpringBatchApplication implements CommandLineRunner {
    private final JobLauncher jobLauncher;

    private final ApplicationContext applicationContext;

    @Autowired
    public TpSpringBatchApplication(JobLauncher jobLauncher,
                                    ApplicationContext applicationContext) {
        //injection by constructor
        this.jobLauncher = jobLauncher;
        this.applicationContext = applicationContext;
    }

	public static void main(String[] args) {
		SpringApplication.run(TpSpringBatchApplication.class, args);
	}
    @Override //from CommandLineRunner interface (called automatically)
    public void run(String... args) throws Exception {
        Job job = (Job) applicationContext.getBean("fromCsvToConsoleJob");
        //Job job = (Job) applicationContext.getBean("myHelloWorldJob");
        JobParameters jobParameters = new JobParametersBuilder()
                /*Necessary for running several instances of a same job (each jobInstance must have a parameter that changes)*/
                .addLong("timeStampOfJobInstance", System.currentTimeMillis()).toJobParameters();

        var jobExecution = jobLauncher.run(job, jobParameters);
        var batchStatus = jobExecution.getStatus();
        while (batchStatus.isRunning()) {
                    System.out.println("Job still running...");
                    Thread.sleep(5000L);
        }
        System.out.println("Job is finished ...");
    }
}


