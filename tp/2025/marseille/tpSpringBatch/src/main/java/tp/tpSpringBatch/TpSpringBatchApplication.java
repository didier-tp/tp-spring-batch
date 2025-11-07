package tp.tpSpringBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TpSpringBatchApplication implements CommandLineRunner {
    private final JobLauncher jobLauncher;

    @Value("${pp:default_value}") //dans classe de @Configuration
    //pour récupérer la valeur de pp=pepe dans application.properties
    private String pp;

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
        //String defaultJobName = "myHelloWorldJob";
        //String defaultJobName = "fromCsvToConsoleJob";
        //String defaultJobName = "fromCsvToJsonJob";
        //String defaultJobName = "fromCsvToXmlJob";
        //String defaultJobName =  "fromDBToConsoleJob";
        String defaultJobName =  "fromDBToConsoleJobWithPartition";

        System.out.println("pp="+this.pp);

        String jobName = null;
        if(args.length>0) jobName=args[0];
        else jobName=System.getProperty("jobName", defaultJobName);

        Job job = (Job) applicationContext.getBean(jobName);

        String defaultInputFilePath="data/input/csv/products.csv";
        //String defaultInputFilePath="data/input/csv/newDetailsProducts_withOrWithoutErrors.csv";
        String inputFilePath=System.getProperty("inputFilePath", defaultInputFilePath);

        String defaultOutputFilePath="data/output/json/products_2.json";
        //String defaultOutputFilePath="data/output/xml/products_2.xml";
        String outputFilePath=System.getProperty("outputFilePath", defaultOutputFilePath);

        JobParameters jobParameters = new JobParametersBuilder()
                /*Necessary for running several instances of a same job (each jobInstance must have a parameter that changes)*/
                .addString("inputFilePath", inputFilePath)//used by some Reader/Writer
                .addString("outputFilePath", outputFilePath)//used by some Reader/Writer
                .addString("enableUpperCase", System.getProperty("enableUpperCase","true"))//used by SimpleUppercaseProductProcessor
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


