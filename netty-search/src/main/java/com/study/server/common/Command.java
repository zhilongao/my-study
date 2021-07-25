package com.study.server.common;

public interface Command {
    /**
     * 登录请求指令
     */
    Byte LOGIN_REQUEST = 1;
    /**
     * 登录响应指令
     */
    Byte LOGIN_RESPONSE = 2;
    /**
     * 消息请求
     */
    Byte MESSAGE_REQUEST = 3;
    /**
     * 消息响应
     */
    Byte MESSAGE_RESPONSE = 4;
    /**
     * 创建群聊请求
     */
    Byte CREATE_GROUP_REQUEST = 5;
    /**
     * 创建群聊响应
     */
    Byte CREATE_GROUP_RESPONSE = 6;
    /**
     * 加入群聊请求
     */
    Byte JOIN_GROUP_REQUEST = 7;
    /**
     * 加入群聊响应
     */
    Byte JOIN_GROUP_RESPONSE = 8;
    /**
     * 退出群聊请求
     */
    Byte QUIT_GROUP_REQUEST = 9;
    /**
     * 退出群聊响应
     */
    Byte QUIT_GROUP_RESPONSE = 10;
    /**
     * 查看群成员请求
     */
    Byte LIST_GROUP_MEMBERS_REQUEST = 11;
    /**
     * 查看群成员响应
     */
    Byte LIST_GROUP_MEMBERS_RESPONSE = 12;
    /**
     * 发送群消息请求
     */
    Byte SEND_GROUP_MESSAGE_REQUEST = 13;
    /**
     * 发送群消息响应
     */
    Byte SEND_GROUP_MESSAGE_RESPONSE = 14;
}
