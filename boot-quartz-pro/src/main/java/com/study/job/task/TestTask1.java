package com.study.job.task;

import com.study.job.util.BaseJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: qingshan
 * @Date: 2018/9/11 16:31
 * @Description: 咕泡学院，只为更好的你
 */
@DisallowConcurrentExecution
public class TestTask1 implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date date = new Date();
        String simpleName = getClass().getSimpleName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.err.println(Thread.currentThread().getName() + " " +sdf.format(date) + simpleName + "： ----咕泡学院，只为更好的你----");
    }
}
