package com.study.project.im.ui.listener;

import com.alibaba.fastjson.JSONObject;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.request.LoginRequestPacket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录按钮触发器
 */
public class LoginActionListener implements ActionListener {

    private JTextField nameField = null;
    private  JPasswordField pwdField = null;

    public LoginActionListener(JTextField nameField, JPasswordField pwdField) {
        this.nameField = nameField;
        this.pwdField = pwdField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取输入,构建发送包
        String name = nameField.getText();
        String pwd = pwdField.getText();
        LoginRequestPacket loginReqPacket = new LoginRequestPacket();
        loginReqPacket.setUsername(name);
        loginReqPacket.setPassword(pwd);
        System.err.println("发送登录请求数据包:" + JSONObject.toJSONString(loginReqPacket));

        MessageQueue.addLoginReqPacket(loginReqPacket);
    }
}
