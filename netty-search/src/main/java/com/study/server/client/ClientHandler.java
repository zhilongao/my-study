package com.study.server.client;

import com.study.server.common.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(new Date() + ":客户端开始登录");
        // 构建数据包
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUserId(UUID.randomUUID().toString());
        packet.setUsername("jack");
        packet.setPassword("123456");
        // 编码
        ByteBuf buffer = PacketEncode.instance.encode(ctx.alloc(), packet);
        // 写出数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.err.println(new Date() + ":客户端收到响应信息");
        ByteBuf buffer = (ByteBuf) msg;
        Packet packet = PacketDecode.instance.decode(buffer);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket respPacket = (LoginResponsePacket) packet;
            if (respPacket.isSuccess()) {
                System.err.println(new Date() + ":客户端登录成功!");
            } else {
                System.err.println(new Date() + ":客户端登录失败,原因:" + respPacket.getReason());
            }
        }
    }
}
