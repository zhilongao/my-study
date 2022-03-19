package com.charles.log.api;

public class BaseUtils {

    private static final String DEFAULT_BASE_PATH = "E:\\files\\";

    protected String basePath = DEFAULT_BASE_PATH;

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }
}
