package com.study.project.im.common.packet.request;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.Packet;
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
