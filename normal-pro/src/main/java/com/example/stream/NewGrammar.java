package com.example.stream;


import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * jdk8新语法
 */
public class NewGrammar {
    public static void main(String[] args) {
        // Lambda表达式
        // test1();
        // test2();

        // 函数式接口
        // test3();
        // test4();
        test5();
    }

    /***************************** Lambda表达式 *************************/
    private static void test1() {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.err.println("task execute in " + Thread.currentThread().getName());
            }
        };
        Thread t1 = new Thread(task1);
        t1.start();
    }

    private static void test2() {
        Thread t1 = new Thread(() -> {
            System.err.println("task execute in " + Thread.currentThread().getName());
        });
        t1.start();

        Thread t2 = new Thread(() -> System.err.println("task execute in " + Thread.currentThread().getName()));
        t2.start();
    }


    /****************************  函数式接口 **************************/
    // 消费函数接口->接收入参,无返回值
    public static void test3() {
        Consumer<String> c1 = m -> {
            System.err.println(m + ":" + m);
        };
        c1.accept("aa");
        c1.accept("bb");
    }

    // 断言函数接口->接收入参,返回布尔值类型
    private static void test4() {
        Predicate<Integer> p = i -> i > 0;
        System.err.println(p.test(-9));
        System.err.println(p.test(0));
        System.err.println(p.test(12));
    }

    // 自定义函数接口
    public static void test5() {
        Money money1 = new Money(120000);
        money1.printMoney(p -> "$" + p);
        money1.printMoneyV2(p -> "$$$$" + p);
    }


    @FunctionalInterface
    public static interface FormatMoney {
        String moneyFormat(int money);
    }

    public static class Money{
        private final int value;
        public Money(int value) {
            this.value = value;
        }
        public void printMoney(FormatMoney formatMoney) {
            String money = formatMoney.moneyFormat(this.value);
            System.err.println(money);
        }
        public void printMoneyV2(Function<Integer, String> func) {
            String money = func.apply(this.value);
            System.err.println(money);
        }
    }


    /****************************  流式操作 **************************/

}
