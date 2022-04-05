package com.charles.im.common.packet.request;


import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestPacket extends DefaultPacket {

    private String toUserId;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
