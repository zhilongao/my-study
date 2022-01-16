package com.study.project.im.ui;

import com.study.project.im.client.ClientApp;
import com.study.project.im.common.LogUtil;
import com.study.project.im.common.MessageQueue;
import com.study.project.im.common.packet.response.MessageResponsePacket;
import com.study.project.im.ui.listener.*;
import com.study.project.im.ui.po.ChatTypeItem;
import com.study.project.im.ui.po.UserItem;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 聊天窗口设计
 */
@Data
public class ImUi {

    private JFrame loginFrame = null;
    private JTextField nameField = null;
    private JPasswordField pwdField = null;
    private JTextField messageField = null;
    private JComboBox comboBox = null;
    private JComboBox msgTypeBox = null;
    private JTextArea jTextArea;
    private JFrame chatFrame = null;
    private String userId;
    private String userName;
    private JButton loginButton;

    private JFrame groupFrame = null;
    private JTextField groupNameField = null;
    private JButton addGroupButton = null;
    private Set<String> groupUser = null;
    // 菜单
    private JMenuBar mb;
    private JMenu groupMenu;
    private JMenuItem createGroupItem;
    private JMenuItem addGroupItem;
    private JMenuItem quitGroupItem;


    public void initUi() {
        initLoginFrame();
        initChatFrame();
        initCreateGroup();
        LoginActionListener loginActionListener = new LoginActionListener(this);
        loginButton.addActionListener(loginActionListener);
        // 客户端去连接
        ClientApp.start();
        // 处理普通的响应消息
        new Thread(() -> {
            while (!Thread.interrupted()) {
                MessageResponsePacket packet = MessageQueue.getRespMessagePacket(userId);
                if (null != packet) {
                    String fromUserName = packet.getFromUserName();
                    String message = packet.getMessage();
                    // LogUtil.log("接收到普通消息:" + message);
                    jTextArea.append(fromUserName + ":" + message + "\n");
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

    /**
     * 登录窗口
     */
    private void initLoginFrame() {
        // 创建JFrame
        loginFrame = new JFrame();
        loginFrame.setTitle("charles聊天室");
        loginFrame.setSize(600, 150);
        loginFrame.setLocation(500, 500);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setResizable(false);
        // 设置为流式布局
        FlowLayout loginLayout = new FlowLayout();
        loginFrame.setLayout(loginLayout);
        // 登录界面加载元素
        Dimension dim = new Dimension(550, 30);
        nameField = new JTextField();
        pwdField = new JPasswordField();
        nameField.setPreferredSize(dim);
        pwdField.setPreferredSize(dim);
        loginButton = new JButton();
        loginButton.setText("立即登录");
        JLabel nameLabel = new JLabel("账号:");
        JLabel pwdLabel = new JLabel("密码:");
        loginFrame.add(nameLabel);
        loginFrame.add(nameField);
        loginFrame.add(pwdLabel);
        loginFrame.add(pwdField);
        loginFrame.add(loginButton);
        loginFrame.setVisible(true);
    }

    /**
     * 聊天窗口
     */
    private void initChatFrame() {
        chatFrame = new JFrame();
        chatFrame.setTitle("聊天窗口");
        chatFrame.setSize(600, 300);
        chatFrame.setLocation(500, 500);
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setResizable(false);
        FlowLayout chatLayout = new FlowLayout();
        chatLayout.setAlignment(FlowLayout.LEFT);
        chatFrame.setLayout(chatLayout);
        // 聊天界面加载元素
        comboBox = new JComboBox();//下拉选择框
        JLabel singLabel = new JLabel("消息类型");
        ChatTypeItem[] chatTypes = CommonUtils.getChatType();
        ChatTypeSelectListener chatTypeListener = new ChatTypeSelectListener(this);
        msgTypeBox = new JComboBox();
        msgTypeBox.setModel(new DefaultComboBoxModel(chatTypes));
        msgTypeBox.addActionListener(chatTypeListener);
        JLabel userIdLabel = new JLabel(" 用户");
        JLabel messageLabel = new JLabel(" 消息");
        messageField = new JTextField();
        Dimension dim2 = new Dimension(200, 30);
        messageField.setPreferredSize(dim2);
        //chatMessage = new JTextField();
        Dimension dim3 = new Dimension(570, 100);
        //chatMessage.setPreferredSize(dim3);
        JButton sendButton = new JButton();
        sendButton.setText("发送");
        SendActionListener listener = new SendActionListener(this);
        sendButton.addActionListener(listener);
        chatFrame.add(singLabel);
        chatFrame.add(msgTypeBox);
        chatFrame.add(userIdLabel);
        chatFrame.add(comboBox);
        chatFrame.add(messageLabel);
        chatFrame.add(messageField);
        chatFrame.setVisible(false);
        chatFrame.add(sendButton);
        jTextArea = new JTextArea();
        jTextArea.setPreferredSize(dim3);
        chatFrame.add(jTextArea);
        // 设置菜单
        initMenu();
    }

    /**
     * 创建群组窗口
     */
    private void initCreateGroup() {
        groupFrame = new JFrame();
        groupFrame.setTitle("创建群组");
        groupFrame.setSize(600, 150);
        groupFrame.setLocation(500, 500);
        groupFrame.setResizable(false);
        FlowLayout chatLayout = new FlowLayout();
        chatLayout.setAlignment(FlowLayout.LEFT);
        groupFrame.setLayout(chatLayout);
        JLabel nameLabel = new JLabel("群组名称:");
        JLabel userLabel = new JLabel("群组成员:");
        Dimension dim = new Dimension(200, 25);
        groupNameField = new JTextField();
        groupNameField.setPreferredSize(dim);
        groupFrame.add(nameLabel);
        groupFrame.add(groupNameField);
        groupFrame.add(userLabel);
        CheckBoxListener boxListener = new CheckBoxListener(this);
        UserItem[] userItems = CommonUtils.getUserItem();
        for (UserItem userItem : userItems) {
            JCheckBox checkBox = new JCheckBox(userItem.getUserName());
            checkBox.addActionListener(boxListener);
            groupFrame.add(checkBox);
        }
        groupUser = new HashSet<>();
        addGroupButton = new JButton("创建");
        AddGroupActionListener addGroupActionListener = new AddGroupActionListener(this);
        addGroupButton.addActionListener(addGroupActionListener);
        groupFrame.add(addGroupButton);
    }


    /**
     * 初始化菜单
     */
    private void initMenu() {
        mb = new JMenuBar();
        groupMenu = new JMenu("群组");
        createGroupItem = new JMenuItem(CommonUtils.CREATE_GROUP_COMMAND);
        addGroupItem = new JMenuItem(CommonUtils.ADD_GROUP_COMMAND);
        quitGroupItem = new JMenuItem(CommonUtils.QUIT_GROUP_COMMAND);
        GroupMenuItemListener listener = new GroupMenuItemListener(this);
        createGroupItem.addActionListener(listener);
        addGroupItem.addActionListener(listener);
        quitGroupItem.addActionListener(listener);
        groupMenu.add(createGroupItem);
        groupMenu.add(addGroupItem);
        groupMenu.add(quitGroupItem);
        mb.add(groupMenu);
        chatFrame.setJMenuBar(mb);
    }
}
