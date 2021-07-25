package com.study.project.im.client.handler;

import com.study.project.im.common.util.Logs;
import com.study.project.im.common.packet.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            Logs.error("退出群[" + packet.getGroupId() + "]成功!");
        } else {
            Logs.error("退出群[" + packet.getGroupId() + "]失败,原因:" + packet.getReason());
        }
    }
}
