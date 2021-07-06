package com.example.stream;

import java.util.stream.IntStream;

public class Test5 {
    public static void main(String[] args) {
        // test1();
        test2();
    }

    private static void test1() {
        int[] nums = {1, 3, 5, 7, 9};
        int sum = 0;
        for (int i : nums) {
            sum += i;
        }
        System.err.println("结果为:" + sum);
    }

    private static void test2() {
        int[] nums = {1, 3, 5, 7, 9};
        int sum = IntStream.of(nums).sum();
        int sum1 = IntStream.of(nums).map(p -> p * 2).sum();
        int sum2 = IntStream.of(nums).map(Test5::compute).sum();
        System.err.println("结果是:" + sum);
        System.err.println(sum1);
        System.err.println(sum2);
    }


    private static int compute(int num) {
        return num + 3;
    }

}
