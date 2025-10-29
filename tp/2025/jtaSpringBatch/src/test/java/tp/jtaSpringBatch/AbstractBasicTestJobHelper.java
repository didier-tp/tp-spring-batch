package tp.jtaSpringBatch;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;

@Slf4j
public abstract class AbstractBasicTestJobHelper {

	
	@Autowired
	//no need of @Qualifier("myHelloWorldJob") because only one unique job should be found
	//in @SpringBatchTest configuration (good pratice in V5 , mandatory in SprinBatch V4)
	protected  Job job;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	@Autowired
	protected JobLauncherTestUtils jobLauncherTestUtils;
	
	@Autowired
    protected  JobRepositoryTestUtils jobRepositoryTestUtils;
  
    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    //to override in subClasses
  	public JobParametersBuilder initJobParametersWithBuilder(JobParametersBuilder jobParametersBuilder) {
  		return jobParametersBuilder;
  		//return jobParametersBuilder.addString("paramName", "paramValue")	;
  	}
	
	public JobParameters initJobParameters() {
		JobParametersBuilder jobParametersBuilder = 
		  		 new JobParametersBuilder()
		  				 .addLong("timeStampOfJobInstance", System.currentTimeMillis());
		  		         //Necessary for running several instances of a same job (each jobInstance must have a parameter that changes)
		jobParametersBuilder =  initJobParametersWithBuilder(jobParametersBuilder);//for .addString("paramName", "paramValue")		
		return jobParametersBuilder.toJobParameters();
	}
	
	public void verifSameContentExceptedResultFile(String expectedFilePath, String actualFilePath){
		FileSystemResource expectedResult = new FileSystemResource(expectedFilePath);
	    FileSystemResource actualResult = new FileSystemResource(actualFilePath);
	    //AssertFile.assertFileEquals(expectedResult, actualResult); //deprecated since v5
	    assertThat(actualResult.getFile()).hasSameTextualContentAs(expectedResult.getFile());//via AssertJ
	    log.debug(">>>> expected_file: " + expectedFilePath
	    		  + " and generated_file: " + actualFilePath + " have same content .");
	}
	//to override in subclass
	public void postJobCheckings() {
		//ex: check generated file or else
	}
		

}
