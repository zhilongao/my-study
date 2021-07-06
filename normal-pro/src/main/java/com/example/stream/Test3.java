package com.example.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Test3 {
    public static void main(String[] args) {
        // test1();
        test2();
    }

    private static void test1() {
        String name = "long";
        Consumer<String> c1 = p -> {
            System.err.println(p + name);};
        c1.accept("hello ");
    }

    private static void test2() {
        List<String> list = new ArrayList<String>();
        Consumer<String> c1 = p -> {
            System.err.println(p + list.size());};
        c1.accept("nihao ");
    }

}
