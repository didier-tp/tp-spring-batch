package tp.tpSpringBatch.decider;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class MyUpdatedCountCheckingDecider implements JobExecutionDecider {
	
	public long minManyUpdated = 2; //2=default value , may be override by jobParameter of same name

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		//stepExecution as "lastStepExecution"
		if(stepExecution==null) return new FlowExecutionStatus(ExitStatus.UNKNOWN.toString());
		
		Long minManyUpdatedJobParameter =  jobExecution.getJobParameters().getLong("minManyUpdated");
		if(minManyUpdatedJobParameter!=null) minManyUpdated = minManyUpdatedJobParameter;
		
		Integer nbAjustedProducts = (Integer) stepExecution.getExecutionContext().get("nbAjustedProducts");
		if(nbAjustedProducts>=minManyUpdated)
			return new FlowExecutionStatus("COMPLETED_WITH_MANY_UPDATED");
		else
			return new FlowExecutionStatus(stepExecution.getExitStatus().getExitCode().toString());
	}
}
