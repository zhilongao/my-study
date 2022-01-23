package com.study.search.aop.search.skywalking.service.impl;

import com.study.search.aop.search.skywalking.service.UserService;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Trace// @Trace注解可以将方法加入到追踪链路
    @Tags({@Tag(key = "param", value = "arg[0]")})
    @Tag(key = "list", value = "returnedObj")// @Tag或@Tags注解可以为追踪链路添加额外信息
    @Override
    public boolean checkUser(String userId) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
