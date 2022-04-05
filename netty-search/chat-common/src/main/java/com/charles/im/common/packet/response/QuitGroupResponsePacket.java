package com.charles.im.common.packet.response;

import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

@Data
public class QuitGroupResponsePacket extends DefaultPacket {

    private boolean success;

    private String groupId;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
