package com.util.file.transfer.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    static Properties prop = new Properties();

    static {
        InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream("file.properties");
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return prop.getProperty(key, "");
    }
}
