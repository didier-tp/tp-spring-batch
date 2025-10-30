package tp.myBatchAdmin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LaunchProcessService {

    public long launchProcess(List<String> commandLineArgsList) {
        long pid = -1;
        log.debug("LaunchProcessService.launchProcess "+ commandLineArgsList);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commandLineArgsList);
        try {
            Process process = builder.start();
            pid = process.pid();
            log.info("pid of process=" +pid);
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return pid;
    }

    public long launchProcess(String commandLineArgs[]) {
        long pid = -1;
        log.debug("LaunchProcessService.launchProcess "+ Arrays.toString(commandLineArgs));
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commandLineArgs);
        try {
            Process process = builder.start();
            pid = process.pid();
            log.info("pid of process=" +pid);
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
        return pid;
    }

    //for first debug (exec path, ...):
    public void startAndWaitingProcess(String commandLineArgs[]) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commandLineArgs);
        boolean isStopped = false;
        try {
            Process process = builder.start();
            System.out.println("pid of process=" + process.pid());
            BufferedReader outputReaderOfSubProcess = new BufferedReader(new InputStreamReader(process.getInputStream()));

            isStopped=process.waitFor(3, TimeUnit.SECONDS);
            if(isStopped) {
                System.out.println("process is terminated");
            }else {
                System.out.println("process not terminated after 3s");
                isStopped=process.waitFor(3, TimeUnit.SECONDS);//re-wait
            }
            if (isStopped) {
                System.out.println("process is stopped with exit value="+process.exitValue());
            }
            System.out.println("std output of process (first line) =" + outputReaderOfSubProcess.readLine());
            System.out.println("stderr of process (first line) =" + process.errorReader().readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
