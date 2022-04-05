package com.charles.im.common.packet.request;

import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

@Data
public class SendGroupMessageRequestPacket extends DefaultPacket {

    private String groupId;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_GROUP_MESSAGE_REQUEST;
    }
}
