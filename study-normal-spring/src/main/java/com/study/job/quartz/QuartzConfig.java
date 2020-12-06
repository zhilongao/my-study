package com.study.job.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    public static final String jobName = "printJob";

    public static final String jobGroup = "studyJobGroup";

    public static final String triggerName = "simpleTrigger";

    public static final String triggerGroup = "simpleTriggerGroup";

    @Bean
    public JobDetail printTimeJobDetail() {
        return JobBuilder.newJob(SelfJob1.class)
                .withIdentity(jobName, jobGroup)
                .usingJobData("key1", "val1")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger printTimeJobTrigger(JobDetail jobDetail) {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(triggerName, triggerGroup)
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Bean
    public Scheduler simpleScheduler(Trigger trigger) {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(trigger);
        return scheduler.getScheduler();
    }
}
