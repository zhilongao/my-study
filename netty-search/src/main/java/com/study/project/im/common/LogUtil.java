package com.study.project.im.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);
    
    public static void log(String message) {
        info(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.info(message);
    }


}
