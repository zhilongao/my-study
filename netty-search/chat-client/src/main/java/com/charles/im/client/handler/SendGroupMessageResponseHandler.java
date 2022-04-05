package com.charles.im.client.handler;

import com.charles.im.common.LogUtil;
import com.charles.im.common.packet.response.SendGroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SendGroupMessageResponseHandler extends SimpleChannelInboundHandler<SendGroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupMessageResponsePacket packet) throws Exception {
        LogUtil.info("收到群[" + packet.getFromGroupId() + "]中[" + packet.getFromUser() + "]发来的消息:" + packet.getMessage());
    }
}
