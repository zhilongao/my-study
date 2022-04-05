package com.charles.im.client.handler;


import com.charles.im.common.LogUtil;
import com.charles.im.common.packet.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 加入群组handler
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            LogUtil.info("join group success, groupId:{}", packet.getGroupId());
        } else {
            LogUtil.info("join group failure, groupId:{}, reason:{}", packet.getGroupId(), packet.getReason());
        }
    }
}
