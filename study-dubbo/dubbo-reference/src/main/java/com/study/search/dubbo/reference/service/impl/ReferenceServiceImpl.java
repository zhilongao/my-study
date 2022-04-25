package com.study.search.dubbo.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.service.EchoService;
import com.study.search.dubbo.reference.service.ReferenceService;
import org.charles.study.common.api.ProviderService;

@Service
public class ReferenceServiceImpl implements ReferenceService {

    @Reference
    private ProviderService providerService;

    @Override
    public String reference(String param) {
        // 回声测试
        EchoService echoService = (EchoService) this.providerService;
        Object status = echoService.$echo("OK");
        System.err.println(status);
        String result = this.providerService.printMessage(param);
        return result;
    }
}
