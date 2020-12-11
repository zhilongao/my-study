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

    private double b1;

    private float f1;

    private LayoutApp layoutApp;

    private int[] m;

    public static void main(String[] args) {
        LayoutApp d = new LayoutApp();
        System.err.println(ClassLayout.parseInstance(d).toPrintable());
    }
    // 一个java对象的内存布局
    // 对象头  实例数据  对齐填充
    // 对象头包括:MarkWord(hash值，分代年龄，锁状态标识)       ClassPointer      数组长度
    // 对象头中：MarkWord固定大小8子节
    // 不开启指针压缩时:ClassPointer占用8子节
    // 开启指针压缩时:ClassPointer占用4子节
    // 假设一个空对象，大小计算公式为
    // 对象头 + 实例数据 + 对齐填充
    //(MarkWord + ClassPointer + 数组长度) + (实例数据) +（对齐填充）

    // 开启指针压缩参数: -XX:+UseCompressedOops
    // 关闭指针压缩参数: -XX:-UseCompressedOops

    // 不开启指针压缩: （8 + 8 + 0）+ （0）+ （0） = 16
    // 开启指针压缩: （8 + 4 + 0）+ （0）+ （4）= 16

    // 假设一个对象有int a; LayoutApp layoutApp属性
    // 不开启指针压缩:（8 + 8 + 0）+ (4 + 8) + (4) = 32
    // 开启指针要锁:(8 + 4 + 0) + (4 + 4) + (4) = 24


}
