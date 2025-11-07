package tp.tpSpringBatch.decider;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import javax.swing.*;

public class NbLineDecider implements JobExecutionDecider {

    public static final int BIG_SIZE=5;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        //stepExecution as "lastStepExecution" (may be null)
        if(stepExecution==null)
            return new FlowExecutionStatus(ExitStatus.UNKNOWN.toString());

        if(!ExitStatus.FAILED.equals(stepExecution.getExitStatus())
                && stepExecution.getReadCount() >BIG_SIZE)
        return new FlowExecutionStatus("COMPLETED_BIG");
       else
        return new FlowExecutionStatus(stepExecution.getExitStatus().getExitCode().toString());
    }
}
