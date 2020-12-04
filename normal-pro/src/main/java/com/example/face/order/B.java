package com.example.face.order;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/4 17:40
 * @since v1.0.0001
 */
public class B extends A {

    public B() {
        System.out.println("class B");
    }

    {
        System.out.println("I'm B class");
    }

    static {
        System.out.println("class B static");
    }
}
