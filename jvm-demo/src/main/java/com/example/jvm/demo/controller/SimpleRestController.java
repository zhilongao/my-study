package com.example.jvm.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/12/1 21:20
 * @since v1.0.0001
 */
@RestController
public class SimpleRestController {

    private static List<String> list = new ArrayList<String>();

    @GetMapping("/test")
    public String getMapping() {
        int i = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.add("message1" + i++);
                }
            }
        }).start();
        return "hello";
    }

}
