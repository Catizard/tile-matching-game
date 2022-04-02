package com.dreamtea.Game.GameServer;

import com.dreamtea.Game.Configurer.GameConfig;
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

    private static final int port = 8180;

    public void start() throws InterruptedException {
        for (int i = 0; i < GameConfig.CHOOSEABLE_ROOM_COUNT; ++i) {
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

