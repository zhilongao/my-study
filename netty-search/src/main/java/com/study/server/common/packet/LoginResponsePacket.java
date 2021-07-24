package com.study.server.common.packet;

import com.study.server.common.Command;
import com.study.server.common.Packet;
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
