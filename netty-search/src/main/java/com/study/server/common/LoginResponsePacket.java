package com.study.server.common;

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

    private boolean success;

    private String reason;
}
