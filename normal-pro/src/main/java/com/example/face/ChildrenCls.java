package com.example.face;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/4 17:40
 * @since v1.0.0001
 */
public class ChildrenCls extends ParentCls {

    static {
        System.out.println("2. ChildrenCls Static Block");
    }

    {
        System.out.println("5. ChildrenCls Code Block");
    }

    public ChildrenCls() {
        System.out.println("6. ChildrenCls Constructor");
    }
}
