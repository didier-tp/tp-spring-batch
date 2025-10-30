package tp.myBatchAdmin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tp.myBatchAdmin.batch.BatchEssential;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BatchEssentialService {

    @Autowired
    private LaunchProcessService launchProcessService;

    public static BatchEssential batchEssentialFromJsonFile(String batchTitle){
        BatchEssential batchEssential = null;
        try {
            ObjectMapper jsonObjectMapper = new ObjectMapper();
            String jobEssentialPath = "data/launch/" + batchTitle + ".batch.json";
            File f = new File(jobEssentialPath);
            batchEssential = jsonObjectMapper.readValue(f, BatchEssential.class);
        }  catch (IOException e) {
           throw new RuntimeException(e);
        }
        return batchEssential;
    }

    public void launchBatchEssential(String batchTitle){
            BatchEssential batchEssential = batchEssentialFromJsonFile(batchTitle);
            log.debug("batchEssential="+batchEssential);
            if(batchEssential.getAppURI().startsWith("file:///")) {
                String jarPath = batchEssential.getAppURI().substring(8);
                log.debug("jarPath=" + jarPath);
                log.debug("jobName=" + batchEssential.getJobName());
                //String[] cmdLineArgs = { "java" ,"-version" };
                //String[] cmdLineArgs = { "java" , "-jar" , jarPath , batchEssential.getJobName()};
                List<String> cmdLineArgs = new ArrayList<>();
                cmdLineArgs.add("java"); //must be in os path

                //ATTENTION: ordre important à respecter pour les arguments de la ligne de commande java:
                //d'abord tous les -DpropName=propValue
                //ensuite -jar pathToJarFile
                //finalement arg0] , exemple : jobName

                if (batchEssential.getMainJobParameters() != null) {
                    for (Map.Entry<String, String> entry : batchEssential.getMainJobParameters().entrySet()) {
                        cmdLineArgs.add("-D" + entry.getKey() + "=" + entry.getValue());
                    }
                }
                cmdLineArgs.add("-jar");
                cmdLineArgs.add(jarPath);
                cmdLineArgs.add(batchEssential.getJobName()); //jobName to launch as main process arg

                log.debug("cmdLineArgs=" + cmdLineArgs);
                launchProcessService.launchProcess(cmdLineArgs);
                //launchProcessService.startAndWaitingProcess(cmdLineArgs);
               }
    }
}
