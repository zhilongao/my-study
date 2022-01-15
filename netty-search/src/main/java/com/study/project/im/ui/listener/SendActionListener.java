package com.study.project.im.ui.listener;

import com.alibaba.fastjson.JSONObject;
import com.study.project.im.common.LogUtil;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.request.MessageRequestPacket;
import com.study.project.im.ui.ImUi;
import com.study.project.im.ui.po.UserItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendActionListener implements ActionListener {

    private JTextField messageField = null;
    private JComboBox comboBox = null;
    private ImUi imUi;

    public SendActionListener(ImUi imUi) {
        this.imUi = imUi;
        this.messageField = imUi.getMessageField();
        this.comboBox = imUi.getComboBox();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取输入,发送数据包
        String message = messageField.getText();
        UserItem user = (UserItem)comboBox.getSelectedItem();
        String toUserId = user.getUserId();
        String toUserName = user.getUserName();
        MessageRequestPacket packet = new MessageRequestPacket();
        packet.setToUserId(toUserId);
        packet.setMessage(message);
        // 发送请求
        String packetMessage = JSONObject.toJSONString(packet);
        MessageQueue.addReqMessagePacket(packet);
        LogUtil.log("消息发送给:" + toUserName);
        LogUtil.log("发送数据包:" + packetMessage);
    }
}
