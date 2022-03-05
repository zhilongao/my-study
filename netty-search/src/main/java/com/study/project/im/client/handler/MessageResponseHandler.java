package com.study.project.im.client.handler;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket packet) throws Exception {
        MessageQueue.addRespMessagePacket(packet);
        String fromUserId = packet.getFromUserId();
        String fromUserName = packet.getFromUserName();
        LogUtil.info(fromUserId + ":" + fromUserName + " ->" + packet.getMessage());
    }
}
