package com.study.project.im.common.packet.request;

import com.study.project.im.common.Command;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.Packet;
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
