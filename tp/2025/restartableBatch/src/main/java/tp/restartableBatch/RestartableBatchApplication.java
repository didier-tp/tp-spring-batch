package tp.restartableBatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import tp.restartableBatch.util.MyJobUtilBean;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
//Lombok annotation to generate a spring injection constructor with required arguments (final fields)
public class RestartableBatchApplication implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;
    private final MyJobUtilBean myJobUtil;

	public static void main(String[] args) {
		SpringApplication.run(RestartableBatchApplication.class, args);
	}

    @Override //from CommandLineRunner interface (called automatically)
    public void run(String... args) throws Exception {
        var defaultJobName = "fromCsvToConsoleJobRestartable";
        String jobName = null;
        if(args.length>0)
            jobName=args[0];
        else
            jobName=System.getProperty("jobName", defaultJobName);

        JobExecution mostRecentJobExecution = this.myJobUtil.findAndShowMostRecentJobExecution(jobName);
        if(mostRecentJobExecution!=null &&
                mostRecentJobExecution.getStatus()!=BatchStatus.COMPLETED) {
            System.out.println("==========================================================");
            System.out.println("The most recent job execution is not completed yet. it will be restarted");
            System.out.println("==========================================================");
            this.myJobUtil.restartJobExecution(mostRecentJobExecution);
        }else{
            System.out.println("==========================================================");
            System.out.println("The most recent job execution is completed . lauching a new job instance");
            System.out.println("==========================================================");
            this.runNewJobInstance(jobName);
        }
    }

    public void runNewJobInstance(String jobName) throws Exception {

        String defaultInputFilePath="data/input/csv/newDetailsProducts_withOrWithoutErrors.csv";
        String inputFilePath=System.getProperty("inputFilePath", defaultInputFilePath);


        System.out.println("****>>> jobName="+jobName +  " inputFilePath=" + inputFilePath );

        Job job = (Job) applicationContext.getBean(jobName);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timeStampOfJobInstance", System.currentTimeMillis())//Necessary for running several instances of a same job (each jobInstance must have a parameter that changes)
                .addString("inputFilePath", inputFilePath)//used by some Reader/Writer
                .addString("enableUpperCase", "true")//used by SimpleUppercaseProductProcessor
                .toJobParameters();
        var jobExecution = jobLauncher.run(job, jobParameters);

        var batchStatus = jobExecution.getStatus();
        while (batchStatus.isRunning()) {
            System.out.println("Job still running...");
            Thread.sleep(5000L);
        }
        System.out.println("Job " + jobName + " is finished ... with status=" + batchStatus);

    }






}
