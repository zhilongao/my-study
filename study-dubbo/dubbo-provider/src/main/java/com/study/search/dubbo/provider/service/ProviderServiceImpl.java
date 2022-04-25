package com.study.search.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import org.charles.study.common.api.ProviderService;
import org.charles.study.common.params.PayParams;
import org.springframework.stereotype.Component;

/**
 * 对外提供dubbo服务
 */
@Component
@Service
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String printMessage(String message) {
        System.err.println(message);
        return "okok";
    }

    @Override
    public String pay(PayParams params) {
        System.err.println("execute pay method, params=" + JSONObject.toJSONString(params));
        return JSONObject.toJSONString(params);
    }
}
