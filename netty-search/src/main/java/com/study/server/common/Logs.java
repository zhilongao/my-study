package com.study.server.common;

import java.util.Date;

public class Logs {

    public static void info(String message) {
        System.out.println(new Date() + ":" + message);
    }

    public static void error(String message) {
        System.err.println(new Date() + ":" + message);
    }
}
