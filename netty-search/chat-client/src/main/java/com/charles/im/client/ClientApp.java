package com.charles.im.client;

import com.alibaba.fastjson.JSONObject;
import com.charles.im.client.handler.*;
import com.charles.im.common.LogUtil;
import com.charles.im.common.MessageQueue;
import com.charles.im.common.auth.SessionUtil;
import com.charles.im.common.console.ConsoleCommandManager;
import com.charles.im.common.console.LoginConsoleCommand;
import com.charles.im.common.handler.IMIdlesStateHandler;
import com.charles.im.common.handler.Spliter;
import com.charles.im.common.packet.request.LoginRequestPacket;
import com.charles.im.common.packet.request.MessageRequestPacket;
import com.charles.im.common.util.PacketDecoder;
import com.charles.im.common.util.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class ClientApp {

    private static final String host = "127.0.0.1";

    private static final int port = 8080;

    private static final int MAX_RETRY = 5;

//    public static void main(String[] args) throws InterruptedException {
//        // 启动一个客户端
//        start();
//    }

    /**
     * 启动一个客户端
     */
    public static void start() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        // 空闲检测处理
                        ch.pipeline().addLast(new IMIdlesStateHandler());
                        // 拆包粘包处理
                        ch.pipeline().addLast(new Spliter());
                        // 解码处理
                        ch.pipeline().addLast(new PacketDecoder());
                        // 业务处理器
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new SendGroupMessageResponseHandler());
                        // 编码处理
                        ch.pipeline().addLast(new PacketEncoder());
                        // 心跳定时器
                        ch.pipeline().addLast(new HearBeatTimerHandler());
                    }
                });
        connect(bootstrap, host, port, MAX_RETRY);
    }


    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                LogUtil.info("客户端连接成功");
                // 启动控制台消息发送线程
                Channel channel = ((ChannelFuture) future).channel();
                // startConsoleThread(channel);
                // 消息处理线程启动
                startLoginPacketThread(channel);
                startMessagePacketThread(channel);
            } else if (retry == 0) {
                LogUtil.info("重连次数用完,放弃连接");
            } else {
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                LogUtil.error("连接失败,第" + order + "次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager commandManager = new ConsoleCommandManager();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                    waitForLoginResponse();
                } else {
                    commandManager.exec(scanner, channel);
                }
            }
        }).start();
    }


    private static void startLoginPacketThread(Channel channnel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                LoginRequestPacket packet = MessageQueue.getLoginRequestPacket();
                if (null != packet) {
                    LogUtil.info("处理登录消息:" + JSONObject.toJSONString(packet));
                    channnel.writeAndFlush(packet);
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

    private static void startMessagePacketThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                MessageRequestPacket packet = MessageQueue.getReqMessagePacket();
                if (null != packet) {
                    channel.writeAndFlush(packet);
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





    private static void waitForLoginResponse() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
