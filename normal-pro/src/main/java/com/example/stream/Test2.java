package com.example.stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Test2 {
    public static void main(String[] args) {
        // 方法引用
        Consumer<String> c1 = s -> System.err.println(s);
        Consumer<String> c2 = System.err::println;
        c1.accept("hello,world1");
        c2.accept("hello,world2");

        // 静态方法的引用
        Dog dog = new Dog("哮天犬");
        Consumer<Dog> c3 = Dog::bark;
        c3.accept(dog);

        // 成员方法的引用
        Function<Integer, Integer> c4 = dog::eat;
        System.err.println(c4.apply(2));

        // 无参构造方法的引用
        Supplier<Dog> s1 = Dog::new;
        System.err.println(s1.get());

        // 有参构造方法的引用
        Function<String, Dog> d1 = Dog::new;
        System.err.println(d1.apply("jack"));
    }

    public static class Dog {
        private String name = "无名狗";
        private int food = 10;

        public Dog() {

        }

        public Dog(String name) {
            this.name = name;
        }
        public static void bark(Dog dog) {
            System.err.println(dog + "叫了");
        }

        public int eat(int num) {
            System.err.println("吃掉狗粮:" + num);
            this.food -= num;
            return this.food;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}
