package com.charles.im.common;

import com.charles.log.api.logback.LogBackUtils;
import org.slf4j.Logger;

public class LogUtil {

    private static Logger logger;

    static {
        logger = LogBackUtils.getInstance().getLogger();;
    }

    public static void log(String message) {
        info(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    public static void error(String message) {
        logger.info(message);
    }


}
