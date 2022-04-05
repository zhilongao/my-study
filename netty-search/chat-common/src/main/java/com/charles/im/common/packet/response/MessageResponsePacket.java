package com.charles.im.common.packet.response;


import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

@Data
public class MessageResponsePacket extends DefaultPacket {

    private String fromUserId;

    private String fromUserName;

    private String toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
