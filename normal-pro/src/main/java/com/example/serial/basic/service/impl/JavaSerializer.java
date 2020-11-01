package com.example.serial.basic.service.impl;

import com.example.serial.basic.service.ISerializer;

import java.io.*;

/**
 * 使用java原生的序列化方式
 */
public class JavaSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 直接将java对象放到内存
            // ObjectOutputStream oos = new ObjectOutputStream(bos);
            // 也可以将java对象放入到文件中
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user"));
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            // 直接从内存中获取java对象
            // ObjectInputStream ois = new ObjectInputStream(bis);
            // 也可以从文件中读取java对象
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user"));
            return (T)ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
