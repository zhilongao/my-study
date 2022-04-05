package com.charles.im.client.handler;


import com.charles.im.common.LogUtil;
import com.charles.im.common.packet.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket packet) throws Exception {
        if (packet.isSuccess()) {
            LogUtil.info("退出群[" + packet.getGroupId() + "]成功!");
        } else {
            LogUtil.info("退出群[" + packet.getGroupId() + "]失败,原因:" + packet.getReason());
        }
    }
}
