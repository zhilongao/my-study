package com.study.server.server.handler;

import com.study.server.common.auth.SessionUtil;
import com.study.server.common.packet.request.QuitGroupRequestPacket;
import com.study.server.common.packet.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket packet) throws Exception {
        // 1. 将channel从ChannelGroup中删除
        String groupId = packet.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());
        // 2. 构造退群响应给到客户端
        QuitGroupResponsePacket response = new QuitGroupResponsePacket();
        response.setSuccess(true);
        response.setGroupId(groupId);
        ctx.channel().writeAndFlush(response);
    }
}
