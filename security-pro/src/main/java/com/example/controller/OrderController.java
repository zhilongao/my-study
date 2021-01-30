package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * 写点注释吧
 *
 * @author gaozhilong
 * @date 2020/10/28 11:19
 * @since v1.0.0001
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @RequestMapping("/order1")
    public String order1() {
        return "order1 .......";
    }

    public static  int index = 0;

    @RequestMapping("/order2")
    public String order2() {
        String path = ClassLoader.getSystemResource("").getPath();
        File file = new File(path + index++ + "file.txt");
        // file.mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println(path);
        return "order2 .......";
    }

    @RequestMapping("/order3")
    public String order3() {
        return "order3 .......";
    }

    //@PreAuthorize(value = "hasRole('CREATE')")
    @RequestMapping("/order4")
    public String order4() {
        return "order4 .......";
    }

}
