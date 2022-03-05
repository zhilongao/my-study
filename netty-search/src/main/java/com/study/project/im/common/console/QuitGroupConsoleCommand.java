package com.study.project.im.common.console;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.packet.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket packet = new QuitGroupRequestPacket();
        LogUtil.info("【退出群聊】输入群id:");
        String groupId = scanner.next();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);
    }
}
