package com.study.server.common.console;

import com.study.server.common.Logs;
import com.study.server.common.packet.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();
        Logs.info("输入groupId,加入群聊:");
        String groupId = scanner.next();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);
    }
}
