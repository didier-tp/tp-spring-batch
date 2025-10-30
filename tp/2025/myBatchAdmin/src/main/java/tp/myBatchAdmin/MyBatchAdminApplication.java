package tp.myBatchAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import tp.myBatchAdmin.service.LaunchProcessService;
import tp.myBatchAdmin.service.MonitoringBatchService;

@SpringBootApplication

public class MyBatchAdminApplication {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MyBatchAdminApplication.class, args);
        System.out.println("MyBatchAdminApplication started...");
        /*
        MonitoringBatchService monitoringBatchService = context.getBean(MonitoringBatchService.class);
        System.out.println("jobNames="+monitoringBatchService.getJobNames());
         */
	}

}
