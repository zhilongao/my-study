package com.example.task.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/3 19:32
 * @since v1.0.0001
 */
public class SelfJobListener implements JobListener {

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.err.println("SelfJobListener listener: " + jobName + " to be executed");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.err.println("SelfJobListener listener: "+ jobName + " execute vetoed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException e) {
        String jobName = context.getJobDetail().getKey().getName();
        System.err.println("SelfJobListener listener: " + jobName + " was executed");
    }
}
