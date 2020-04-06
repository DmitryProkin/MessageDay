package messageDay.repository;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

class HelloJob implements Job
{
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        System.out.println("Hello Quartz!");

    }

}
class SimpleTriggerExample {
    public static void main( String[]args ) throws Exception
    {
        //Quartz 1.6.3
        //JobDetail job = new JobDetail();
        //job.setName("dummyJobName");
        //job.setJobClass(HelloJob.class);
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("dummyJobName", "group1").build();

        //Quartz 1.6.3
        //CronTrigger trigger = new CronTrigger();
        //trigger.setName("dummyTriggerName");
        //trigger.setCronExpression("0/5 **  **  **  **  ?");

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/5 **  **  **  **  ?"))
                .build();

        //schedule it
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);

    }
}