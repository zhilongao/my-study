package com.charles.log.api.log4j;

import org.apache.log4j.*;


/**
 * 编程的方式使用log4j
 * @author gaozhilong
 * @date 2022/3/15 15:03
 */
public class Log4jUtils {

    private static final String DEFAULT_BASE_PATH = "E:\\files\\";

    private static final String INFO_LOG_FILE = "info-log4j.log";

    private static final String ERROR_LOG_FILE = "error-log4j.log";

    public Logger getLogger(String name) {
        Logger logger = LogManager.getLogger(name);
        Appender consoleAppender = initConsoleAppender();
        Appender infoAppender = initInfoAppender();
        Appender errorAppender = initErrorAppender();
        logger.addAppender(consoleAppender);
        logger.addAppender(infoAppender);
        logger.addAppender(errorAppender);
        return logger;
    }

    private Appender initConsoleAppender() {
        Layout layout = createCommonLayout();
        ConsoleAppender appender = new ConsoleAppender();
        appender.setName("console-appender");
        appender.activateOptions();
        appender.setLayout(layout);
        return appender;
    }

    private Appender initInfoAppender() {
        Layout layout = createCommonLayout();
        RollingFileAppender appender = new RollingFileAppender();
        appender.setAppend(true);
        appender.setName("info-appender");
        appender.setFile(DEFAULT_BASE_PATH + INFO_LOG_FILE);
        appender.setLayout(layout);
        appender.activateOptions();
        return appender;
    }

    private Appender initErrorAppender() {
        Layout layout = createCommonLayout();
        RollingFileAppender appender = new RollingFileAppender();
        appender.setAppend(true);
        appender.setName("error-appender");
        appender.setFile(DEFAULT_BASE_PATH + ERROR_LOG_FILE);
        appender.setLayout(layout);
        appender.activateOptions();
        return appender;
    }

    private Layout createCommonLayout() {
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("[%p] %d{yyyy-MM-dd HH:mm:ss SSS} [%t] %C.%M - %m%n");
        return layout;
    }
}
