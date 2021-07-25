package com.study.server.common.packet.response;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import com.study.server.common.auth.Session;
import lombok.Data;

@Data
public class SendGroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_GROUP_MESSAGE_RESPONSE;
    }
}
