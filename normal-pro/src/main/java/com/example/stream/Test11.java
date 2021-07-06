package com.example.stream;

import java.util.Random;
import java.util.stream.Stream;

public class Test11 {
    // 1.所有元素是链式调用，一个元素只迭代一次
    // 2.每一个中间操作返回一个新的流，流里面的属性sourceStage指向同一个地方，就是Head
    // 3.有状态操作会把无状态操作截断，单独处理
    // 4.并行环境下，有状态的中间操作不一定能够并行执行
    // 5.parallel sequential也是中间操作，但是不创建流
    public static void main(String[] args) {
        // Stream的运行机制
        Random random = new Random();
        // 随机产生数据
        Stream<Integer> stream = Stream.generate(() -> random.nextInt())
                // 产生500个
                .limit(500)
                // 第一个无状态操作
                .peek(s -> print("peek" + s))
                // 第二个无状态操作
                .filter(s -> {
                    print("filter:" + s);
                    return s > 100000;
                })
                // 第三个无状态操作
                .sorted((i1, i2)-> {
                    print("排序:i1=" + i1 + " i2="+ i2);
                    return i1-i2;
                })
                // 又个无状态操作
                .peek(s -> print("peek3 " + s))
                // 并行操作
                .parallel();
        // 终止操作
        long count = stream.count();
        System.out.println(count);
    }

    private static void print(String message) {
        // System.err.println(message);
        System.err.println(Thread.currentThread().getName() + " " + message);
    }

}
