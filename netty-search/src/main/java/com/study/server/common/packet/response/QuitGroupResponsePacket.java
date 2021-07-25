package com.study.server.common.packet.response;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import lombok.Data;

@Data
public class QuitGroupResponsePacket extends Packet {

    private boolean success;

    private String groupId;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
