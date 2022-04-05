package com.charles.im.common.packet.response;

import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends DefaultPacket {

    private boolean success;

    private String groupId;

    private List<String> userIdList;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
