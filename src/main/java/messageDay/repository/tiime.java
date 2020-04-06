package messageDay.repository;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.scheduling.Trigger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;

public class tiime {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    public static void main(String[]args) {

        //method 1
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        System.out.println(date);
        //format timestamp
        System.out.println(sdf.format(timestamp));
        int i =5;
        int x =(int) (Math.random() * ++i);
        System.out.println(x);
        java.sql.Date nows = new java.sql.Date( new java.util.Date().getTime() );
        java.sql.Date tomorrow= new java.sql.Date( nows.getTime() + 24*60*60*1000);
        System.out.println(nows);
        System.out.println(tomorrow);


//        Trigger trigger = new TriggerBuilder
//                .withIdentity("trigger3", "group1")
//                .withSchedule(dailyAtHourAndMinute(10, 42))
//                .forJob(myJobKey)
//                .build();
    }
}
