package tp.myBatchAdmin.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class TestBatchMonitoringService {

    @Autowired
    MonitoringBatchService monitoringBatchService;

    @Test
    public void testGetJobNames(){
        List<String> jobNames = monitoringBatchService.getJobNames();
        assertTrue(jobNames.size()>0);
        log.info("jobNames="+jobNames);
    }

    @Test
    public void testGetLastJobInstance(){
        String jobName="myHelloWorldJob";
        JobInstance lastJobInstance = monitoringBatchService.getLastJobInstance(jobName);
        assertNotNull(lastJobInstance);
        log.info("lastJobInstance="+lastJobInstance);
    }

    @Test
    public void testGetLastJobExecution(){
        String jobName="myHelloWorldJob";
        JobExecution lastJobExecution = monitoringBatchService.getLastJobExecution(jobName);
        assertNotNull(lastJobExecution);
        log.info("lastJobExecution="+lastJobExecution);
    }


}
