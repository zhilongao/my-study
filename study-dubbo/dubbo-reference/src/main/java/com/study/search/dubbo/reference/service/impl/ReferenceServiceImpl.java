package com.study.search.dubbo.reference.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.study.search.dubbo.reference.service.ReferenceService;
import org.charles.study.common.api.ProviderService;

@Service
public class ReferenceServiceImpl implements ReferenceService {

    @Reference
    private ProviderService providerService;

    @Override
    public String reference(String param) {
        String result = providerService.printMessage(param);
        return result;
    }
}
