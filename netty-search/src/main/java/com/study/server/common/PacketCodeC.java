package com.study.server.common;

import com.study.server.common.packet.LoginRequestPacket;
import com.study.server.common.packet.LoginResponsePacket;
import com.study.server.common.packet.MessageRequestPacket;
import com.study.server.common.packet.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据包解码工具类
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC instance = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    private final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /****************************** 编码 *******************/
    public ByteBuf encode(Packet packet) {
        return encode(ByteBufAllocator.DEFAULT, packet);
    }

    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        // 1. 创建ByteBuf对象
        ByteBuf byteBuf = allocator.ioBuffer();
        return encode(byteBuf, packet);
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化java对象
        byte[] data = Serializer.DEFAULT.serialize(packet);
        // 2. 实际的编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
        return byteBuf;
    }

    /**************************** 解码 ****************************/
    public Packet decode(ByteBuf buf) {
        // 跳过magic number
        buf.skipBytes(4);
        // 跳过版本号
        buf.skipBytes(1);
        // 序列化算法标识
        byte serializeAlgorithm = buf.readByte();
        // 指令
        byte command = buf.readByte();
        // 数据包长度
        int length = buf.readInt();
        // 读取数据
        byte[] data = new byte[length];
        buf.readBytes(data);
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
             return serializer.deserialize(requestType, data);
        }
        return null;
    }

    public Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

    public Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }
}
