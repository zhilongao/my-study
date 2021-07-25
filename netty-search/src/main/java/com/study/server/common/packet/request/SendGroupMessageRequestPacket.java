package com.study.server.common.packet.request;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import lombok.Data;

@Data
public class SendGroupMessageRequestPacket extends Packet {

    private String groupId;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_GROUP_MESSAGE_REQUEST;
    }
}
