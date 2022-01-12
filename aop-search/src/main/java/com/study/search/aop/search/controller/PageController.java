package com.study.search.aop.search.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.fastjson.JSONObject;
import com.study.search.aop.search.excel.IAnalysisEventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EventListener;
import java.util.List;

@RequestMapping("/page")
@Controller
public class PageController {


    @GetMapping("/page")
    @ResponseBody
    public String page() throws IOException {
        System.err.println("execute here");
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\Tencent\\QQ\\Bin\\QQScLauncher.exe");
        return "h1";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        try {



            InputStream is = new BufferedInputStream(file.getInputStream());
            IAnalysisEventListener listener = new IAnalysisEventListener();
            EasyExcel.read(is, listener);


//            ReadWorkbook readWorkbook = new ReadWorkbook();
//            readWorkbook.setInputStream(is);
//            ExcelReader reader = new ExcelReader(readWorkbook);
//            reader.read(new ReadSheet(1));
            List<Object> datas = listener.getDatas();
            return JSONObject.toJSONString(datas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

}
