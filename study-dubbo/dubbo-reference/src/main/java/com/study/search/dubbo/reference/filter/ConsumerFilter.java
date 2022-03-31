package com.study.search.dubbo.reference.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

@Activate(group = Constants.CONSUMER)
public class ConsumerFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (invocation instanceof RpcInvocation) {
            RpcInvocation rpc = (RpcInvocation) invocation;
            rpc.setAttachment("product_type", "api");
            return invoker.invoke(rpc);
        }
        return invoker.invoke(invocation);
    }
}
