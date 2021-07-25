package com.study.server.common.console;

import com.study.server.common.Logs;
import com.study.server.common.packet.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class MessageConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        Logs.error("【发送消息】输入userId和message");
        String toUserId = scanner.next();
        String message = scanner.next();
        MessageRequestPacket messagePacket = new MessageRequestPacket(toUserId, message);
        channel.writeAndFlush(messagePacket);
    }
}
