package com.study.search.aop.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.search.aop.search.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/test")
public class IndexController {

    @Autowired
    private OrderService orderService;

    String allUrl = "https://open.feishu.cn/open-apis/authen/v1/index?app_id=cli_a1bdb7de747c500d&redirect_uri=http://4302g45548.vicp.fun/test/index1?companId=100&areaId=101&state=test";



    String feiShuAuthenApi = "https://open.feishu.cn/open-apis/authen/v1/index?app_id=cli_a1bdb7de747c500d&redirect_uri=http://4302g45548.vicp.fun/test/index1";

    /**
     * 二维码连接地址,统一接收,然后分发
     * @param request request
     * @param response response
     * @return
     */
    @ResponseBody
    @GetMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String userAgent = request.getHeader("user-agent");
        String companId = request.getParameter("companId");
        String areaId = request.getParameter("areaId");
        String houseId = request.getParameter("houseId");
        System.err.println(userAgent);
        System.err.println(companId + ":" + areaId + ":" + houseId);
        System.err.println("\n");
        StringBuilder builder = new StringBuilder();
        if (userAgent.contains("Feishu")) {
            builder.append(feiShuAuthenApi);
            Map<String, String> params = new HashMap<>();
            params.put("type", "Feishu");
            params.put("companId", companId);
            params.put("areaId", areaId);
            params.put("houseId", houseId);
            StringBuilder sb = new StringBuilder();
            sb.append("type:").append("Feishu").append(",");
            sb.append("companId:").append(companId).append(",");
            sb.append("areaId:").append(areaId).append(",");
            sb.append("houseId:").append(houseId);
            builder.append("?").append("params").append("=").append(sb.toString());
//            builder.append("&").append("companId").append("=").append(companId);
//            builder.append("&").append("areaId").append("=").append(areaId);
//            builder.append("&").append("houseId").append("=").append(houseId);
            try {
                System.err.println(builder.toString());
                response.sendRedirect(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "feishu";
        } else if (userAgent.contains("wxwork")) {
            return "wxwork";
        } else {
            if (userAgent.contains("clientId:10201")) {
                return "云之家Android端";
            } else if (userAgent.contains("clientId:10200")) {
                return "云之家iOS端";
            } else if (userAgent.contains("clientId:10208")) {
                return "云之家iPad端";
            }
        }
        // todo 云之家如何判断
        return "ok";
    }

    /**
     * 接收来自各个客户端的请求(云之家 飞书 企微)
     * @param request request
     * @return
     */
    @ResponseBody
    @GetMapping("/index1")
    public String index1(HttpServletRequest request) {
        String params = request.getParameter("params");
        String[] split = params.split(",");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            String[] item = split[i].split(":");
            map.put(item[0], item[1]);
        }
        if (map.get("type").equals("Feishu")) {
            String code = request.getParameter("code");
            map.put("code", code);
            System.err.println("飞书");
        }
        return JSONObject.toJSONString(map);
    }

    @ResponseBody
    @GetMapping("/get")
    public String test(HttpServletRequest request, @RequestParam("name") String name) {
        orderService.findList("1001");
        return "get ok";
    }

    @ResponseBody
    @PostMapping("/post")
    public String post(HttpServletRequest request, @RequestBody JSONObject param) {
        System.err.println("--------------start header----------------");
        printHeaders(request);
        System.err.println("---------------start param---------------");
        System.err.println(param);
        System.err.println("--------------- end ---------------");
        return "post ok";
    }

    private void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.err.println(headerName + "-" + headerValue);
        }
    }

}
