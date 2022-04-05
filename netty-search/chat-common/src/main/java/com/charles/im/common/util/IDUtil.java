package com.charles.im.common.util;

import java.util.UUID;

public class IDUtil {

    public static String randomId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,8);
    }
}
