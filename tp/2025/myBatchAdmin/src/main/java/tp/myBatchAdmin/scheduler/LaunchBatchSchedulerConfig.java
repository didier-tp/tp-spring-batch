package tp.myBatchAdmin.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import tp.myBatchAdmin.batch.BatchEssential;
import tp.myBatchAdmin.batch.MySchedule;
import tp.myBatchAdmin.scheduler.quartzjob.LaunchEssentialBatchJob;
import tp.myBatchAdmin.scheduler.quartzjob.RefreshMonitoringJob;
import tp.myBatchAdmin.service.BatchEssentialService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;


@Configuration
public class LaunchBatchSchedulerConfig {

    @Bean
    public Scheduler myBatchStartedScheduler(SchedulerFactoryBean schedulerFactory) throws SchedulerException {

        Scheduler scheduler = schedulerFactory.getScheduler();

       List<String> batchTitleList = new ArrayList<>();

        File launchDirFile = new File("data/launch");
        File[] files = launchDirFile.listFiles();
        for(File f : files){
            String batchToLaunchFileName= f.getName(); //with .batch.json extension
            String batchTitle = batchToLaunchFileName.substring(0,batchToLaunchFileName.length()-11);
            //System.out.println("*** " + batchTitle);
            batchTitleList.add(batchTitle);

            BatchEssential batchEssential = BatchEssentialService.batchEssentialFromJsonFile(batchTitle);
            MySchedule mySchedule = batchEssential.getScheduling();
            if(mySchedule!=null) {
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("batchTitle", batchTitle);

                JobDetail launchJobDetail = JobBuilder.newJob().ofType(LaunchEssentialBatchJob.class)
                        .storeDurably()
                        .withIdentity("Qrtz_LaunchEssentialBatch_JobDetail_" + batchTitle)
                        .withDescription("Invoke Launch EssentialBatch Job Service for batchTitle=" + batchTitle)
                        .setJobData(jobDataMap)
                        .build();

                Trigger launchJobTrigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity("Qrtz_launchJob_Trigger" + batchTitle /*, "group1" */)
                        //.withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5))
                        //.withSchedule(CronScheduleBuilder.cronSchedule("5 * * ? * *"))
                        .withSchedule(CronScheduleBuilder.cronSchedule(mySchedule.asCronString()))
                        .build();

                scheduler.scheduleJob(launchJobDetail, launchJobTrigger);
            }
        }

        Trigger monitoringTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("Qrtz_Trigger_monitoring" )
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(60))
                .build();

        JobDataMap monitoringJobDataMap = new JobDataMap();
        monitoringJobDataMap.put("batchTitleList", batchTitleList);

        JobDetail monitoringJobDetail = JobBuilder.newJob().ofType(RefreshMonitoringJob.class)
                .storeDurably()
                .withIdentity("Qrtz_MonitoringJobDetail" )
                .withDescription("Invoke Launch RefreshMonitoring Job Service")
                .setJobData(monitoringJobDataMap)
                .build();

        scheduler.scheduleJob(monitoringJobDetail, monitoringTrigger);//améliorable/optimisable en tenant compte de la liste des jobs lancés et pas terminés

        scheduler.start();
        return scheduler;
    }
}
