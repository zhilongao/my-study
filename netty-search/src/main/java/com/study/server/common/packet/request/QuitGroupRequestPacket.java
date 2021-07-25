package com.study.server.common.packet.request;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import lombok.Data;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
