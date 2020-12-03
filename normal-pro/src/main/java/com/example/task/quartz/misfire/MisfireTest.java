package com.example.task.quartz.misfire;

import org.quartz.SchedulerException;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/3 19:23
 * @since v1.0.0001
 */
public class MisfireTest {
    public static void main(String[] args) throws SchedulerException {
/*
		// JobDetail1
		JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity("job1", "group1").build();

		// Trigger1
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().
						withMisfireHandlingInstructionNowWithExistingCount().
						withIntervalInSeconds(1).
						repeatForever()).build();

		// JobDetail 2
		JobDetail jobDetail2 = JobBuilder.newJob(MyJob3.class).withIdentity("job2", "group1").build();

		// Trigger 2  占用掉5个线程，只占用一次
		Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().
						withIntervalInMilliseconds(1).
						withRepeatCount(5)).build();

		// SchedulerFactory
		SchedulerFactory  factory = new StdSchedulerFactory();

		// Scheduler
		Scheduler scheduler = factory.getScheduler();

		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.scheduleJob(jobDetail2, trigger2);
		scheduler.start();*/

    }
}
