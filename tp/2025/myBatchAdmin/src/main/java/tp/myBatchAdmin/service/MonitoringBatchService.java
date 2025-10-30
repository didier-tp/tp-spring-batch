package tp.myBatchAdmin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.myBatchAdmin.batch.BatchMonitoring;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MonitoringBatchService {

    @Autowired
    private JobOperator jobOperator;// applicationContext.getBean(JobOperator.class);

    @Autowired
    private JobExplorer jobExplorer;// applicationContext.getBean(JobExplorer.class);


    BatchMonitoring getLastMonitorJobExecution(String jobName){
        BatchMonitoring batchMonitoring= new BatchMonitoring();
        return batchMonitoring;
    }

    public List<String> getJobNames(){
        List<String> jobNames = jobExplorer.getJobNames();
        //System.out.println("jobNames=" + jobNames);
        return jobNames;
    }

    public JobInstance getLastJobInstance(String jobName){
        JobInstance lastJobInstance = jobExplorer.getLastJobInstance(jobName);
        //System.out.println("lastJobInstance=" + lastJobInstance);
        return lastJobInstance;
    }

    public JobExecution getLastJobExecution(String jobName){
        JobInstance lastJobInstance = jobExplorer.getLastJobInstance(jobName);
        System.out.println("lastJobInstance=" + lastJobInstance);
        JobExecution lastJobExecution=jobExplorer.getLastJobExecution(lastJobInstance);
        System.out.println("lastJobExecution=" + lastJobExecution);
        System.out.println("executionContext of lastJobExecution=" +
                lastJobExecution.getExecutionContext());
        /*
        var stepExecutions = lastJobExecution.getStepExecutions();
        for(StepExecution stepExecution : stepExecutions) {
            System.out.println("\t stepExecution with exitStatus="+ stepExecution.getExitStatus()
                    +" with status="+ stepExecution.getStatus()+ " and with executionContext="
                    + stepExecution.getExecutionContext());
        }
        */
        return lastJobExecution;
    }

    public List<JobInstance> getJobInstances(String jobName){
        List<JobInstance> jobInstances = new ArrayList<>(); //by default if not found
        try {
            long nbInstances = jobExplorer.getJobInstanceCount(jobName);
            jobInstances = jobExplorer.findJobInstancesByJobName(jobName,0, (int)nbInstances);
        } catch (NoSuchJobException e) {
            //throw new RuntimeException(e);
            //e.printStackTrace();
            System.err.println(e.getMessage());
        }
        System.out.println("jobInstances=" + jobInstances);
        return jobInstances;
    }


}
