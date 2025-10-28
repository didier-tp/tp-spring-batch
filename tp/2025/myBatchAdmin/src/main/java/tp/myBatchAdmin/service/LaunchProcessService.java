package tp.myBatchAdmin.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
//@Sfl4j
public class LaunchProcessService {

    public long launchProcess(String commandLineArgs[]) {
        long pid = -1;
        System.out.println("LaunchProcessService.launchProcess ");
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commandLineArgs);
        try {
            Process process = builder.start();
            pid = process.pid();
            System.out.println("pid of process=" +pid);
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return pid;
    }
}
