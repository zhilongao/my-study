package com.charles.im.common.auth;


import com.charles.im.common.LogUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler instance = new AuthHandler();

    protected AuthHandler() {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogUtil.info("服务端AuthHandler校验!");
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
            LogUtil.info("当前连接登录验证完毕,无需再次验证,AuthHandler被移除!");
        } else {
            LogUtil.info("无登录验证,强制关闭连接!");
        }
    }
}
