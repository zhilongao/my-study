package com.study.project.im.ui;

import com.alibaba.fastjson.JSONObject;
import com.study.project.im.client.ClientApp;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.DefaultPacket;
import com.study.project.im.common.packet.request.LoginRequestPacket;
import com.study.project.im.common.packet.request.MessageRequestPacket;
import com.study.project.im.common.packet.response.LoginResponsePacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ImUi {

    private static JTextField nameField = null;
    private static JPasswordField pwdField = null;
    private static JTextField messageField = null;
    private static JComboBox comboBox = null;

    private static JFrame loginFrame = null;
    private static JFrame chatFrame = null;


    public static void main(String[] args) {
        initUi();
    }


    public static void initUi() {
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
        JButton loginButton = new JButton();
        loginButton.setText("立即登录");
        loginButton.addActionListener(new LoginActionListener());
        nameField = new JTextField();
        pwdField = new JPasswordField();
        Dimension dim = new Dimension(400, 40);
        nameField.setPreferredSize(dim);
        pwdField.setPreferredSize(dim);
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
        UserItem[] userItem = getUserItem();
        comboBox.setModel(new DefaultComboBoxModel(userItem));
        comboBox.setBounds(15, 15, 100, 25);
        JLabel userIdLabel = new JLabel("用户");
        JLabel messageLabel = new JLabel("消息");
        messageField = new JTextField();
        Dimension dim2 = new Dimension(400, 40);
        messageField.setPreferredSize(dim2);
        JButton sendButton = new JButton();
        sendButton.setText("发送");
        SendActionListener listener = new SendActionListener();
        sendButton.addActionListener(listener);
        chatFrame.add(userIdLabel);
        chatFrame.add(comboBox);
        chatFrame.add(messageLabel);
        chatFrame.add(messageField);
        chatFrame.setVisible(false);
        chatFrame.add(sendButton);

    }

    /**
     * 登录按钮触发监听器
     */
    public static class LoginActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取输入,构建发送包
            String name = nameField.getText();
            String pwd = pwdField.getText();
            LoginRequestPacket loginReqPacket = new LoginRequestPacket();
            loginReqPacket.setUsername(name);
            loginReqPacket.setPassword(pwd);
            log("发送登录请求数据包:" + JSONObject.toJSONString(loginReqPacket));
            // 启动客户端,发送请求,启动新线程等待响应
            ClientApp.start();
            MessageQueue.addLoginReqPacket(loginReqPacket);
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
                        UserItem[] userItem = getUserItem();
                        comboBox.setModel(new DefaultComboBoxModel(userItem));
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

    /**
     * 发送按钮监听器
     */
    public static class SendActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取输入,发送数据包
            String message = messageField.getText();
            UserItem user = (UserItem)comboBox.getSelectedItem();
            String toUserId = user.getUserId();
            MessageRequestPacket packet = new MessageRequestPacket();
            packet.setToUserId(toUserId);
            packet.setMessage(message);
            // 发送请求
            String packetMessage = JSONObject.toJSONString(packet);
            // todo 消息发送
            //MessageQueue.addLoginPacket(packet);
            //log("发送数据包:" + packetMessage);
        }
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

    public static void log(String message) {
        System.err.println(message);
    }

}
