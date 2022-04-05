package com.charles.im.common.packet;

import lombok.Data;

@Data
public class DefaultPacket extends Packet{

    @Override
    public Byte getCommand() {
        return -1;
    }
}
