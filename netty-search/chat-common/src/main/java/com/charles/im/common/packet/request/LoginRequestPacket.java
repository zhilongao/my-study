package com.charles.im.common.packet.request;


import com.charles.im.common.Command;
import com.charles.im.common.packet.DefaultPacket;
import lombok.Data;

/**
 * 登录请求包
 */
@Data
public class LoginRequestPacket extends DefaultPacket {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
