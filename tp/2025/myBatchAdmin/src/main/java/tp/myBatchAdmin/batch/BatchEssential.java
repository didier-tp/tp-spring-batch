package tp.myBatchAdmin.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/*
a placer/stocker dans data/monitoring/xyz.monitoring.json
où xyz est le ".title" du BatchEssential
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BatchEssential {
    //title = identifiant d'un batch a lancer regulièrement
     private String title; // lié à "nomJob" + "quelques jobParemeters" et pouvant être lancer régulièrement (via scheduler)
     private String description; //optional description

     private String jobName ; //as in jobRepository DataBase
     private String appName; //name of springBacth app
     private String appURI; //(path or ..., ex: "file:///c:/.../...xxx.jar")
     private Map<String,String> mainJobParameters;       // "mainJobParameters"
     private MySchedule  scheduling; //interpreted by quartz (MySchedule.asCronString())
     //       - ...
}
