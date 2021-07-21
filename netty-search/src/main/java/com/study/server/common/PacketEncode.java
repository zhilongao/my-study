package com.study.server.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 数据包编码工具类
 */
public class PacketEncode {
    private static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketEncode instance = new PacketEncode();

    private PacketEncode() {

    }

    public ByteBuf encode(Packet packet) {
        return encode(ByteBufAllocator.DEFAULT, packet);
    }

    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        // 1. 创建ByteBuf对象
        ByteBuf byteBuf = allocator.ioBuffer();
        // 2. 序列化java对象
        byte[] data = Serializer.DEFAULT.serialize(packet);
        // 3. 实际的编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
        return byteBuf;
    }
}
