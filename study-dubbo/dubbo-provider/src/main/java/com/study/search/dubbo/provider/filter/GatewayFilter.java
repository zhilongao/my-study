package com.study.search.dubbo.provider.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

@Activate(group = Constants.PROVIDER)
public class GatewayFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.err.println("execute here");
        if (invocation instanceof RpcInvocation) {
            RpcInvocation rpc = (RpcInvocation) invocation;
            String productType = rpc.getAttachment("product_type");
            System.err.println("productType:" + productType);
        }
        return invoker.invoke(invocation);
    }
}
