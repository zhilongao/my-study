package com.study.project.im.client.handler;

import com.study.project.im.common.util.Logs;
import com.study.project.im.common.auth.Session;
import com.study.project.im.common.auth.SessionUtil;
import com.study.project.im.common.packet.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket packet) throws Exception {
        Logs.error("群创建成功,id为[" + packet.getGroupId() + "]");
        Session session = SessionUtil.getSession(ctx.channel());
        List<String> userNameList = packet.getUserNameList();
        userNameList.remove(session.getUserName());
        Logs.error("群里面有:" + userNameList);
    }
}
