package com.example.algorithm.linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/1 20:51
 * @since v1.0.0001
 */
public class App1 {

    public static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
            list.add("hello,world");
        }
    }
}
