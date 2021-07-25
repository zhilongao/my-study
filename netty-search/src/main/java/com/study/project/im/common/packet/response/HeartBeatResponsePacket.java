package com.study.project.im.common.packet.response;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.Packet;

public class HeartBeatResponsePacket extends Packet {



    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
