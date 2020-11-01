package com.example.serial.basic;

import com.example.serial.basic.entity.User;
import com.example.serial.basic.service.ISerializer;
import com.example.serial.basic.service.impl.HessianSerializer;
import com.example.serial.basic.service.impl.JavaSerializer;
import com.example.serial.basic.service.impl.XmlSerializer;

public class App {

    public static void main(String[] args) {
        // javaTest();
        // xmlTest();
        hessianTest();
    }

    private static void hessianTest() {
        ISerializer iSerializer = new HessianSerializer();
        User user = new User();
        user.setName("jack-hessian");
        user.setAge(20);
        System.err.println(user);
        System.err.println("---------------------------");
        byte[] data = iSerializer.serialize(user);
        System.err.println("length: " + data.length);
        System.err.println(new String(data));
        System.err.println();
        System.err.println("--------------------------");
        User useRec = iSerializer.deserialize(data);
        System.err.println(useRec);
        System.err.println(user == useRec);
    }

    private static void xmlTest() {
        ISerializer iSerializer = new XmlSerializer();
        User user = new User();
        user.setName("jack-xml");
        user.setAge(19);
        System.err.println(user);
        System.err.println("------------------------------");
        byte[] data = iSerializer.serialize(user);
        System.err.println("length: "  + data.length);
        System.err.println(new String(data));
        System.err.println();
        System.err.println("------------------------------");
        User userRec = iSerializer.deserialize(data);
        System.err.println(userRec);
        System.err.println(user == userRec);
    }

    private static void javaTest() {
        ISerializer iSerializer = new JavaSerializer();
        User user = new User();
        user.setName("jack");
        user.setAge(18);
        System.err.println(user);
        System.err.println("------------------------------");
        byte[] data = iSerializer.serialize(user);
        System.err.println("-------------------------");
        System.err.println(data.length);
        for (int i = 0; i < data.length; i++) {
            System.err.print(data[i] + " ");
        }
        System.err.println("\n");
        System.err.println("-------------------------");
        User userRec = iSerializer.deserialize(data);
        System.err.println(userRec);
        // 反序列化后不是同一个对象
        System.err.println(user == userRec);
    }

}
