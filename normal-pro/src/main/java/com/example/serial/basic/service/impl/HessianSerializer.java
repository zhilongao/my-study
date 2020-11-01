package com.example.serial.basic.service.impl;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.example.serial.basic.service.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * hessian的序列化方式
 */
public class HessianSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(bos);
        try {
            ho.writeObject(object);
            // 猜测此处应该会将transient修饰的关键字写入
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        HessianInput hi = new HessianInput(bis);
        try {
            return (T)hi.readObject();
            // 此处会读取transient修饰的关键字，然后赋值
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
