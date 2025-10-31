package tp.myBatchAdmin.scheduler.quartzjob;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.myBatchAdmin.service.BatchEssentialService;
import tp.myBatchAdmin.service.MonitoringBatchService;

import java.util.List;

@Component
@Slf4j
public class RefreshMonitoringJob implements Job {

    @Autowired
    private MonitoringBatchService monitoringBatchService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        List<String> batchTitleList = ( List<String>)jobDataMap.get("batchTitleList");
        log.info("RefreshMonitoringJob.execute calling monitoringBatchService.refreshMonitoring() with batchTitleList=" + batchTitleList);
        for(String batchTitle : batchTitleList) {
            monitoringBatchService.refreshMonitoring(batchTitle);
        }
    }
}
