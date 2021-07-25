package com.study.project.im.common.packet.response;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.Packet;
import com.study.project.im.common.auth.Session;
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
