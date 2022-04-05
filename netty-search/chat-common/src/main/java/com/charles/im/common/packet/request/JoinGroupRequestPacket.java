package com.charles.im.common.packet.request;

import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

@Data
public class JoinGroupRequestPacket extends DefaultPacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
