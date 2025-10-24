package tp.restartableBatch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MyJobUtilBean {

    @Autowired
    private JobOperator jobOperator;// applicationContext.getBean(JobOperator.class);

    @Autowired
    private JobExplorer jobExplorer;// applicationContext.getBean(JobExplorer.class);

    public JobExecution findAndShowMostRecentJobExecution(String jobName) {
        JobExecution mostRecentJobExecution=null;
        try {
            List<String> jobNames = jobExplorer.getJobNames();
            System.out.println("jobNames=" + jobNames);
            long nbInstances = jobExplorer.getJobInstanceCount(jobName);
            List<JobInstance> jobInstances = jobExplorer.findJobInstancesByJobName(jobName,
                    0, (int)nbInstances);
            System.out.println("jobInstances=" + jobInstances);

            JobInstance mostRecentJobInstance = jobInstances.get(0);
            System.out.println("mostRecentJobInstance=" + mostRecentJobInstance);

            List<JobExecution>  jobExecutions =jobExplorer.getJobExecutions(mostRecentJobInstance);
            System.out.println("jobExecutions=" + jobExecutions);
            mostRecentJobExecution = jobExecutions.get(0);
            System.out.println("mostRecentJobExecution=" + mostRecentJobExecution);
            System.out.println("executionContext of mostRecentJobExecution=" +
                    mostRecentJobExecution.getExecutionContext());

            var stepExecutions = mostRecentJobExecution.getStepExecutions();
            for(StepExecution stepExecution : stepExecutions) {
                System.out.println("\t stepExecution with exitStatus="+ stepExecution.getExitStatus()
                        +" with status="+ stepExecution.getStatus()+ " and with executionContext="
                        + stepExecution.getExecutionContext());
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return  mostRecentJobExecution;
    }

    public void restartUncompletedJob(String jobName) {
        JobExecution mostRecentJobExecution = this.findAndShowMostRecentJobExecution(jobName);
        if(mostRecentJobExecution!=null)
            this.restartJobExecution(mostRecentJobExecution);
    }

    public void restartJobExecution(JobExecution jobExecution) {
        try {
            System.out.println("**** restartJobExecution ****");
            jobOperator.restart(jobExecution.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
