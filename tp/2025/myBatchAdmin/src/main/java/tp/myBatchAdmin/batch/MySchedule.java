package tp.myBatchAdmin.batch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class MySchedule {
    private String seconds="0"; //0-59 or * or ...
    private String minutes="0"; //0-59 or * or ...
    private String hours="0"; //0-23 or * or ...
    private String daysOfMonth="?";//1-31 or ? or * or ...
    private String months="*"; //1-12
    private String daysOfWeek="*"; //0-7 (1=Monday , 5=Friday , 6=Saturday , 0or 7 = Sunday)
    //private String year="*";

    public String asCronString(){
        return this.seconds+" "+ this.minutes + " " + this.hours + " "
                + this.daysOfMonth + " " + this.months+ " " +  this.daysOfWeek;
    }
}
