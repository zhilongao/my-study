package com.example.task.quartz.scheduler;

import com.example.task.quartz.job.MyJob1;
import com.example.task.quartz.listener.SelfJobListener;
import com.example.task.quartz.listener.SelfSchedulerListener;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/3 19:18
 * @since v1.0.0001
 */
public class MyScheduler {
    public static void main(String[] args) throws SchedulerException {
        // JobDetail
        String jobName = "job1";
        String jobGroup = "group1";
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("gupao", "只为更好的你");
        jobDataMap.put("moon", 5.21F);
        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class)
                .withIdentity(jobName, jobGroup)
                .setJobData(jobDataMap)
                .build();
        // SimpleScheduleBuilder
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever();
        // Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleScheduleBuilder)
                .build();
        // SchedulerFactory
        SchedulerFactory factory = new StdSchedulerFactory();
        // Scheduler
        Scheduler scheduler = factory.getScheduler();
        // add JobDetail和Trigger到Scheduler
        scheduler.getListenerManager().addSchedulerListener(new SelfSchedulerListener());
        scheduler.getListenerManager().addJobListener(new SelfJobListener());
        scheduler.scheduleJob(jobDetail, trigger);
        // start Scheduler
        scheduler.start();
    }
}
