package tp.basesSpringBatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import tp.basesSpringBatch.admin.JobExecInfo;

import java.util.Arrays;

/*
NB: pour ne pas trop cacher le run automatique de Spring Boot,
 on implémente ici l'interface CommandLineRunner et on surcharge la méthode run()
 */

@SpringBootApplication
@RequiredArgsConstructor //Lombok annotation to generate a spring injection constructor with required arguments (final fields)
@Slf4j
public class BasesSpringBatchApplication  implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(BasesSpringBatchApplication.class, args);
	}

    @Override //from CommandLineRunner interface (called automatically)
    public void run(String... args) throws Exception {
        log.debug("BasesSpringBatchApplication launched with args =" + Arrays.toString(args));
        var defaultJobName = "myHelloWorldJob"; //OK
        //var defaultJobName = "fromCsvToConsoleJob"; //OK with defaultInputFilePath="data/input/csv/products.csv";
        //var defaultJobName = "fromDetailsCsvToDbJob"; //OK with defaultInputFilePath="data/input/csv/newDetailsProducts.csv";
        //var defaultJobName = "fromCsvToJsonJob"; //ok with defaultInputFilePath="data/input/csv/products.csv";
        //var defaultJobName = "fromCsvToXmlJob";//ok with defaultInputFilePath="data/input/csv/products.csv"; and defaultOutputFilePath="data/output/xml/products.xml";
        //var defaultJobName = "generateDbDataSetJob"; //OK with no input file , no output file but need product_with_details table  in productdb
        //var defaultJobName = "fromDbExtractStatToCsvJob"; //OK with no input file , output file ="data/output/csv/productStats.csv" and need product table in productdb

        String jobName = null;
        if(args.length>0) {
            jobName = args[0];
            log.debug("BasesSpringBatchApplication jobName from args[0] =" + jobName);
        }
        else {
            //jobName = System.getProperty("jobName", defaultJobName);
            jobName = System.getProperty("jobName");
            if(jobName!=null){
                log.debug("BasesSpringBatchApplication jobName from -DjobName=" + jobName);
            }else{
                jobName = defaultJobName;
                log.debug("BasesSpringBatchApplication jobName from defaultJobName=" + jobName);
            }
        }

        String jobExecNotifDirectory = System.getProperty("jobExecNotifDirectory", "data/output/jobExecNotifications");

        //String defaultInputFilePath="data/input/csv/products.csv";
        String defaultInputFilePath="data/input/csv/newDetailsProducts.csv";
        //String defaultInputFilePath="data/input/csv/newDetailsProducts_withOrWithoutErrors.csv";
        String inputFilePath=System.getProperty("inputFilePath", defaultInputFilePath);

        String defaultOutputFilePath="data/output/xml/products.xml";
        String outputFilePath=System.getProperty("outputFilePath", defaultOutputFilePath);

        log.info("****>>> jobName="+jobName +  " inputFilePath=" + inputFilePath + " outputFilePath=" + outputFilePath);

        Job job = (Job) applicationContext.getBean(jobName);

        long defaultTimeStampOfJobInstance = System.currentTimeMillis();
        Long timeStampOfJobInstance = Long.parseLong(System.getProperty("timeStampOfJobInstance", Long.toString(defaultTimeStampOfJobInstance)));
        String myTimeStampString = JobExecInfo.myDateTimeString(timeStampOfJobInstance);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timeStampOfJobInstance", timeStampOfJobInstance)//Necessary for running several instances of a same job (each jobInstance must have a parameter that changes)
                .addString("inputFilePath", inputFilePath)//used by some Reader/Writer
                .addString("outputFilePath", outputFilePath)//used by some Reader/Writer
                .addString("enableUpperCase", System.getProperty("enableUpperCase","true"))//used by SimpleUppercaseProductProcessor
				.addString("productCategoryToIncrease", "aliment")//used by IncreasePriceOfProductWithDetailsProcessor
                .addDouble("increaseRatePct", 5.0)//used by IncreasePriceOfProductWithDetailsProcessor
                .addLong("dataSetSize", 5000L)//used by DataSetGeneratorJob
                .toJobParameters();
        var jobExecution = jobLauncher.run(job, jobParameters);
        String startingJobNotifPath = jobExecNotifDirectory +"/start_"+ jobName + "_"+ myTimeStampString + ".json";
        JobExecInfo.writeJobExecInfoToFile(JobExecInfo.fromJobExecution(jobExecution), startingJobNotifPath);
        //var batchStatus = jobExecution.getStatus();

        while (jobExecution.getStatus().isRunning()) {
            System.out.println("Job still running...");
            Thread.sleep(5000L);
        }
        System.out.println("Job " + jobName + " is finished ...");
        String endingJobNotifPath = jobExecNotifDirectory +"/end_"+ jobName + "_"+ myTimeStampString + ".json";
        JobExecInfo.writeJobExecInfoToFile(JobExecInfo.fromJobExecution(jobExecution), endingJobNotifPath);
    }
}
