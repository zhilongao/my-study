package com.example.face;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/4 17:39
 * @since v1.0.0001
 */
public class ParentCls {

    static {
        System.out.println("1. ParentCls Static Block");
    }

    {
        System.out.println("3. ParentCls Code Block");
    }

    public ParentCls() {
        System.out.println("4. ParentCls Constructor");
    }
}
