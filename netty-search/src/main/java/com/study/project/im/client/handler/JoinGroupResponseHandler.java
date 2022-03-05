package com.study.project.im.client.handler;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.packet.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            LogUtil.info("加入群[" + packet.getGroupId() + "]成功!");
        } else {
            LogUtil.info("加入群[" + packet.getGroupId() + "]失败,原因:" + packet.getReason());
        }
    }
}
