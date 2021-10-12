package com.util.file;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/test")
public class UploadController {

    private static final String basePath = "E:\\files\\";

    // form-data
    // body是表单形式,一次可以上传多个文件(接收到的是键值对)
    @GetMapping("/upload1")
    public Object upload1(@RequestParam("name") String name,
                        @RequestParam("age") int age,
                        @RequestParam("file1") MultipartFile file1,
                        @RequestParam("file2") MultipartFile file2) {
        System.err.println("name:" + name);
        System.err.println("age:" + age);
        try {
            printFileInfo(file1);
            String fileName = file1.getOriginalFilename();
            upload(file1.getInputStream(), fileName);
        } catch (Exception e) {
            System.err.println("文件" + file1.getOriginalFilename() + " 上传失败");
            e.printStackTrace();
        }
        try {
            printFileInfo(file2);
            String fileName = file2.getOriginalFilename();
            upload(file2.getInputStream(), fileName);
        } catch (Exception e) {
            System.err.println("文件" + file2.getOriginalFilename() + " 上传失败");
            e.printStackTrace();
        }
        return "ok";
    }

    // binary
    // body是binary二进制形式,一次只能上传一个文件(接收到的是二进制流)
    @GetMapping("/upload2")
    public Object upload2(HttpServletRequest request) {
        String fileName = UUID.randomUUID().toString() + ".txt";
        try {
            upload(request.getInputStream(), fileName);
            return true;
        } catch (Exception e) {
            System.err.println("文件" + fileName + " 上传失败");
            e.printStackTrace();
            return false;
        }
    }

    private void printFileInfo(MultipartFile file) {
        System.err.println("[文件类型] - [{}]"+ file.getContentType());
        System.err.println("[文件名称] - [{}]"+ file.getOriginalFilename());
        System.err.println("[文件大小] - [{}]"+ file.getSize());
    }

    private boolean upload(InputStream is, String fileName) {
        Path dest = Paths.get(basePath + fileName);
        try {
            FileCopyUtils.copy(is, Files.newOutputStream(dest));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
