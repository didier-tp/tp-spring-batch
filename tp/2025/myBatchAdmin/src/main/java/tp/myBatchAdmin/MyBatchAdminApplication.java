package tp.myBatchAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import tp.myBatchAdmin.service.LaunchProcessService;

@SpringBootApplication

public class MyBatchAdminApplication {


	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MyBatchAdminApplication.class, args);
        System.out.println("MyBatchAdminApplication started...");
        LaunchProcessService lps = context.getBean(LaunchProcessService.class);
        lps.launchProcess( new String[] { "notepad.exe" } );

	}

}
