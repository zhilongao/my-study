package com.charles.im.common.packet.response;


import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;

public class HeartBeatResponsePacket extends DefaultPacket {



    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
