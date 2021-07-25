package com.study.server.common.packet.request;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import lombok.Data;

import java.util.Set;

@Data
public class CreateGroupRequestPacket extends Packet {

    private Set<String> userIdSet;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
