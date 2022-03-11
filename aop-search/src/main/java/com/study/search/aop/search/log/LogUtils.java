package com.study.search.aop.search.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.*;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    private static final String basePath = "E:\\files\\";

    private static LogUtils instance = new LogUtils();

    public static LogUtils getInstance() {
        return instance;
    }

    private LogUtils() {

    }

    /**
     * 获取Logger,对外提供的方法
     * @return Logger
     */
    public Logger getLogger (String basePath, String logName) {
        // attribute
        String fileInfoName = basePath + "info.log";
        String fileErrorName = basePath + "error.log";
        String infoFnp = basePath + "info-%d{yyyy-MM-dd}.%i.log";
        String errorFnp = basePath + "error-%d{yyyy-MM-dd}.%i.log";
        String pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n";
        String fileSize = "2mb";
        // create
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Appender infoAppender = initInfoAppender(context, fileInfoName, infoFnp, fileSize, pattern);
        Appender errorAppender = initErrorAppender(context, fileErrorName, errorFnp, fileSize, pattern);
        Appender consoleAppender = initConsoleAppender(context, pattern);
        return initLogger(logName, Level.INFO, infoAppender, consoleAppender, errorAppender);
    }

    /**
     * 初始化Logger
     * @param logName logName
     * @param level 日志级别
     * @param appenderList appender列表
     * @return Logger
     */
    private Logger initLogger(String logName, Level level, Appender<ILoggingEvent>... appenderList) {
        Logger logger = LoggerFactory.getLogger(logName);
        ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger)logger;
        logbackLogger.detachAndStopAllAppenders();
        for (Appender appender : appenderList) {
            logbackLogger.addAppender(appender);
        }
        logbackLogger.setLevel(level);
        logbackLogger.setAdditive(false);
        return logbackLogger;
    }

    /**
     * 创建ConsoleLogger
     * @param context context
     * @param pattern pattern
     * @return
     */
    private Appender initConsoleAppender(LoggerContext context, String pattern) {
        ConsoleAppender appender = new ConsoleAppender();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(pattern);
        encoder.start();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.start();
        return appender;
    }

    /**
     * 创建InfoLogger
     * @param context context
     * @param fileName fileName
     * @param fnp fnp
     * @param pattern pattern
     * @return appender
     */
    private Appender initInfoAppender(LoggerContext context, String fileName, String fnp, String fileSize, String pattern) {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<ILoggingEvent>();
        appender.setFile(fileName);
        // 创建policy并启动
        TimeBasedRollingPolicy policy = policyCreateAndStart(fnp, fileSize, appender, context);
        // 创建encoder并启动
        PatternLayoutEncoder encoder = encoderCreateAndStart(context, pattern);
        // 设置levelFilter
        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(Level.INFO);
        levelFilter.setOnMatch(FilterReply.ACCEPT);
        // levelFilter.setOnMismatch(FilterReply.DENY);
        levelFilter.start();
        // appender设置属性并启动
        appender.setRollingPolicy(policy);
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.addFilter(levelFilter);
        appender.setPrudent(false);
        appender.start();
        // 返回appender
        return appender;
    }

    /**
     * 创建ErrorLogger
     * @param context context
     * @param fileName fileName
     * @param fnp fnp
     * @param pattern pattern
     * @return appender
     */
    private Appender initErrorAppender(LoggerContext context, String fileName, String fnp, String fileSize, String pattern) {
        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<ILoggingEvent>();
        appender.setFile(fileName);
        // 创建policy并启动
        TimeBasedRollingPolicy policy = policyCreateAndStart(fnp, fileSize, appender, context);
        // 创建encoder并启动
        PatternLayoutEncoder encoder = encoderCreateAndStart(context, pattern);
        // 设置levelFilter
        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(Level.ERROR);
        levelFilter.setOnMatch(FilterReply.ACCEPT);
        levelFilter.setOnMismatch(FilterReply.DENY);
        levelFilter.start();
        // appender设置属性并启动
        appender.setFile(fileName);
        appender.setRollingPolicy(policy);
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.addFilter(levelFilter);
        appender.setPrudent(false);
        appender.start();
        // 返回appender
        return appender;
    }

    /**
     * 创建policy
     * @param fnp fnp
     * @param appender appender
     * @param context context
     * @return policy
     */
    private TimeBasedRollingPolicy policyCreateAndStart(String fnp, String fileSize, FileAppender appender, LoggerContext context) {
        // 创建policy并启动
        SizeAndTimeBasedRollingPolicy policy = new SizeAndTimeBasedRollingPolicy();
        policy.setFileNamePattern(fnp);
        policy.setParent(appender);
        policy.setContext(context);
        // 文件大于1024kb重新创建新文件
        FileSize maxFileSize = FileSize.valueOf(fileSize);
        policy.setMaxFileSize(maxFileSize);
        // 大于1天的删除
        policy.setMaxHistory(1);
        policy.start();
        return policy;
    }

    /**
     * 创建Encoder
     * @param context context
     * @param pattern pattern
     * @return encoder
     */
    private PatternLayoutEncoder encoderCreateAndStart(LoggerContext context, String pattern) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern(pattern);
        encoder.start();
        return encoder;
    }



    /**
     * 简单的测试
     */
    private static void simpleTest() {
        String fileName = basePath + logName("info");
        // 获取LoggerContext
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 创建FileAppender
        FileAppender fileAppender = new FileAppender();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("timestamp");
        fileAppender.setFile(fileName);
        // 创建Encoder 设置属性并启动
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%r %thread %level - %msg%n");
        encoder.start();
        // appender设置属性并启动
        fileAppender.setEncoder(encoder);
        fileAppender.start();
        // 获取Logger,并为其添加Appender
        Logger logbackLogger = loggerContext.getLogger("Main");
        ((ch.qos.logback.classic.Logger) logbackLogger).addAppender(fileAppender);
        // 打印一些东西
        logbackLogger.debug("debug message");
        logbackLogger.info("1111info message");
        logbackLogger.warn("warn message");
        logbackLogger.error("error message");
    }


    public static String logName(String level) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return level + "-" + format.format(new Date()) + ".log";
    }
}
