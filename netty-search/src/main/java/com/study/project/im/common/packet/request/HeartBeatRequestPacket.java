package com.study.project.im.common.packet.request;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.Packet;

public class HeartBeatRequestPacket extends DefaultPacket {


    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
