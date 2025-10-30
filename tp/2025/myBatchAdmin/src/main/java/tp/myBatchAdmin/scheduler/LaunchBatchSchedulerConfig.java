package tp.myBatchAdmin.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.myBatchAdmin.batch.BatchEssential;
import tp.myBatchAdmin.batch.MySchedule;
import tp.myBatchAdmin.scheduler.quartzjob.LaunchEssentialBatchJob;
import tp.myBatchAdmin.service.BatchEssentialService;

import java.io.File;


@Configuration
public class LaunchBatchSchedulerConfig {

    @Bean
    public Scheduler myBatchStartedScheduler() throws SchedulerException {

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        File launchDirFile = new File("data/launch");
        File[] files = launchDirFile.listFiles();
        for(File f : files){
            String batchToLaunchFileName= f.getName(); //with .batch.json extension
            String batchTitle = batchToLaunchFileName.substring(0,batchToLaunchFileName.length()-11);
            //System.out.println("*** " + batchTitle);

            BatchEssential batchEssential = BatchEssentialService.batchEssentialFromJsonFile(batchTitle);
            MySchedule mySchedule = batchEssential.getScheduling();
            if(mySchedule!=null) {
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put("batchTitle", batchTitle);

                JobDetail jobDetail = JobBuilder.newJob().ofType(LaunchEssentialBatchJob.class)
                        .storeDurably()
                        .withIdentity("Qrtz_LaunchEssentialBatch_JobDetail_" + batchTitle)
                        .withDescription("Invoke Launch EssentialBatch Job Service for batchTitle=" + batchTitle)
                        .setJobData(jobDataMap)
                        .build();

                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity("Qrtz_Trigger" + batchTitle /*, "group1" */)
                        //.withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5))
                        //.withSchedule(CronScheduleBuilder.cronSchedule("5 * * ? * *"))
                        .withSchedule(CronScheduleBuilder.cronSchedule(mySchedule.asCronString()))
                        .build();

                scheduler.scheduleJob(jobDetail, trigger);
            }
        }

        scheduler.start();
        return scheduler;
    }
}
