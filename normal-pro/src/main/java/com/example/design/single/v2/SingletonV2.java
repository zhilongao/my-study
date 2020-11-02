package com.example.design.single.v2;


import java.io.Serializable;

/**
 * 懒汉式单例
 *
 * @author gaozhilong
 * @date 2020/10/31 16:19
 * @since v1.0.0001
 */
public class SingletonV2 implements Serializable {

    private static volatile SingletonV2 singletonV2;

    private SingletonV2() {

    }

    /**
     * 缺点：
     *  1. 线程不安全
     * @return
     */
    public static SingletonV2 getInstanceV1() {
        if (singletonV2 == null) {
            singletonV2 = new SingletonV2();
        }
        return singletonV2;
    }

    /**
     * 双重检查锁实现的单例
     * 缺点：
     *    1. 反射攻击
     *    2. 反序列化攻击
     * @return
     */
    public static SingletonV2 getInstanceV2() {
        if (singletonV2 == null) {
            synchronized (SingletonV2.class) {
                if (singletonV2 == null) {
                    singletonV2 = new SingletonV2();
                }
            }
        }
        return singletonV2;
    }

}
