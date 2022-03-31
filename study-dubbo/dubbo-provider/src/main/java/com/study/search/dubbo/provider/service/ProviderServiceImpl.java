package com.study.search.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.charles.study.common.api.ProviderService;
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
        return message;
    }
}
