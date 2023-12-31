package com.study.job.task;

import com.study.job.util.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: qingshan
 * @Date: 2018/9/11 17:15
 * @Description: 咕泡学院，只为更好的你
 */
public class SelfTask2 implements BaseJob {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String simpleName = getClass().getSimpleName();
        System.err.println(Thread.currentThread().getName() + " "  + sdf.format(date) + simpleName + " ： ----做技术人的指路明灯，职场生涯的精神导师----");
    }
}
