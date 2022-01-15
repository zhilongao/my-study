package com.study.project.im.ui;

import com.study.project.im.client.ClientApp;
import com.study.project.im.common.LogUtil;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.response.MessageResponsePacket;
import com.study.project.im.ui.listener.LoginActionListener;
import com.study.project.im.ui.listener.SendActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * 聊天窗口设计
 */
public class ImUi {

    private JTextField nameField = null;
    private JPasswordField pwdField = null;
    private JTextField messageField = null;
    private JComboBox comboBox = null;
    private JTextField chatMessage = null;
    private JFrame loginFrame = null;
    private JFrame chatFrame = null;
    private String userId;
    private String userName;


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
        LoginActionListener loginActionListener = new LoginActionListener(this);
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
        JLabel userIdLabel = new JLabel("用户");
        JLabel messageLabel = new JLabel("消息");
        messageField = new JTextField();
        Dimension dim2 = new Dimension(400, 40);
        messageField.setPreferredSize(dim2);
        chatMessage = new JTextField();
        chatMessage.setPreferredSize(dim2);
        JButton sendButton = new JButton();
        sendButton.setText("发送");
        SendActionListener listener = new SendActionListener(this);
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
        // 处理普通的响应消息
        new Thread(() -> {
            while (!Thread.interrupted()) {
                MessageResponsePacket packet = MessageQueue.getRespMessagePacket(userId);
                if (null != packet) {
                    String fromUserName = packet.getFromUserName();
                    String message = packet.getMessage();
                    LogUtil.log("接收到普通消息:" + message);
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
    }


    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JPasswordField getPwdField() {
        return pwdField;
    }

    public void setPwdField(JPasswordField pwdField) {
        this.pwdField = pwdField;
    }

    public JTextField getMessageField() {
        return messageField;
    }

    public void setMessageField(JTextField messageField) {
        this.messageField = messageField;
    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(JComboBox comboBox) {
        this.comboBox = comboBox;
    }

    public JTextField getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(JTextField chatMessage) {
        this.chatMessage = chatMessage;
    }

    public JFrame getLoginFrame() {
        return loginFrame;
    }

    public void setLoginFrame(JFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public JFrame getChatFrame() {
        return chatFrame;
    }

    public void setChatFrame(JFrame chatFrame) {
        this.chatFrame = chatFrame;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
