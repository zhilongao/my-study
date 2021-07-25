package com.study.server.common;

import com.study.server.server.handler.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler instance = new IMHandler();

    private Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    protected IMHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.instance);
        handlerMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.instance);
        handlerMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.instance);
        handlerMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.instance);
        handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.instance);
        handlerMap.put(Command.SEND_GROUP_MESSAGE_REQUEST, SendGroupMessageRequestHandler.insatnce);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
