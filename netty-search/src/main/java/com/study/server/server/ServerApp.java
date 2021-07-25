package com.study.server.server;

import com.study.server.common.*;
import com.study.server.common.auth.AuthHandler;
import com.study.server.server.handler.*;
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
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.instance);
                        ch.pipeline().addLast(LoginRequestHandler.instance);
                        ch.pipeline().addLast(AuthHandler.instance);
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
                    Logs.error("端口[" + port + "]绑定成功!");
                } else {
                    Logs.error("端口[" + port + "]绑定失败!");
                    bind(bootstrap, port + 1);
                }
            }
        });
    }
}
