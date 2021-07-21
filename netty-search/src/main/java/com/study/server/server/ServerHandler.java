package com.study.server.server;

import com.study.server.common.LoginRequestPacket;
import com.study.server.common.Packet;
import com.study.server.common.PacketDecode;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        // 解码
        Packet packet = PacketDecode.instance.decode(buf);
        // 逻辑处理
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket logReqPacket = (LoginRequestPacket) packet;
            if (validLogin(logReqPacket)) {
                System.err.println("登录成功!");
            } else {
                System.err.println("登录失败");
            }
        }
    }

    private boolean validLogin(LoginRequestPacket packet) {

        return true;
    }
}
