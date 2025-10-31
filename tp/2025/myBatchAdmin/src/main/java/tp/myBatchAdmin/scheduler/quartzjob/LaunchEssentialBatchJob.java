package tp.myBatchAdmin.scheduler.quartzjob;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.myBatchAdmin.service.BatchEssentialService;

@Component
public class LaunchEssentialBatchJob implements Job {

    @Autowired
    private BatchEssentialService batchEssentialService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String batchTitle = jobDataMap.get("batchTitle").toString();
        System.out.println("LaunchEssentialBatchJob.execute calling batchEssentialService.launchBatchEssential() with batchTitle=" + batchTitle);
        batchEssentialService.launchBatchEssential(batchTitle);
    }
}
