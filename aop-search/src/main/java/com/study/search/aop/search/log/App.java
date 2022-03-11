package com.study.search.aop.search.log;

import org.slf4j.Logger;

public class App {
    public static void main(String[] args) {
        String basePath = "E:\\files\\";
        String logName = "test-log-1";
        LogUtils instance = LogUtils.getInstance();
        Logger logger = instance.getLogger(basePath, logName);
        // use
        logger.info("info, hello,world1");
        logger.warn("warn, hello,world2");
        logger.error("error, hello,world3");
    }
}
