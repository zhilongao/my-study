package com.study.server.server.handler;

import com.study.server.common.auth.SessionUtil;
import com.study.server.common.packet.JoinGroupRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket packet) throws Exception {
        // 1.获取groupId
        String groupId = packet.getGroupId();

    }
}
