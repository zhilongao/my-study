package com.study.server.client.handler;

import com.study.server.common.Logs;
import com.study.server.common.packet.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket packet) throws Exception {
        Logs.error("群[" + packet.getGroupId() + "]中的人包括 " + packet.getSessionList());
    }
}
