package com.study.server.common.packet;

import com.study.server.common.Command;
import com.study.server.common.Packet;
import lombok.Data;

/**
 * 登录请求包
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
