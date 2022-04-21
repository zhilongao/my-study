package com.study.search.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.charles.study.common.api.CallbackApi;
import org.springframework.stereotype.Component;

@Component
@Service
public class CallbackServiceImpl implements CallbackApi {
    @Override
    public String callBack(String params) {
        return "ok";
    }
}
