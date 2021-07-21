package com.study.server.common;

import io.netty.buffer.ByteBuf;

/**
 * 数据包解码工具类
 */
public class PacketDecode {


    public static final PacketDecode instance = new PacketDecode();

    private PacketDecode() {

    }

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
        if (command == Command.LOGIN_REQUEST) {
            return LoginRequestPacket.class;
        }
        return null;
    }

    public Serializer getSerializer(byte serializeAlgorithm) {
        return new JSONSerializer();
    }
}
