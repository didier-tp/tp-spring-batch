package tp.tpSpringBatch.step.java;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class MyAbstractStepConfig {
	
	@Autowired
	protected JobRepository jobRepository;
	
	@Autowired /*@Qualifier("batch")*/
	protected PlatformTransactionManager batchTxManager;
	
	//NB:  jobRepository will be useful in Job concrete SubClass to build new Job and new Steps
	//    batchTxManager will be useful in Job concrete SubClass to build new Steps
	
}
