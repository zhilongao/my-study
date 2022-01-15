package com.study.project.im.ui;

import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.response.LoginResponsePacket;
import com.study.project.im.ui.po.ChatTypeItem;
import com.study.project.im.ui.po.UserItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CommonUtils {

    public static ChatTypeItem[] getChatType() {
        List<ChatTypeItem> list = new ArrayList<>();
        ChatTypeItem item1 = new ChatTypeItem(1, "单聊");
        ChatTypeItem item2 = new ChatTypeItem(2, "群聊");
        list.add(item1);
        list.add(item2);
        return list.toArray(new ChatTypeItem[0]);
    }

    public static void refreshUser(JComboBox comboBox) {
        UserItem[] userItem = CommonUtils.getUserItem();
        comboBox.setModel(new DefaultComboBoxModel(userItem));
    }

    public static void refreshGroup(JComboBox comboBox) {
        // todo 刷新群组

    }


    public static UserItem[] getUserItem() {
        Queue<DefaultPacket> loginUserQueue = MessageQueue.getLoginPacketQueue();
        List<UserItem> list = new ArrayList<UserItem>();
        while (!loginUserQueue.isEmpty()) {
            LoginResponsePacket packet = (LoginResponsePacket)loginUserQueue.poll();
            UserItem item = new UserItem();
            item.setUserId(packet.getUserId());
            item.setUserName(packet.getUserName());
            list.add(item);
        }
        return list.toArray(new UserItem[0]);
    }
}
