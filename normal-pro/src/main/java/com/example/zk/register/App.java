package com.example.zk.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    // 模拟实现注册中心的功能
    public static void main(String[] args) {
        ServiceRegister register = new ServiceRegister();
        String orderService = "com.example.zk.register.OrderServiceRegister";
        String userService = "com.example.zk.register.UserServiceRegister";
        List<String> addresses = new ArrayList<>();
        addresses.add("10.192.39.30:8080");
        addresses.add("10.192.39.31:8080");
        addresses.add("10.192.39.32:8080");
        addresses.add("10.192.39.33:8080");
        for (String address : addresses) {
            register.register(orderService, address);
            register.register(userService, address);
        }
        ServiceLookUp lookUp = new ServiceLookUp();
        String discovery = lookUp.discovery(orderService);
        System.err.println(discovery);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
