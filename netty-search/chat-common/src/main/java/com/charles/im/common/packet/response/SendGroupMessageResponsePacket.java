package com.charles.im.common.packet.response;


import com.charles.im.common.Command;
import com.charles.im.common.auth.Session;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

@Data
public class SendGroupMessageResponsePacket extends DefaultPacket {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_GROUP_MESSAGE_RESPONSE;
    }
}
