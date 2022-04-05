package com.charles.im.server.handler;

import com.charles.im.common.auth.SessionUtil;
import com.charles.im.common.packet.request.SendGroupMessageRequestPacket;
import com.charles.im.common.packet.response.SendGroupMessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class SendGroupMessageRequestHandler extends SimpleChannelInboundHandler<SendGroupMessageRequestPacket> {

    public static final SendGroupMessageRequestHandler insatnce = new SendGroupMessageRequestHandler();

    protected SendGroupMessageRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupMessageRequestPacket packet) throws Exception {
        String groupId = packet.getGroupId();
        // 1. 构造响应
        SendGroupMessageResponsePacket response = new SendGroupMessageResponsePacket();
        response.setFromGroupId(groupId);
        response.setMessage(packet.getMessage());
        response.setFromUser(SessionUtil.getSession(ctx.channel()));
        // 2. 发送群消息
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(response);
    }
}
