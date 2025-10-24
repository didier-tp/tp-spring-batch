package tp.myQuartzScheduler.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public void someMethod(java.util.Date fireTime, String name) {
        System.out.println("MyService.someMethod called at " + fireTime + " for " + name);
    }
}
