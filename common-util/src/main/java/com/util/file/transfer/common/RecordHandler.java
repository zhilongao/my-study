package com.util.file.transfer.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class RecordHandler {
    /**
     * 查询上传记录
     * @param sourceId sourceId
     * @return
     */
    public FileLogInfo find(Long sourceId) {
        LogsUtil.info("find logfile " + sourceId);
        String basePath = ConfigUtils.get("file.store.base.path");
        try {
            File file = new File(basePath + "\\" + "store.log");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Properties prop = new Properties();
            prop.load(new FileInputStream(file));
            String path = prop.getProperty(String.valueOf(sourceId));
            if (path != null && path.length() > 0) {
                FileLogInfo logInfo = new FileLogInfo();
                logInfo.setId(sourceId);
                logInfo.setPath(path);
                return logInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存上传记录，日后可以改成通过数据库存放
     * @param id
     * @param saveFile
     */
    public void save(Long id, File saveFile) {
        LogsUtil.info("save logfile "+ id);
        try {
            String basePath = ConfigUtils.get("file.store.base.path");
            File file = new File(basePath + "\\" + "store.log");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Properties prop = new Properties();
            prop.load(new FileInputStream(file));
            prop.put(String.valueOf(id), saveFile.getAbsolutePath());
            prop.store(new FileOutputStream(file), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当文件上传完毕，删除记录
     * @param sourceId
     */
    public void delete(long sourceId) {
        LogsUtil.info("delete logfile " + sourceId);
        try {
            String basePath = ConfigUtils.get("file.store.base.path");
            File file = new File(basePath + "\\" + "store.log");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Properties prop = new Properties();
            prop.load(new FileInputStream(file));
            prop.remove(String.valueOf(sourceId));
            prop.store(new FileOutputStream(file), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
