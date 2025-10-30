package tp.myBatchAdmin.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
a placer/stocker dans data/monitoring/xyz.monitoring.json
où xyz est le ".title" du BatchMonitoring
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BatchMonitoring {
    private String title; // (même que MyBatchEssential)
    private String jobName; // same as in jobRepository  DB
    //private String lastStartDateTime; //as "yyyy-mm-ddThh:MM:SS"
    private String lastStartTimeStamp; //as nb ms since 1970-01-01 GMT
    private String lastEndTimeStamp; // ??? as nb ms since 1970-01-01 GMT
    private String lastJobInstanceId;
    private String lastJobExecutionId;
    private String lastStatus; // (COMPLETED or ...)
}
