package com.example.stream;

import java.util.Random;
import java.util.stream.Stream;

public class Test7 {

    public static void main(String[] args) {
        // 流的中间操作
        String message = "my name is 007";
        // 打印每个单词的长度
        Stream.of(message.split(" "))
                // 过滤器
                .filter(s -> s.length() > 2)
                .map(s -> s.length())
                .forEach(System.err::println);

        System.err.println("\n----------------------");
        // flatMap A->B属性(是个集合) 最终得到所有A元素里面的所有B属性集合
        Stream.of(message.split(" "))
                .flatMap(s -> s.chars().boxed())
                .forEach( s -> System.err.print((char)s.intValue() + " "));
        System.err.println("\n==============================");
        // peek的使用 用于debug 是个中间操作 和forEch时终止操作
        Stream.of(message.split(" ")).peek(System.err::println).forEach(System.out::println);

        System.err.println("\n=======================");
        new Random().ints()
                .filter(x -> x > 1000 && x < 10000)
                .limit(10)
                .forEach(System.out::println);
    }

}
