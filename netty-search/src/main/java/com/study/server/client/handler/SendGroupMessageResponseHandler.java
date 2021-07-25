package com.study.server.client.handler;

import com.study.server.common.Logs;
import com.study.server.common.packet.response.SendGroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SendGroupMessageResponseHandler extends SimpleChannelInboundHandler<SendGroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupMessageResponsePacket packet) throws Exception {
        Logs.error("收到群[" + packet.getFromGroupId() + "]中[" + packet.getFromUser() + "]发来的消息:" + packet.getMessage());
    }
}
