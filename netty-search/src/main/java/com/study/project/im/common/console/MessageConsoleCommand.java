package com.study.project.im.common.console;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.packet.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class MessageConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogUtil.info("【发送消息】输入userId和message");
        String toUserId = scanner.next();
        String message = scanner.next();
        MessageRequestPacket messagePacket = new MessageRequestPacket(toUserId, message);
        channel.writeAndFlush(messagePacket);
    }
}
