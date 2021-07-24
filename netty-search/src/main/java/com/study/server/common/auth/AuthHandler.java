package com.study.server.common.auth;

import com.study.server.common.Logs;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Logs.error("服务端AuthHandler校验!");
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            Logs.info("当前连接登录验证完毕,无需再次验证,AuthHandler被移除!");
        } else {
            Logs.info("无登录验证,强制关闭连接!");
        }
    }
}