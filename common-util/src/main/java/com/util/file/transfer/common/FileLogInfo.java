package com.util.file.transfer.common;

/**
 * @author gaozhilong
 * @date 2021/6/9 16:42
 */
public class FileLogInfo {

    private Long id;

    private String path;

    public FileLogInfo() {

    }

    public FileLogInfo(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
