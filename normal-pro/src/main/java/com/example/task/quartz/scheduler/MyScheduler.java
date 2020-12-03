package com.example.task.quartz.scheduler;

import com.example.task.quartz.job.MyJob1;
import org.quartz.JobBuilder;
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
        JobDetail jobDetail = JobBuilder.newJob(MyJob1.class)
                .withIdentity("job1", "group1")
                .usingJobData("gupao", "只为更好的你")
                .usingJobData("moon", 5.21F)
                .build();
        // Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();
        // SchedulerFactory  Scheduler
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        // 绑定关系是1：N
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
