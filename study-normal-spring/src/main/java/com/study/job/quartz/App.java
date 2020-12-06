package com.study.job.quartz;

import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws SchedulerException {
        // ramTest();
        configTest();
        // dbTest();
    }

    private static void ramTest() throws SchedulerException {
        // 1. 获取容器
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_quartz_ram.xml");
        // 2. 从容器中获取到调度器
        StdScheduler scheduler = (StdScheduler) context.getBean("scheduler");
        // 3. 调度开始
        scheduler.start();
    }

    private static void configTest() throws SchedulerException {
        ApplicationContext context = new AnnotationConfigApplicationContext(QuartzConfig.class);
        StdScheduler scheduler = (StdScheduler) context.getBean("simpleScheduler");
        scheduler.start();
    }

    private static void dbTest() throws SchedulerException {
        // 1. 获取容器
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_quartz_db.xml");
        // 2. 从容器中获取到调度器
        StdScheduler dbScheduler = (StdScheduler) context.getBean("scheduler");
        // 3. 调度开始
        dbScheduler.start();
    }
}
