package tp.myBatchAdmin.basic;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tp.myBatchAdmin.service.MyService;

@Component
public class MySampleJob implements Job {

    private String name = "MySampleJob";

    @Autowired
    private MyService myService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        this.myService.someMethod(context.getFireTime(), this.name);
    }

}
