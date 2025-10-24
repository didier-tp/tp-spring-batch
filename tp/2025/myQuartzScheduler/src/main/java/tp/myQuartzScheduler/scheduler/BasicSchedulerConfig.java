package tp.myQuartzScheduler.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tp.myQuartzScheduler.basic.MySampleJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class BasicSchedulerConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(MySampleJob.class)
                .storeDurably()
                .withIdentity("Qrtz_Job_Detail")
                .withDescription("Invoke Sample Job service...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Qrtz_Trigger")
                .withDescription("Sample trigger")
                //.withSchedule(simpleSchedule().repeatForever().withIntervalInHours(1))
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5))
                .build();
    }
}
