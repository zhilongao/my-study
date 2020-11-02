package com.example.design.single;

import com.example.design.single.v2.SingletonV2;
import com.example.design.single.v3.SingletonV3;
import org.springframework.util.SerializationUtils;

import java.lang.reflect.Constructor;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/10/31 16:25
 * @since v1.0.0001
 */
public class App {

    public static void main(String[] args) throws Exception{
        // 反射攻击
        // reflectAttack();
        // 反序列化攻击
        // serialAttack();

        enumTest();
    }

    private static void reflectAttack() throws Exception {
        SingletonV2 singleton1 = SingletonV2.getInstanceV2();
        Constructor<SingletonV2> constructor = SingletonV2.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingletonV2 singleton2 = constructor.newInstance();
        System.err.println(singleton1);
        System.err.println(singleton2);
    }

    private static void serialAttack() {
        SingletonV2 singleton1 = SingletonV2.getInstanceV2();
        byte[] serialize = SerializationUtils.serialize(singleton1);
        SingletonV2 singleton2 = (SingletonV2)SerializationUtils.deserialize(serialize);
        System.err.println(singleton1);
        System.err.println(singleton2);
    }

    private static void enumTest() {
        SingletonV3 instance1 = SingletonV3.INSTANCE;
        SingletonV3 instance2 = SingletonV3.INSTANCE;
        System.err.println(instance1);
        System.err.println(instance2);
        System.err.println(instance1 == instance2);
    }
}
