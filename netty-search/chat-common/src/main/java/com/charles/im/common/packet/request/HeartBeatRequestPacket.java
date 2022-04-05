package com.charles.im.common.packet.request;

import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;

public class HeartBeatRequestPacket extends DefaultPacket {


    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
