package com.study.server.client.handler;

import com.study.server.common.Logs;
import com.study.server.common.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {
        String fromUserId = packet.getFromUserId();
        String fromUserName = packet.getFromUserName();
        Logs.error(fromUserId + ":" + fromUserName + " ->" + packet.getMessage());
    }
}
