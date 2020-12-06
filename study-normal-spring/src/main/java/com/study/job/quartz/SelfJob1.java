package com.study.job.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SelfJob1 implements Job {

    private Logger log = LoggerFactory.getLogger(SelfJob1.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("窝窝头一块钱四个："+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
    }
}
