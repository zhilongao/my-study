package com.study.server.client.handler;

import com.study.server.common.Logs;
import com.study.server.common.packet.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket packet) throws Exception {
        Logs.error("群创建成功,id为[" + packet.getGroupId() + "]");
        Logs.error("群里面有:" + packet.getUserNameList());
    }
}
