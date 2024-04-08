package tp.tpSpringBatch.job;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tp.tpSpringBatch.TpSpringBatchApplication;


@SpringBatchTest
@SpringBootTest(classes = { TpSpringBatchApplication.class} )
@ActiveProfiles(profiles = {})
//@ActiveProfiles(profiles = {"xmlJobConfig"})
public class TestHelloWorldJob {
	
	Logger logger = LoggerFactory.getLogger(TestHelloWorldJob.class);
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
		
	@Autowired @Qualifier("myHelloWorldJob")
	private Job job;
	
	@Test
	public void testHelloWorldJob() throws Exception {
		 this.jobLauncherTestUtils.setJob(job);
	     JobExecution jobExecution = jobLauncherTestUtils.launchJob();
	     logger.debug("jobExecution="+jobExecution.toString());
	     assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
	}

}
