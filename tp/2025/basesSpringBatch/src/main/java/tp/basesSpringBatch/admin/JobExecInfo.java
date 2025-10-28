package tp.basesSpringBatch.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter @ToString @NoArgsConstructor
public class JobExecInfo {
    private String jobName;
    private String status;
    private String exitStatus;
    private Long jobInstanceId;
    private Long jobExecutionId;
    private Map<String,String> jobParametersMap = new HashMap<>();

    public static JobExecInfo fromJobExecution(org.springframework.batch.core.JobExecution jobExecution) {
        JobExecInfo info = new JobExecInfo();
        info.setJobName(jobExecution.getJobInstance().getJobName());
        info.setStatus(jobExecution.getStatus().toString());
        info.setExitStatus(jobExecution.getExitStatus().getExitCode());
        info.setJobInstanceId(jobExecution.getJobInstance().getInstanceId());
        info.setJobExecutionId(jobExecution.getId());
        jobExecution.getJobParameters().getParameters().forEach((key, value) -> {
            info.getJobParametersMap().put(key, /*value.toString()*/ value.getValue().toString() );
        });
        return info;
    }

    public static void writeJobExecInfoToFile(JobExecInfo info, String filePath) {
        // Implementation to write JobExecInfo to a file (e.g., JSON or CSV format)
        // This is a placeholder for actual file writing logic
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        try {
            jsonObjectMapper.writeValue(new File(filePath), info);
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }
}
