package com.example.jvm.controller;

import com.example.jvm.service.MyMetaspace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/11/2 16:53
 * @since v1.0.0001
 */
@RestController
public class NonHeapController {
    public static List<Class<?>> list = new ArrayList<Class<?>>();

    /**
     * 不停的在内存中动态创建类，模拟方法区内存溢出
     * 需要设置Metaspace的大小 -XX:MetaspaceSize=50M -XX:MaxMetaspaceSize=50M
     * java.lang.OutOfMemoryError:Metaspace
     * @return
     * @throws Exception
     */
    @GetMapping("/nonHeap")
    public String nonHeap() throws Exception{
        while (true) {
            list.addAll(MyMetaspace.createClasses());
            Thread.sleep(5);
        }
    }
}
