package com.study.server.server;

import com.study.server.common.*;
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
            LoginRequestPacket reqPacket = (LoginRequestPacket) packet;
            LoginResponsePacket respPacket = new LoginResponsePacket();
            respPacket.setVersion(packet.getVersion());
            if (validLogin(reqPacket)) {
                respPacket.setSuccess(true);
            } else {
                respPacket.setSuccess(false);
                respPacket.setReason("账号密码校验失败");
            }
            ByteBuf byteBuf = PacketEncode.instance.encode(ctx.alloc(), respPacket);
            ctx.channel().writeAndFlush(byteBuf);
        }

    }

    private boolean validLogin(LoginRequestPacket packet) {

        return true;
    }
}
