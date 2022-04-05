package com.charles.im.common.console;


import com.charles.im.common.LogUtil;
import com.charles.im.common.auth.SessionUtil;
import com.charles.im.common.packet.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        LogUtil.info("【拉入群聊】输入userId列表,userId之间引文逗号隔开:");
        String userIds = scanner.next();
        // 群聊创建,需要加入创建者userId
        Set<String> groupUserIds = new HashSet<>(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        groupUserIds.add(SessionUtil.getSession(channel).getUserId());
        packet.setUserIdSet(groupUserIds);
        channel.writeAndFlush(packet);
    }
}
