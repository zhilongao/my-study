package com.example.redis.util;

import java.util.ResourceBundle;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/23 9:53
 * @since v1.0.0001
 */
public class ResourceUtil {

    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("redis");
    }

    public static String getKey(String key) {
        return resourceBundle.getString(key);
    }
}
