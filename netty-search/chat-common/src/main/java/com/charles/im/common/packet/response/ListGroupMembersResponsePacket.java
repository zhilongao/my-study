package com.charles.im.common.packet.response;

import com.charles.im.common.Command;
import com.charles.im.common.auth.Session;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

import java.util.List;


@Data
public class ListGroupMembersResponsePacket extends DefaultPacket {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
