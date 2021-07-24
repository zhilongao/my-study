package com.study.server.server.handler;

import com.study.server.common.IDUtil;
import com.study.server.common.Logs;
import com.study.server.common.auth.Session;
import com.study.server.common.auth.SessionUtil;
import com.study.server.common.packet.LoginRequestPacket;
import com.study.server.common.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket packet) throws Exception {
        if (validLogin(packet)) {
            // 1. 维护会话状态
            String userId = IDUtil.randomId();
            String username = packet.getUsername();
            Session session = new Session(userId, username);
            SessionUtil.bindSession(session, ctx.channel());
            // 2. 响应客户端
            LoginResponsePacket response = new LoginResponsePacket();
            response.setUserId(userId);
            response.setUserName(username);
            response.setSuccess(true);
            Logs.info("服务端" + packet + " 登录成功");
            ctx.channel().writeAndFlush(response);
        }
    }

    private boolean validLogin(LoginRequestPacket packet) {
        // todo 校验账号信息
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
    }
}
