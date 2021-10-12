package com.study.search.aop.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.search.aop.search.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/feishu")
public class IndexController {

    @Autowired
    private OrderService orderService;

    public static final String appId = "cli_a1bdb7de747c500d";

    public static final String redirectUri = "https://silly-fly-59.loca.lt/open/feishu/feiShuAuthLogin";

    public static final Map<String, String> stateMap = new HashMap<>();

/*    @PostMapping("/receive")
    @ResponseBody
    public String index(@RequestBody JSONObject object) {
        System.err.println(object.toJSONString());
        String challenge = object.getString("challenge");
        String type = object.getString("type");
        String token = object.getString("token");
        JSONObject obj = new JSONObject();
        obj.put("challenge", challenge);
        return obj.toJSONString();
    }*/

    @GetMapping(value = "/index1")
    public ModelAndView testM1(@RequestParam Map<String, Object> body) {
        ModelAndView model = new ModelAndView("index");
        return model;
    }

    @GetMapping(name = "同步飞书的组织结构", value = "/updateCompany")
    @ResponseBody
    public String updateCompany(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            // todo 没有token时,是重定向还是抛出异常
            String url = "https://open.feishu.cn/open-apis/authen/v1/index?app_id=" + appId + "&redirect_uri=" + redirectUri;
            try {
                response.sendRedirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // throw new BusinessException("token为空!");
        }
        return "ok";
    }


/*
    @GetMapping(value = "/userList")
    @ResponseBody
    @CrossOrigin
    public String userList(HttpServletRequest request, HttpServletResponse response) {
        List<String> list = new ArrayList<>();
        list.add("message1");
        list.add("message2");
        list.add("message3");
        String ticket = request.getHeader("ticket");
        if (!StringUtils.isEmpty(ticket) && stateMap.containsKey(ticket)) {
            // 获取ticket对应的用户 企业信息

            // 返回数据
            return JSONObject.toJSONString(list);
        }
        // 重定向到授权登录界面,并设置回调
        if (StringUtils.isEmpty(ticket)) {
            try {
                String url = "https://open.feishu.cn/open-apis/authen/v1/index?app_id=" + appId + "&redirect_uri=" +redirectUri;
                response.sendRedirect(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }
*/


    // 飞书的登录回调
    @GetMapping(value = "/testRedirect")
    @ResponseBody
    @CrossOrigin
    public String redirectUrl(@RequestParam String code, @RequestParam String state) {
        System.err.println(code);
        System.err.println(state);
        // 通过code去获取用户信息
        String ticket = generateTicket();
        String userInfo = getUserInfo(ticket);
        stateMap.put(ticket, userInfo);
        JSONObject result = new JSONObject();
        result.put("ticket", ticket);
        result.put("userInfo", userInfo);
        return result.toJSONString();
    }

    public String getUserInfo(String code) {
        // 获取用户信息accessToken refreshToken等
        String userInfoApi = "https://open.feishu.cn/open-apis/authen/v1/access_token";
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod postMethod = HttpMethod.POST;
        return "";
    }

    public static String generateTicket() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
