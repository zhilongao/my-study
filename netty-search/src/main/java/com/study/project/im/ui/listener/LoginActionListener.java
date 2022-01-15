package com.study.project.im.ui.listener;

import com.alibaba.fastjson.JSONObject;
import com.study.project.im.common.LogUtil;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.request.LoginRequestPacket;
import com.study.project.im.common.packet.response.LoginResponsePacket;
import com.study.project.im.ui.ImUi;
import com.study.project.im.ui.po.UserItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
        System.err.println("发送登录请求数据包:" + JSONObject.toJSONString(loginReqPacket));
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
                    LogUtil.log("登录请求响应数据包:" + JSONObject.toJSONString(loginRespPacket));
                    // 更新下相关的选项值
                    String userId = loginRespPacket.getUserId();
                    String userName = loginRespPacket.getUserName();
                    this.imUi.setUserId(userId);
                    this.imUi.setUserName(userName);
                    chatFrame.setTitle("聊天窗口-" + userName);
                    refreshUser();
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

    public void refreshUser() {
        UserItem[] userItem = getUserItem();
        this.imUi.getComboBox().setModel(new DefaultComboBoxModel(userItem));
    }

    public UserItem[] getUserItem() {
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
