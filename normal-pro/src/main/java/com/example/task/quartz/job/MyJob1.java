package com.example.task.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/3 19:11
 * @since v1.0.0001
 */
public class MyJob1 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        System.out.println( " " + sf.format(date) + " 任务1执行了，" + dataMap.getString("gupao"));
    }
}
