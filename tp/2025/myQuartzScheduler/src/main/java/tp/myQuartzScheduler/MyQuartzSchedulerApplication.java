package tp.myQuartzScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyQuartzSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyQuartzSchedulerApplication.class, args);
        System.out.println("MyQuartzSchedulerApplication started...");
	}

}
