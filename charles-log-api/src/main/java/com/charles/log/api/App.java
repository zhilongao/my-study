package com.charles.log.api;


import com.charles.log.api.log4j.Log4jUtils;
import com.charles.log.api.logback.LogBackUtils;
import org.slf4j.Logger;

/**
 * @author gaozhilong
 * @date 2022/3/15 14:44
 */
public class App {

    public static void main(String[] args) {
        App app = new App();
        app.logbackPrint();
       // app.log4jPrint();
    }

    public void logbackPrint() {
        Logger logger = LogBackUtils.getInstance().getLogger();;
        logger.info("info, hello,world1");
        logger.warn("warn, hello,world2");
        logger.error("error, hello,world3");
    }

    public void log4jPrint() {
        Log4jUtils utils = new Log4jUtils();
        org.apache.log4j.Logger logger = utils.getLogger("log-01");
        logger.info("hello,world");
        logger.error("error log");
    }


}
