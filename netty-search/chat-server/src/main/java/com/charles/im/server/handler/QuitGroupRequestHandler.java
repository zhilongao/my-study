package com.charles.im.server.handler;


import com.charles.im.common.LogUtil;
import com.charles.im.common.auth.SessionUtil;
import com.charles.im.common.packet.request.QuitGroupRequestPacket;
import com.charles.im.common.packet.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 退出群组处理器
 */
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler instance = new QuitGroupRequestHandler();

    protected QuitGroupRequestHandler() {

    }


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
        LogUtil.info("quit group success, groupId:{}", groupId);
        ctx.channel().writeAndFlush(response);
    }
}
