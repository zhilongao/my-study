package com.study.project.im.common.packet.response;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.Packet;
import lombok.Data;

/**
 * 登录响应包
 */
@Data
public class LoginResponsePacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public String toString() {
        return "LoginResponsePacket{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", success=" + success +
                ", reason='" + reason + '\'' +
                '}';
    }
}
