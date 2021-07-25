package com.study.project.im.client.handler;

import com.study.project.im.common.util.Logs;
import com.study.project.im.common.packet.response.SendGroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SendGroupMessageResponseHandler extends SimpleChannelInboundHandler<SendGroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupMessageResponsePacket packet) throws Exception {
        Logs.error("收到群[" + packet.getFromGroupId() + "]中[" + packet.getFromUser() + "]发来的消息:" + packet.getMessage());
    }
}
