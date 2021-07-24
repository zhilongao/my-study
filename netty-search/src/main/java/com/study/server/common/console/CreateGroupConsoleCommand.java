package com.study.server.common.console;

import com.study.server.common.Logs;
import com.study.server.common.packet.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        Logs.error("【拉入群聊】输入userId列表,userId之间引文逗号隔开:");
        String userIds = scanner.next();
        packet.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(packet);
    }
}
