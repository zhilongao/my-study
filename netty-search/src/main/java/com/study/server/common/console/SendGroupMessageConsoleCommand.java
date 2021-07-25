package com.study.server.common.console;

import com.study.server.common.Logs;
import com.study.server.common.packet.request.SendGroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendGroupMessageConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        Logs.error("【群消息发送】");
        String groupId = scanner.next();
        String message = scanner.next();
        SendGroupMessageRequestPacket packet = new SendGroupMessageRequestPacket();
        packet.setGroupId(groupId);
        packet.setMessage(message);
        channel.writeAndFlush(packet);
    }
}
