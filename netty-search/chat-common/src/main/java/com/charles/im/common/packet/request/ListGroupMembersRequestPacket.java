package com.charles.im.common.packet.request;

import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

@Data
public class ListGroupMembersRequestPacket extends DefaultPacket {

    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
