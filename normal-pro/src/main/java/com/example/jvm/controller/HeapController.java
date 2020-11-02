package com.example.jvm.controller;

import com.example.jvm.domain.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/2 11:47
 * @since v1.0.0001
 */
@RestController
public class HeapController {

    public static List<Person> list = new ArrayList<Person>();

    /**
     * 内存中不停的创建对象，模拟堆内存溢出
     * 需要配置参数:-Xmx20M -Xms20M
     * java.lang.OutOfMemoryError: GC overhead limit exceeded
     * @return
     * @throws Exception
     */
    @GetMapping("/heap")
    public String heap() throws Exception{
        System.err.println("req heap");
        while (true) {
            list.add(new Person());
            Thread.sleep(1);
        }
    }

}
