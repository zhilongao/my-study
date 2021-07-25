package com.study.server.client.handler;

import com.study.server.common.Logs;
import com.study.server.common.packet.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            Logs.error("加入群[" + packet.getGroupId() + "]成功!");
        } else {
            Logs.error("加入群[" + packet.getGroupId() + "]失败,原因:" + packet.getReason());
        }
    }
}
