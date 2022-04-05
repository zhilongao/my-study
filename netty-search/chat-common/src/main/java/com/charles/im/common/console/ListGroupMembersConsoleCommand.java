package com.charles.im.common.console;


import com.charles.im.common.LogUtil;
import com.charles.im.common.packet.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogUtil.info("【获取群成员列表】 输入groupId");
        String groupId = scanner.next();
        ListGroupMembersRequestPacket packet = new ListGroupMembersRequestPacket();
        packet.setGroupId(groupId);
        channel.writeAndFlush(packet);
    }
}
