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
}
