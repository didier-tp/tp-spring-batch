package tp.basesSpringBatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/*
NB: pour ne pas trop cacher le run automatique de Spring Boot,
 on implémente ici l'interface CommandLineRunner et on surcharge la méthode run()
 */

@SpringBootApplication
@RequiredArgsConstructor //Lombok annotation to generate a spring injection constructor with required arguments (final fields)
public class BasesSpringBatchApplication  implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(BasesSpringBatchApplication.class, args);
	}

    @Override //from CommandLineRunner interface (called automatically)
    public void run(String... args) throws Exception {
        //var defaultJobName = "myHelloWorldJob";
        //var defaultJobName = "fromCsvToConsoleJob";
        var defaultJobName = "fromDetailsCsvToDbJob";

        String jobName = null;
        if(args.length>0)
            jobName=args[0];
        else
            jobName=System.getProperty("jobName", defaultJobName);

        //String defaultInputFilePath="data/input/csv/products.csv";
        String defaultInputFilePath="data/input/csv/newDetailsProducts.csv";
        //String defaultInputFilePath="data/input/csv/newDetailsProducts_withOrWithoutErrors.csv";
        String inputFilePath=System.getProperty("inputFilePath", defaultInputFilePath);

        String defaultOutputFilePath="data/output/xml/products.xml";
        String outputFilePath=System.getProperty("outputFilePath", defaultOutputFilePath);

        System.out.println("****>>> jobName="+jobName +  " inputFilePath=" + inputFilePath + " outputFilePath=" + outputFilePath);

        Job job = (Job) applicationContext.getBean(jobName);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timeStampOfJobInstance", System.currentTimeMillis())//Necessary for running several instances of a same job (each jobInstance must have a parameter that changes)
                .addString("inputFilePath", inputFilePath)//used by some Reader/Writer
                .addString("outputFilePath", outputFilePath)//used by some Reader/Writer
                .addString("enableUpperCase", "true")//used by SimpleUppercaseProductProcessor
                .toJobParameters();
        var jobExecution = jobLauncher.run(job, jobParameters);

        var batchStatus = jobExecution.getStatus();
        while (batchStatus.isRunning()) {
            System.out.println("Job still running...");
            Thread.sleep(5000L);
        }
        System.out.println("Job " + jobName + " is finished ...");

    }
}
