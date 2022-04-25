package com.charles.dubbo.test.invoke;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.charles.dubbo.test.util.DefaultConfigInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * dubbo的泛化调用(不需要引入服务提供者的依赖,通过GenericService直接调用)
 *
 */
@Service
public class DubboInvokeService {


    public static final String DUBBO_APPLICATION_NAME = "dubbo_invoke_test";

    public static final String ZOOKEEPER_ADDRESS = "zookeeper://%s";

    public Object invoke(String interfaceName, String methodName, String[] parameterTypes, Object[] args,
                         Map<String, String> attachmentMap) {
        // application config
        ApplicationConfig application = new ApplicationConfig();
        application.setName(DUBBO_APPLICATION_NAME);
        // register config
        String zkUrl = ZOOKEEPER_ADDRESS.replaceAll("%s", DefaultConfigInfo.ZK_URL);
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(zkUrl);
        application.setRegistry(registry);
        // reference config
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        // 弱类型接口名
        reference.setInterface(interfaceName);
        // 声明为泛化接口
        reference.setGeneric(true);
        reference.setApplication(application);
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();
        RpcContext.getContext().setAttachments(attachmentMap);
        return genericService.$invoke(methodName, parameterTypes, args);
    }
}
