package com.charles.im.client.handler;


import com.charles.im.common.LogUtil;
import com.charles.im.common.auth.Session;
import com.charles.im.common.auth.SessionUtil;
import com.charles.im.common.packet.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * 群组创建handler
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket packet) throws Exception {
        LogUtil.info("create group success,groupId:{}", packet.getGroupId());
        Session session = SessionUtil.getSession(ctx.channel());
        List<String> userNameList = packet.getUserNameList();
        userNameList.remove(session.getUserName());
        LogUtil.info("groupId:{}, userNameList:{}", packet.getGroupId(), userNameList);
    }
}
