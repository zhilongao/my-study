package com.example.stream;


import java.util.function.Function;
import java.util.stream.IntStream;

public class Test1 {
    public static void main(String[] args) {
        // Lambda表达式
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.err.println(Thread.currentThread().getName() + " first execute!");
            }
        };
        Runnable r2 = () -> {
            System.err.println(Thread.currentThread().getName() + " execute");};
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

        // Future与多线程编程



        /*System.err.println("hello world");

        int[] nums = {99, 123, -12, 1, -20, 88, 66};
        int min1 = searchMinV1(nums);
        int min2 = searchMinV2(nums);
        System.err.println(min1);
        System.err.println(min2);

        test1();*/

        /*
        Money money1 = new Money(120000);
        money1.printMoney(p -> {
            return "$" + p;
        });

        money1.printMoneyV2(p -> { return "$$$$" + p; });
        */

        // 断言函数
        /*
        Predicate<Integer> p = i -> i > 0;
        System.err.println(p.test(-9));
        System.err.println(p.test(0));
        System.err.println(p.test(12));
        */

        // 消费函数接口
        // Consumer<String> c1 = m -> System.err.println("%%%%" + m);
        // c1.accept("aa");
        // c1.accept("bb");


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
            System.err.println(formatMoney.moneyFormat(this.value));
        }

        public void printMoneyV2(Function<Integer, String> func) {
            System.err.println(func.apply(this.value));
        }
    }




    public static int searchMinV1(int[] nums) {
        int min = Integer.MAX_VALUE;
        for (int i : nums) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    public static int searchMinV2(int[] nums) {
        // 串行
        //return IntStream.of(nums).min().getAsInt();
        // 并行
        return IntStream.of(nums).parallel().min().getAsInt();
    }

    public static void test1() {
        new Thread(() -> System.err.println("ok")).start();
    }

    public static void test2() {
        Runnable target = () -> System.err.println("ok2");
        new Thread(target).start();
    }


    @FunctionalInterface
    public static interface ComputeInterface {
        int compute(int a);
    }

    public static void test3() {
        ComputeInterface v1 = i -> i * 2;
        ComputeInterface v2 = i -> {return (i * 2);};
    }


}
