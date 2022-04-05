package com.charles.im.common.packet.request;


import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

import java.util.Set;

@Data
public class CreateGroupRequestPacket extends DefaultPacket {

    private Set<String> userIdSet;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
