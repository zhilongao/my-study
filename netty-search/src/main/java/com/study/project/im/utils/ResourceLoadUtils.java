package com.study.project.im.utils;

import java.io.*;
import java.util.Properties;

public class ResourceLoadUtils {

    public static final String FILE_PATH = "application.properties";

    public static Properties properties = new Properties();

    public static Properties get() {
        if (!properties.isEmpty()) {
            return properties;
        } else {
            synchronized (ResourceLoadUtils.class) {
                if (properties.isEmpty()) {
                    load();
                }
                return properties;
            }
        }
    }

    public static void load() {
        try {
            properties.load(ResourceLoadUtils.class.getClassLoader().getResourceAsStream(FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
