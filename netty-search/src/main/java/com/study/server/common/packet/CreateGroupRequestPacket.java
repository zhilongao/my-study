package com.study.server.common.packet;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
