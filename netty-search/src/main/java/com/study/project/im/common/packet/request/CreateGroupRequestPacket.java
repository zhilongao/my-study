package com.study.project.im.common.packet.request;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.Packet;
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
