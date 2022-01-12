package com.util.excel;

import com.alibaba.excel.metadata.BaseRowModel;
import com.util.excel.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App1 {
    public static void main(String[] args) {
        App1 app = new App1();
       // app.testImport();
        app.testWriter();
    }


    public void testWriter() {
        List<BaseRowModel> users = new ArrayList<>();
        User u1 = new User();
        u1.setUserId(1);
        u1.setUserName("jack");
        u1.setAge(10);
        User u2 = new User();
        u2.setUserId(2);
        u2.setUserName("jack2");
        u2.setAge(11);
        User u3 = new User();
        u3.setUserId(3);
        u3.setUserName("jack3");
        u3.setAge(23);
        users.add(u1);
        users.add(u2);
        users.add(u3);
        File f = new File("D:\\logs\\file\\test.xls");
        OutputStream os = null;
        try {
            os = new FileOutputStream(f);
            ExcelUtils.export(os, User.class, users);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void testImport() {
        File file = new File("D:\\logs\\file\\测试工作表格1.xls");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            SelfImportHandler handler = new SelfImportHandler();
            ExcelUtils.parseExcel(is, handler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
