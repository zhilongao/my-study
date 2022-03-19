package com.study.project.im.common;

import com.charles.log.api.logback.LogBackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    private static Logger loggerV2;

    static {
        loggerV2 = LogBackUtils.getInstance().getLogger();;
    }

    public static void log(String message) {
        info(message);
    }

    public static void info(String message) {
        loggerV2.info(message);
    }

    public static void error(String message) {
        loggerV2.info(message);
    }


}
