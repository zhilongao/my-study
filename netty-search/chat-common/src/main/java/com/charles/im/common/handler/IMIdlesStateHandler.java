package com.charles.im.common.handler;

import com.charles.im.common.LogUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IMIdlesStateHandler extends IdleStateHandler {

    private static final int READ_IDLE_TIME = 15;

    public IMIdlesStateHandler() {
        super(READ_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        LogUtil.info(READ_IDLE_TIME + "s not read data, close connection");
        ctx.channel().close();
    }
}
