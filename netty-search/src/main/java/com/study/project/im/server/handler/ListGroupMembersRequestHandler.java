package com.study.project.im.server.handler;

import com.study.project.im.common.auth.Session;
import com.study.project.im.common.auth.SessionUtil;
import com.study.project.im.common.packet.request.ListGroupMembersRequestPacket;
import com.study.project.im.common.packet.response.ListGroupMembersResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler instance = new ListGroupMembersRequestHandler();

    protected ListGroupMembersRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket packet) throws Exception {
        // 1. 获取到ChannelGroup
        String groupId = packet.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        // 2. 构造成员信息
        List<Session> sessionList = new ArrayList<Session>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }
        // 3. 获取信息返回给客户端
        ListGroupMembersResponsePacket response = new ListGroupMembersResponsePacket();
        response.setGroupId(groupId);
        response.setSessionList(sessionList);
        ctx.channel().writeAndFlush(response);
    }
}
