package com.study.server.server.handler;

import com.study.server.common.IDUtil;
import com.study.server.common.Logs;
import com.study.server.common.auth.SessionUtil;
import com.study.server.common.packet.request.CreateGroupRequestPacket;
import com.study.server.common.packet.response.CreateGroupResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket packet) throws Exception {
        Set<String> userIdList = packet.getUserIdSet();
        List<String> userNameList = new ArrayList<>();
        // 创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        // 筛选待加入的用户
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        // 群聊结果响应
        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket response = new CreateGroupResponsePacket();
        response.setSuccess(true);
        response.setGroupId(groupId);
        response.setUserNameList(userNameList);
        // 保存下当前群状态
        SessionUtil.recordGroup(groupId, channelGroup);
        // 将响应发送到每个客户端
        channelGroup.writeAndFlush(response);
        // 信息
        Logs.error("群创建成功,id为[" + response.getGroupId() + "]");
        Logs.error("群里有:" + response.getUserNameList());
    }
}
