package com.dreamtea.Game.GroundServer;

import io.netty.bootstrap.ServerBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("groundServer")
public class GroundServer {
    @Autowired
    @Qualifier("groundServerBootstrap")
    private ServerBootstrap b;
    //TODO 此处的 port 是写死的
    private final int port = 8190;

    public void start() throws InterruptedException {
        b.bind(port).sync();
    }
}
