package com.charles.im.common;

import com.alibaba.fastjson.JSONObject;

import com.charles.im.common.packet.DefaultPacket;
import com.charles.im.common.packet.request.LoginRequestPacket;
import com.charles.im.common.packet.request.MessageRequestPacket;
import com.charles.im.common.packet.response.LoginResponsePacket;
import com.charles.im.common.packet.response.MessageResponsePacket;
import com.charles.im.common.utils.RedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    public static final String LOGIN_REQ_PACKET_QUEUE_KEY = "login_req_packet_queue_key";

    public static final String LOGIN_RESP_PACKET_QUEUE_KEY = "login_resp_packet_queue_key";

    public static final String MESSAGE_REQ_PACKET_QUEUE_KEY = "message_req_packet_queue_key";

    public static final String MESSAGE_RESP_PACKET_QUEUE_KEY = "message_resp_packet_queue_key_";

    public static final String LOGIN_USER_QUEUE_KEY = "login_user_queue_key";


    /**
     * 添加登录请求包
     * @param packet packet
     */
    public static void addLoginReqPacket(LoginRequestPacket packet) {
        setPacket(LOGIN_REQ_PACKET_QUEUE_KEY, packet);
    }

    /**
     * 获取登录请求包
     * @return
     */
    public static LoginRequestPacket getLoginRequestPacket() {
        String key = LOGIN_REQ_PACKET_QUEUE_KEY;
        return (LoginRequestPacket)getPacket(key, LoginRequestPacket.class);
    }

    /**
     * 添加登录响应包
     * @param packet packet
     */
    public static void addLoginRespPacket(DefaultPacket packet) {
        setPacket(LOGIN_RESP_PACKET_QUEUE_KEY, packet);
    }

    /**
     * 获取登录响应包
     * @return
     */
    public static LoginResponsePacket getLoginRespPacket() {
        String key = LOGIN_RESP_PACKET_QUEUE_KEY;
        return (LoginResponsePacket)getPacket(key, LoginResponsePacket.class);
    }

    public static void addReqMessagePacket(MessageRequestPacket packet) {
        setPacket(MESSAGE_REQ_PACKET_QUEUE_KEY, packet);
    }

    public static MessageRequestPacket getReqMessagePacket() {
        String key = MESSAGE_REQ_PACKET_QUEUE_KEY;
        return (MessageRequestPacket)getPacket(key, MessageRequestPacket.class);
    }


    public static void addRespMessagePacket(MessageResponsePacket packet) {
        String toUserId = packet.getToUserId();
        setPacket(MESSAGE_RESP_PACKET_QUEUE_KEY + toUserId, packet);
    }

    public static MessageResponsePacket getRespMessagePacket(String toUserId) {
        String key = MESSAGE_RESP_PACKET_QUEUE_KEY + toUserId;
        return (MessageResponsePacket)getPacket(key, MessageResponsePacket.class);
    }

    /**
     * 获取所有登录用户
     * @return
     */
    public static Queue<DefaultPacket> getLoginPacketQueue() {
        return getPacketQueue(LOGIN_USER_QUEUE_KEY,  LoginResponsePacket.class);
    }


    public static DefaultPacket getPacket(String key, Class<? extends DefaultPacket> cls) {
        Jedis jedis = getJedis();
        Set<String> values = jedis.spop(key, 1);
        if (values.size() > 0) {
            for (String value : values) {
                return JSONObject.parseObject(value, cls);
            }
        }
        return null;
    }

    public static Queue<DefaultPacket> getPacketQueue(String key,  Class<? extends DefaultPacket> cls) {
        Queue<DefaultPacket> queue = new LinkedBlockingQueue<>();
        Jedis jedis = getJedis();
        Set<String> values = jedis.smembers(key);
        if (values.size() > 0) {
            queue.clear();
            for (String msg : values) {
                LogUtil.info(msg);
                DefaultPacket packet = JSONObject.parseObject(msg, cls);
                queue.add(packet);
            }
        }
        return queue;
    }



    public static void addLoginPacket(DefaultPacket packet) {
        setPacket(LOGIN_USER_QUEUE_KEY, packet);
    }

    public static void setPacket(String key, DefaultPacket packet) {
        Jedis jedis = getJedis();
        jedis.sadd(key, JSONObject.toJSONString(packet));
    }


    public static Jedis getJedis() {
        return RedisUtils.getJedis();
    }
}
