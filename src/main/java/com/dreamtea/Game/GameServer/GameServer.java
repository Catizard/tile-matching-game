package com.dreamtea.Game.GameServer;

import io.netty.bootstrap.ServerBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("gameServer")
public class GameServer {

    @Autowired
    @Qualifier("serverBootstrapList")
    private ArrayList<ServerBootstrap> serverBootstrapList;

    //TODO 此处的端口是写死的
    private static final int port = 8180;

    public void start() throws InterruptedException {
        for (int i = 0;i < 10;++i) {
            serverBootstrapList.get(i).bind(port + i);
        }
    }

    //TODO 我不知道如何正常的释放channel 也许不释放也没问题？
//    @PreDestroy
//    public void stop() {
//        for(Channel channel : roomChannels) {
//            channel.close();
//            channel.parent().close();
//        }
//    }
}

