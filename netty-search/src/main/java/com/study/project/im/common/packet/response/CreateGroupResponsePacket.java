package com.study.project.im.common.packet.response;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.DefaultPacket;
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
