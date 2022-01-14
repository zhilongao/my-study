package com.study.project.im.client.handler;

import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.auth.Session;
import com.study.project.im.common.auth.SessionUtil;
import com.study.project.im.common.packet.response.LoginResponsePacket;
import com.study.project.im.common.util.Logs;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {
        MessageQueue.addLoginRespPacket(packet);// 响应包队列(处理)
        MessageQueue.addLoginPacket(packet);// 登录响应包队列(查询状态)
        if (packet.isSuccess()) {
            // print
            String userId = packet.getUserId();
            String userName = packet.getUserName();
            System.out.println("[" + userName + "]登录成功，userId 为: " + userId);
            // 标识登录成功
            Session session = new Session(userId, userName);
            SessionUtil.bindSession(session, ctx.channel());
            // 选择聊天模式
//            System.err.println("[选择聊天模式]");
//            System.err.println("1:发送消息");
//            System.err.println("2:创建群组");
//            System.err.println("3:加入群组");
//            System.err.println("4:退出群组");
//            System.err.println("5:列出群组成员");
//            System.err.println("6:发送群组消息");
        } else {
            Logs.error("客户端登录失败,原因:" + packet.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logs.error("客户端连接被迫关闭!");
    }
}
