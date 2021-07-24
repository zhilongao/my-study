package com.study.server.common;

import java.util.UUID;

public class IDUtil {

    public static String randomId() {
        return UUID.randomUUID().toString();
    }

}
