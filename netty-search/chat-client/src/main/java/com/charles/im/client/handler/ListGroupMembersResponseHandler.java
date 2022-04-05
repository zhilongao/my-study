package com.charles.im.client.handler;

import com.charles.im.common.LogUtil;
import com.charles.im.common.packet.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 获取群组handler
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket packet) throws Exception {
        LogUtil.info("群[" + packet.getGroupId() + "]中的人包括 " + packet.getSessionList());
    }
}
