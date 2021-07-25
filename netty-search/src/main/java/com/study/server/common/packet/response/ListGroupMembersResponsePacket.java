package com.study.server.common.packet.response;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import com.study.server.common.auth.Session;
import lombok.Data;

import java.util.List;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
