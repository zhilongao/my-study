package com.study.server.common.console;

import com.study.server.common.Logs;
import com.study.server.common.packet.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        Logs.error("输入用户名登录:");
        String userName = scanner.nextLine();
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUsername(userName);
        packet.setPassword("pwd");
        channel.writeAndFlush(packet);
    }
}
