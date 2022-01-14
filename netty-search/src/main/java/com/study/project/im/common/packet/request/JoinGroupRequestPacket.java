package com.study.project.im.common.packet.request;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.Packet;
import lombok.Data;

@Data
public class JoinGroupRequestPacket extends DefaultPacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
