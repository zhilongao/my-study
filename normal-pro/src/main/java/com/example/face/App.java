package com.example.face;


public class App {
    public static void main(String[] args) {
        // 加载顺序
        // testLoadOrder();

        // finally失效时机
        FinallyExecute.testFinally();
    }



    // 加载顺序
    private static void testLoadOrder() {
        // 1. 父类静态代码块
        // 2. 子类静态代码块
        // 3. 父类普通代码块
        // 4. 父类构造方法
        // 5. 子类普通代码块
        // 6. 子类构造方法
        new ChildrenCls();
    }
}
