package com.example.design.single.v1;

/**
 * 饿汉式单利
 *
 * @author gaozhilong
 * @date 2020/10/31 16:17
 * @since v1.0.0001
 */
public class SingletonV1 {

    private static SingletonV1 singletonV1 = new SingletonV1();

    private SingletonV1() {

    }
    
    public static SingletonV1 getInstance() {
        return singletonV1;
    }
}
