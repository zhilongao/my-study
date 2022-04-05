package com.charles.im.server.handler;


import com.charles.im.common.LogUtil;
import com.charles.im.common.auth.SessionUtil;
import com.charles.im.common.packet.request.CreateGroupRequestPacket;
import com.charles.im.common.packet.response.CreateGroupResponsePacket;
import com.charles.im.common.util.IDUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 群组创建处理器
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler instance = new CreateGroupRequestHandler();

    protected CreateGroupRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) throws Exception {
        Set<String> userIdList = packet.getUserIdSet();
        List<String> userNameList = new ArrayList<>();
        List<String> groupUserIds = new ArrayList<>();
        // 创建一个channel分组 筛选待加入的用户
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                groupUserIds.add(userId);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        // 群聊结果响应
        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket response = new CreateGroupResponsePacket();
        response.setSuccess(true);
        response.setGroupId(groupId);
        response.setUserIdList(groupUserIds);
        response.setUserNameList(userNameList);
        // 保存下当前群状态
        SessionUtil.recordGroup(groupId, channelGroup);
        // 将响应发送到每个客户端
        channelGroup.writeAndFlush(response);
        // print log message
        LogUtil.info("create group success|groupId:{}|userIds:{}|userNames:{}", response.getGroupId(),
                response.getUserIdList(), response.getUserNameList());
    }
}
