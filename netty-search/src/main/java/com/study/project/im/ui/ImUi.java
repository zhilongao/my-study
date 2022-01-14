package com.study.project.im.ui;

import com.alibaba.fastjson.JSONObject;
import com.study.project.im.client.ClientApp;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.response.LoginResponsePacket;
import com.study.project.im.common.packet.response.MessageResponsePacket;
import com.study.project.im.ui.listener.LoginActionListener;
import com.study.project.im.ui.listener.SendActionListener;
import com.study.project.im.ui.po.UserItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * 聊天窗口设计
 */
public class ImUi {

    private  JTextField nameField = null;
    private  JPasswordField pwdField = null;
    private  JTextField messageField = null;
    private  JComboBox comboBox = null;

    private  JTextField chatMessage = null;
    private  JFrame loginFrame = null;
    private  JFrame chatFrame = null;

    public  void initUi() {
        // 创建JFrame
        loginFrame = new JFrame();
        loginFrame.setTitle("登录界面");
        loginFrame.setSize(500, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);

        chatFrame = new JFrame();
        chatFrame.setTitle("聊天窗口");
        chatFrame.setSize(500, 300);
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setResizable(false);
        // 设置为流式布局
        FlowLayout loginLayout = new FlowLayout();
        loginFrame.setLayout(loginLayout);
        FlowLayout chatLayout = new FlowLayout();
        chatFrame.setLayout(chatLayout);

        // 登录界面加载元素
        Dimension dim = new Dimension(400, 40);
        nameField = new JTextField();
        pwdField = new JPasswordField();
        nameField.setPreferredSize(dim);
        pwdField.setPreferredSize(dim);
        JButton loginButton = new JButton();
        loginButton.setText("立即登录");
        LoginActionListener loginActionListener = new LoginActionListener(nameField, pwdField);
        loginButton.addActionListener(loginActionListener);
        JLabel nameLabel = new JLabel("账号：");
        JLabel pwdLabel = new JLabel("密码：");
        loginFrame.add(nameLabel);
        loginFrame.add(nameField);
        loginFrame.add(pwdLabel);
        loginFrame.add(pwdField);
        loginFrame.add(loginButton);
        loginFrame.setVisible(true);

        // 聊天界面加载元素
        comboBox = new JComboBox();//下拉选择框
        comboBox.setBounds(15, 15, 100, 25);
        refreshUser();
        JLabel userIdLabel = new JLabel("用户");
        JLabel messageLabel = new JLabel("消息");
        messageField = new JTextField();
        Dimension dim2 = new Dimension(400, 40);
        messageField.setPreferredSize(dim2);
        chatMessage = new JTextField();
        chatMessage.setPreferredSize(dim2);
        JButton sendButton = new JButton();
        sendButton.setText("发送");
        SendActionListener listener = new SendActionListener(messageField, comboBox);
        sendButton.addActionListener(listener);
        chatFrame.add(userIdLabel);
        chatFrame.add(comboBox);
        chatFrame.add(messageLabel);
        chatFrame.add(messageField);
        chatFrame.setVisible(false);
        chatFrame.add(sendButton);
        chatFrame.add(chatMessage);
        // 客户端去连接
        ClientApp.start();
        // 处理登录响应
        new Thread(() -> {
            int retry = 0;
            while (true) {
                if (3 == retry) {
                    log("请求超时!");
                }
                retry ++;
                LoginResponsePacket loginRespPacket = MessageQueue.getLoginRespPacket();
                if (null != loginRespPacket) {
                    log("登录请求响应数据包:" + JSONObject.toJSONString(loginRespPacket));
                    // 更新下相关的选项值
                    chatFrame.setTitle("聊天窗口-" + loginRespPacket.getUserName());
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

        // 处理普通的响应消息
        new Thread(() -> {
            while (!Thread.interrupted()) {
                MessageResponsePacket packet = MessageQueue.getRespMessagePacket();
                if (null != packet) {
                    String fromUserName = packet.getFromUserName();
                    String message = packet.getMessage();
                    chatMessage.setText(fromUserName + ":" + message);
                } else {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // 刷新选择框
//        new Thread(() -> {
//            while (!Thread.interrupted()) {
//                refreshUser();
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }


    public void refreshUser() {
        UserItem[] userItem = getUserItem();
        comboBox.setModel(new DefaultComboBoxModel(userItem));
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

    public void log(String message) {
        System.err.println(message);
    }

}
