package com.charles.dubbo.test.search;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.Serialization;
import com.alibaba.dubbo.remoting.transport.CodecSupport;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboInvoker;

public class App1 {

    public static void main(String[] args) {
        URL url = URL.valueOf("dubbo://172.16.1.28:31020/org.charles.study.common.api.ProviderService?anyhost=true&application=dubbo-reference&check=false&codec=dubbo&dubbo=2.6.2&generic=false&heartbeat=60000&interface=org.charles.study.common.api.ProviderService&methods=printMessage,pay&pid=2380&register.ip=172.16.1.28&remote.timestamp=1650433318734&side=consumer&timestamp=1650433344945");
        Serialization serialization = CodecSupport.getSerialization(url);
        String name = serialization.getClass().getName();
        System.err.println(name);
        // com.alibaba.dubbo.common.serialize.hessian2.Hessian2Serialization


        // build req
        com.alibaba.dubbo.remoting.exchange.Request req = new com.alibaba.dubbo.remoting.exchange.Request();
        RpcInvocation inv = new RpcInvocation();
        DubboInvoker invoker = new DubboInvoker<String>(String.class, URL.valueOf(""), null);
        inv.setInvoker(invoker);
        req.setData(inv);
        // build resp
        com.alibaba.dubbo.remoting.exchange.Response resp = new com.alibaba.dubbo.remoting.exchange.Response();



    }
}
