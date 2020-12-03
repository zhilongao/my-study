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
public class MyJobListener implements JobListener {
    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        System.out.println( "Method 111111 :"+ "获取到监听器名称：" + name);
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println("Method 222222 :"+ jobName + " ——任务即将执行 ");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println("Method 333333 :"+ jobName + " ——任务被否决 ");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException e) {
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println("Method 444444 :"+ jobName + " ——执行完毕 ");
        System.out.println("------------------");
    }
}
