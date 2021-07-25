package com.study.project.im.server.handler;

import com.study.project.im.common.util.IDUtil;
import com.study.project.im.common.util.Logs;
import com.study.project.im.common.auth.Session;
import com.study.project.im.common.auth.SessionUtil;
import com.study.project.im.common.packet.request.LoginRequestPacket;
import com.study.project.im.common.packet.response.LoginResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler instance = new LoginRequestHandler();

    protected LoginRequestHandler() {

    }

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
            Logs.info("【" + packet.getUsername() + "】 登录成功");
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
