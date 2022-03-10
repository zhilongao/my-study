package com.study.search.aop.search.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.rolling.TriggeringPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogCreate {


    private static final String basePath = "E:\\files\\";

    public static void main(String[] args) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = initLogger("test1", context);
        StatusPrinter.print(context);
        logger.info("info loge");
        logger.warn("warn log");
        logger.error("error log");

        // normal();
    }

    private static void normal() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        FileAppender fileAppender = new FileAppender();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("timestamp");

        String fileName = basePath + logName("info");
        fileAppender.setFile(fileName);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%r %thread %level - %msg%n");
        encoder.start();

        fileAppender.setEncoder(encoder);
        fileAppender.start();

        // attach the rolling file appender to the logger of your choice
        Logger logbackLogger = loggerContext.getLogger("Main");
        ((ch.qos.logback.classic.Logger) logbackLogger).addAppender(fileAppender);



        // log something
        logbackLogger.debug("debug message");
        logbackLogger.info("1111info message");
        logbackLogger.warn("warn message");
        logbackLogger.error("error message");
    }



    public static Logger initLogger(String logName, LoggerContext context) {

        RollingFileAppender infoAppender = initRollingFileAppender(context);
        ch.qos.logback.classic.Logger logger = context.getLogger(logName);
        logger.addAppender(infoAppender);
        return logger;
    }

    public static RollingFileAppender initRollingFileAppender(LoggerContext context) {
        RollingFileAppender fileAppender = new RollingFileAppender();

        String fileName = basePath + "info.log";
        String fnp = fileName + ".%d{yyyy-MM-dd}";
        fileAppender.setContext(context);
        fileAppender.setName("timestamp");
        fileAppender.setFile(fileName);


        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        String pattern = "%d{yyyy-MM-dd HH:mm:ss}|%F|%M|%msg%n";
        encoder.setContext(context);
        encoder.setPattern(pattern);
        encoder.start();
        fileAppender.setEncoder(encoder);
        fileAppender.start();


        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setFileNamePattern(fnp);
        rollingPolicy.setMaxHistory(90);

        TimeBasedRollingPolicy rollingPolicy2 = new TimeBasedRollingPolicy();
        rollingPolicy2.setContext(context);
        rollingPolicy2.setFileNamePattern(fnp);
        rollingPolicy2.setMaxHistory(90);

        fileAppender.setRollingPolicy(rollingPolicy);
        fileAppender.setTriggeringPolicy(rollingPolicy2);
        rollingPolicy.start();
        rollingPolicy2.start();


//        LevelFilter levelFilter = new LevelFilter();
//        levelFilter.setLevel(Level.INFO);
//        levelFilter.setOnMatch(FilterReply.ACCEPT);
//        levelFilter.setOnMismatch(FilterReply.DENY);
//        levelFilter.setContext(context);
//        levelFilter.start();
//        fileAppender.addFilter(levelFilter);

        fileAppender.start();
        return fileAppender;
    }

    public static String logName(String level) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return level + "-" + format.format(new Date()) + ".log";
    }


}
