package com.study.project.im.common.console;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.packet.request.SendGroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendGroupMessageConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogUtil.info("【群消息发送】");
        String groupId = scanner.next();
        String message = scanner.next();
        SendGroupMessageRequestPacket packet = new SendGroupMessageRequestPacket();
        packet.setGroupId(groupId);
        packet.setMessage(message);
        channel.writeAndFlush(packet);
    }
}
