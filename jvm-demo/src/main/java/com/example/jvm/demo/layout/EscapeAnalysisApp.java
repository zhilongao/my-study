package com.example.jvm.demo.layout;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/12 10:11
 * @since v1.0.0001
 */
public class EscapeAnalysisApp {
    public static void main(String[] args) {
        printPeople();
    }

    private static void printPeople() {
        People p = new People(18, "jack");
        System.err.println(p);
    }

    static class People {
        int age;
        String name;

        public People(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "People{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private static StringBuffer createBuffer1(String s1, String s2) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(s1);
        buffer.append(s2);
        return buffer;
    }

    private static String createStr(String s1, String s2) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(s1);
        buffer.append(s2);
        return buffer.toString();
    }
    // 1. 栈上分配
    // 我们都知道Java中的对象都是在堆上分配的，而垃圾回收机制会回收堆中不再使用的对象，
    // 但是筛选可回收对象，回收对象还有整理内存都需要消耗时间。
    // 如果能够通过逃逸分析确定某些对象不会逃出方法之外，那就可以让这个对象在栈上分配内存，
    // 这样该对象所占用的内存空间就可以随栈帧出栈而销毁，就减轻了垃圾回收的压力。
    // 在一般应用中，如果不会逃逸的局部对象所占的比例很大，如果能使用栈上分配，那大量的对象就会随着方法的结束而自动销毁了。
    //2. 同步消除
    // 线程同步本身比较耗时，如果确定一个变量不会逃逸出线程，无法被其它线程访问到，
    // 那这个变量的读写就不会存在竞争，对这个变量的同步措施可以清除。
    // 3. 标量替换
    // Java虚拟机中的原始数据类型（int，long等数值类型以及reference类型等）都不能再进一步分解，它们就可以称为标量。
    // 相对的，如果一个数据可以继续分解，那它称为聚合量，Java中最典型的聚合量是对象。
    // 如果逃逸分析证明一个对象不会被外部访问，并且这个对象是可分解的，那程序真正执行的时候将可能不创建这个对象，
    // 而改为直接创建它的若干个被这个方法使用到的成员变量来代替。
    // 拆散后的变量便可以被单独分析与优化，可以各自分别在栈帧或寄存器上分配空间，原本的对象就无需整体分配空间了。
    // 栈上分配 同步消除 标量替换

    // 4. 总结
    // 虽然概念上的JVM总是在Java堆上为对象分配空间，但并不是说完全依照概念的描述去实现；
    // 只要最后实现处理的“可见效果”与概念中描述的一直就没问题了。
    // 所以说，“you can cheat as long as you don't get caught”。
    // Java对象在实际的JVM实现中可能在GC堆上分配空间，也可能在栈上分配空间，也可能完全就消失了。
    // 这种行为从Java源码中看不出来，也无法显式指定，只是聪明的JVM自动做的优化而已。
    // 但是逃逸分析会有时间消耗，所以性能未必提升多少，并且由于逃逸分析比较耗时，
    // 目前的实现都是采用不那么准确但是时间压力相对较小的算法来完成逃逸分析，这就可能导致效果不稳定，要慎用。
    // 由于HotSpot虚拟机目前的实现方法导致栈上分配实现起来比较复杂，因为在HotSpot中暂时还没有做这项优化。
    // Reference：Java的reference等于C的指针。
    // 所以，在Java的方法调用中，reference也要复制一份压入堆栈。
    // 在方法中对reference的操作就是对这个reference副本的操作。

    // 相关JVM参数
    // -XX:+DoEscapeAnalysis 开启逃逸分析
    // -XX:+PrintEscapeAnalysis 开启逃逸分析后，可通过此参数查看分析结果。
    // -XX:+EliminateAllocations 开启标量替换
    // -XX:+EliminateLocks 开启同步消除

    // 这个参数好像还不支持
    // -XX:+PrintEliminateAllocations 开启标量替换后，查看标量替换情况。
    // 仅在jvm的调试版本中可用，1.8版本中未发布。

    // cpu的地址总线  数据总线
    // 内存的地址总线 数据总线
    // 数据从内存传送到cpu的寄存器
    // 寄存器中，一个字的右边属于低位，左边属于高位。
    // 如果寄存器的高位与内存的高位相对应，低位与内存的低位相对应，称为小端存储。反之成为大端存储，大部分处理器采用小端存储。

    // 大端存储和小端存储的由来。
}
