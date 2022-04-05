package com.charles.im.common.console;


import com.charles.im.common.LogUtil;
import com.charles.im.common.packet.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket packet = new JoinGroupRequestPacket();
        LogUtil.info("【加入群聊】输入群id:");
        String groupId = scanner.next();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);
    }
}
