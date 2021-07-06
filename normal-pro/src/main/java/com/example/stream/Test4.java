package com.example.stream;

import java.util.function.Function;

public class Test4 {
    public static void main(String[] args) {
        // test1();
        test2();
    }

    private static void test1() {
        //级联表达式
        Function<Integer, Function<Integer, Integer>> fnc = x -> y -> x + y;
        System.err.println(fnc.apply(2).apply(3));
    }

    private static void test2() {
        Function<Integer, Function<Integer, Function<Integer, Integer>>> fnc = x -> y -> z -> x + y + z;
        System.err.println(fnc.apply(2).apply(3).apply(5));

        int[] nums = {2, 3, 4};
        Function f = fnc;
        for (int i = 0; i < nums.length; i ++) {
            if (f instanceof Function) {
                Object obj = f.apply(nums[i]);
                if (obj instanceof Function) {
                    f = (Function)obj;
                } else {
                    System.err.println("调用结束,结果为:" + obj);
                }
            }
        }

    }

}
