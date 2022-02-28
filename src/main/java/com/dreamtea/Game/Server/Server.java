package com.dreamtea.Game.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component("nettyServer")
public class Server {

    @Autowired
    @Qualifier("serverBootStrap")
    ServerBootstrap serverBootstrap;

    private Channel channel;
    private static final int port = 8181;

    public void start() throws InterruptedException {
        channel = serverBootstrap
                .bind(port)
                .sync()
                .channel()
                .closeFuture()
                .sync()
                .channel();
    }

    @PreDestroy
    public void stop() {
        channel.close();
        channel.parent().close();
    }
}
