package com.study.server.common.packet.request;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import lombok.Data;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
