package com.study.project.im.client.handler;

import com.study.project.im.common.auth.Session;
import com.study.project.im.common.auth.SessionUtil;
import com.study.project.im.common.packet.response.LoginResponsePacket;
import com.study.project.im.common.util.Logs;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            // print
            String userId = packet.getUserId();
            String userName = packet.getUserName();
            System.out.println("[" + userName + "]登录成功，userId 为: " + userId);
            // 标识登录成功
            Session session = new Session(userId, userName);
            SessionUtil.bindSession(session, ctx.channel());
        } else {
            Logs.error("客户端登录失败,原因:" + packet.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logs.error("客户端连接被迫关闭!");
    }
}
