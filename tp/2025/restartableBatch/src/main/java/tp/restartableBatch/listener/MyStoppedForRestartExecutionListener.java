package tp.restartableBatch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class MyStoppedForRestartExecutionListener implements StepExecutionListener {
	/*
	 * @Override public void beforeStep(StepExecution stepExecution) {
	 * StepExecutionListener.super.beforeStep(stepExecution); }
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
        /*
        System.out.println(">>>> stepExecution status: "+stepExecution.getStatus());//FAILED or COMPLETED
        System.out.println(">>>> stepExecution exit status: "+stepExecution.getExitStatus());//FAILED_or_COMPLETED+exitDescription
        System.out.println(">>>> stepExecution failureExceptions: "+stepExecution.getFailureExceptions());  // [] or [ ..., FlatFileParseException ]
        */
        BatchStatus batchStatus = BatchStatus.COMPLETED;
		if(stepExecution.getFailureExceptions().size()>0)
            batchStatus = BatchStatus.STOPPED;

		//NO RESTART with default status of stepExecution = BatchStatus.COMPLETED
		//RESTART with status = BatchStatus.FAILED or BatchStatus.STOPPED
		//IMPORTANT: job/step RESTART depends of BatchStatus (but not ExitStatus !!!)
        stepExecution.setStatus(batchStatus);
		return StepExecutionListener.super.afterStep(stepExecution);
	}
}