package com.study.project.im.server;

import com.study.project.im.common.LogUtil;
import com.study.project.im.common.auth.AuthHandler;
import com.study.project.im.common.handler.Spliter;
import com.study.project.im.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ServerApp {

    private static final int port = 8080;

    public static void main(String[] args) {
        startServer();
    }


    private static void startServer() {
        final ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        bootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        // 空闲检测
                        ch.pipeline().addLast(new IMIdlesStateHandler());
                        // 拆包粘包处理
                        ch.pipeline().addLast(new Spliter());
                        // 编解码处理
                        ch.pipeline().addLast(PacketCodecHandler.instance);
                        // 登录处理
                        ch.pipeline().addLast(LoginRequestHandler.instance);
                        // 心跳处理
                        ch.pipeline().addLast(HearBeatRequestHandler.instance);
                        // 鉴权处理
                        ch.pipeline().addLast(AuthHandler.instance);
                        // 业务处理
                        ch.pipeline().addLast(IMHandler.instance);
                    }
                });
        bind(bootstrap, port);
    }


    private static void bind(ServerBootstrap bootstrap, int port) {
        bootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LogUtil.info("server bind port success, port:{}", port);
                } else {
                    LogUtil.info("server bind port fail, port:{}", port);
                    bind(bootstrap, port + 1);
                }
            }
        });
    }
}
