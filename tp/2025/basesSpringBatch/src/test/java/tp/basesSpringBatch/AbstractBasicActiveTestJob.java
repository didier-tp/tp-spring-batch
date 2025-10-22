package tp.basesSpringBatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;

@Slf4j
public abstract class AbstractBasicActiveTestJob extends AbstractBasicTestJobHelper{
			
	@Test
	public void basicGenericTestJob() throws Exception {
		JobParameters jobParameters = initJobParameters();
		log.debug(">>>> jobName=" + job.getName());
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		log.debug("jobExecution="+jobExecution.toString());
		
		JobInstance actualJobInstance = jobExecution.getJobInstance();
	    assertEquals(job.getName(), actualJobInstance.getJobName());
	    
	    ExitStatus actualJobExitStatus = jobExecution.getExitStatus();
	    assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
	    
	    postJobCheckings();
	}

}
