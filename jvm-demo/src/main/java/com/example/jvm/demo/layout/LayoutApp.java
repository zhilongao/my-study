package com.example.jvm.demo.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/11 18:56
 * @since v1.0.0001
 */
public class LayoutApp {

    private int a;

    private LayoutApp layoutApp;

    public static void main(String[] args) {
        LayoutApp d = new LayoutApp();
        System.err.println(ClassLayout.parseInstance(d).toPrintable());
    }
    // 一个空对象  不开启指针压缩，占用16字节  8字节MarkWord 8字节ClassPointer引用
    //             开启指针压缩，占用16字节   8字节MarkWord 4字节的ClassPointer引用  4字节的对齐填充

    // 不开启 8 + 8  + 4 + 8 = 28  + 4 = 32
    // 开启   8 + 4  + 4 + 4 = 20 + 4 = 24
}
