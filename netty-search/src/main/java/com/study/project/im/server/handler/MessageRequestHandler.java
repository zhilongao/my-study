package com.study.project.im.server.handler;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.auth.Session;
import com.study.project.im.common.auth.SessionUtil;
import com.study.project.im.common.packet.request.MessageRequestPacket;
import com.study.project.im.common.packet.response.MessageResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final MessageRequestHandler instance = new MessageRequestHandler();

    protected MessageRequestHandler() {

    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket packet) throws Exception {
        // 1. 拿到客户端的会话信息
        Session session = SessionUtil.getSession(ctx.channel());
        // 2. 构造要发送的消息
        MessageResponsePacket message = new MessageResponsePacket();
        message.setFromUserId(session.getUserId());
        message.setFromUserName(session.getUserName());
        message.setMessage(packet.getMessage());
        message.setToUserId(packet.getToUserId());
        // 3. 拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(packet.getToUserId());
        // 4. 将消息发送给接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(message);
        } else {
            LogUtil.info("fail send message to user fromUserId:{}, toUserId:{}, message:{}",
                    session.getUserId(), packet.getToUserId(), message);
        }

    }
}
