package com.charles.im.client.handler;


import com.charles.im.common.LogUtil;
import com.charles.im.common.MessageQueue;
import com.charles.im.common.packet.response.MessageResponsePacket;
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
