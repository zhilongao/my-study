package com.example.jvm.demo.layout;

public class StackOverFlow {

    private int a = 0;

    public void test() {
        a ++;
        System.err.println(a);
        test();
    }

    public static void main(String[] args) {
        StackOverFlow stackOverFlow = new StackOverFlow();
        stackOverFlow.test();
    }
    // 指针压缩
    // 查看jvm的栈大小
    // java -XX:+PrintFlagsFinal -version | grep ThreadStack
    // jvm默认栈大小 1024k
    // 设置jvm的栈大小
    // 参数 -Xss120k
}
