package com.example.stream;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test8 {
    public static void main(String[] args) {
        // 终止操作
        // 非短路操作
        String message = "my name is 008";
        message.chars().parallel().forEach(i -> System.err.print((char)i + " "));
        System.err.println("\n==============");

        message.chars().parallel().forEachOrdered(i -> System.err.print((char)i + " "));
        System.err.println("\n==============");

        List<String> list = Stream.of(message.split(" ")).collect(Collectors.toList());
        System.err.println(list);
        System.err.println("\n===============");

        Optional<String> letters = Stream.of(message.split(" ")).reduce((x, y) -> x + "|" + y);
        String reduce = Stream.of(message.split(" ")).reduce("", (x, y) -> x + "|" + y);
        Integer length = Stream.of(message.split(" ")).map(s -> s.length()).reduce(0, (x, y) -> x + y);
        System.err.println(letters.orElse(""));
        System.err.println(reduce);
        System.err.println(length);
        System.err.println("\n=====================");

        Optional<String> max = Stream.of(message.split(" ")).max((s1, s2) -> s1.length() - s2.length());
        System.err.println(max.get());
        System.err.println("\n===============================");

        OptionalInt first = new Random().ints().findFirst();
        System.err.println(first.getAsInt());
        System.err.println("\n===============================");
        // 短路操作

    }
}
