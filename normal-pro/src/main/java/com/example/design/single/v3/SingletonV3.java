package com.example.design.single.v3;

/**
 * 写点注释吧
 *
 * 为什么枚举类不能够被执行反射攻击
 * 1. 枚举类的实现，其class被反编译后会得到一个abstract修饰的类，故无法实例化。(防止反射攻击)
 * 2. 关于单实例，是在static代码块中，类加载阶段实例化的。(线程安全)
 * 3.
 *
 *
 * @author gaozhilong
 * @date 2020/10/31 16:35
 * @since v1.0.0001
 */
public enum SingletonV3 {
    /**
     * 单例
     */
    INSTANCE;


    public void doSomething() {
        System.out.println("doSomething");
    }
}
