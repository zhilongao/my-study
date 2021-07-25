package com.study.project.im.server.handler;

import com.study.project.im.common.auth.SessionUtil;
import com.study.project.im.common.packet.request.JoinGroupRequestPacket;
import com.study.project.im.common.packet.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler instance = new JoinGroupRequestHandler();

    protected JoinGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket packet) throws Exception {
        // 1. 将当前用户的channel加入到ChannelGroup
        String groupId = packet.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());
        // 2. 构造加群响应发送到客户端
        JoinGroupResponsePacket response = new JoinGroupResponsePacket();
        response.setSuccess(true);
        response.setGroupId(groupId);
        // 3. 发送响应
        ctx.channel().writeAndFlush(response);
    }
}
