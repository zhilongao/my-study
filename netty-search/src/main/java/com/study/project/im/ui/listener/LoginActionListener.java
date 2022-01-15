package com.study.project.im.ui.listener;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.request.LoginRequestPacket;
import com.study.project.im.common.packet.response.LoginResponsePacket;
import com.study.project.im.ui.CommonUtils;
import com.study.project.im.ui.ImUi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * 登录按钮触发器
 */
public class LoginActionListener implements ActionListener {

    private JTextField nameField = null;
    private JPasswordField pwdField = null;
    private JFrame loginFrame = null;
    private JFrame chatFrame = null;
    private ImUi imUi;


    public LoginActionListener(ImUi imUi) {
        this.imUi = imUi;
        this.nameField = imUi.getNameField();
        this.pwdField = imUi.getPwdField();
        this.loginFrame = imUi.getLoginFrame();
        this.chatFrame = imUi.getChatFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取输入,构建发送包
        String name = nameField.getText();
        String pwd = pwdField.getText();
        LoginRequestPacket loginReqPacket = new LoginRequestPacket();
        loginReqPacket.setUsername(name);
        loginReqPacket.setPassword(pwd);
        MessageQueue.addLoginReqPacket(loginReqPacket);
        // 处理登录响应
        handleLoginResponse();
    }

    private void handleLoginResponse() {
        new Thread(() -> {
            int retry = 0;
            while (true) {
                if (3 == retry) {
                    LogUtil.log("请求超时!");
                }
                retry ++;
                LoginResponsePacket loginRespPacket = MessageQueue.getLoginRespPacket();
                if (null != loginRespPacket) {
                    // 更新下相关的选项值
                    String userId = loginRespPacket.getUserId();
                    String userName = loginRespPacket.getUserName();
                    this.imUi.setUserId(userId);
                    this.imUi.setUserName(userName);
                    chatFrame.setTitle("charles聊天室-" + userName);
                    CommonUtils.refreshUser(this.imUi.getComboBox());
                    // ui显示控制
                    loginFrame.setVisible(false);
                    chatFrame.setVisible(true);
                    break;
                } else {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
