package com.study.project.im.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImUi {

    private static JTextField nameField = null;
    private static JPasswordField pwdField = null;
    private static JTextField messageField = null;
    private static JTextField userIdField = null;

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
        JLabel userIdLable = new JLabel("用户");
        JLabel messageLabel = new JLabel("消息");
        messageField = new JTextField();
        userIdField = new JTextField();
        Dimension dim2 = new Dimension(400, 40);
        messageField.setPreferredSize(dim2);
        userIdField.setPreferredSize(dim2);
        JButton sendButton = new JButton();
        sendButton.setText("发送");
        SendActionListener listener = new SendActionListener();
        sendButton.addActionListener(listener);
        chatFrame.add(userIdLable);
        chatFrame.add(userIdField);
        chatFrame.add(messageLabel);
        chatFrame.add(messageField);
        chatFrame.setVisible(false);
        chatFrame.add(sendButton);
    }

    public static class LoginActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String pwd = pwdField.getText();
            log("用户名:" + name + ";密码:" + pwd);
            chatFrame.setVisible(true);
        }
    }

    public static class SendActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String user = userIdField.getText();
            String message = messageField.getText();
            System.err.println("用户:" + user);
            System.err.println("消息发送:" + message);
        }
    }



    public static void log(String message) {
        System.err.println(message);
    }

}
