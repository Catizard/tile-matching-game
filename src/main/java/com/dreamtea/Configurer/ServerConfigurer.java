package com.dreamtea.Configurer;

import com.dreamtea.Game.Server.Handler.ChatRoomHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;

@Configuration
public class ServerConfigurer {

    @Bean
    @Scope("prototype")
    public ChatRoomHandler chatRoomHandlerFactory() {
        return new ChatRoomHandler();
    }

    @Bean
    public ArrayList<ChatRoomHandler> chatRoomHandlerListFactory() {
        ArrayList<ChatRoomHandler> chatRoomHandlerList = new ArrayList<>();
        for(int i = 0;i < 10;++i) {
            chatRoomHandlerList.add(chatRoomHandlerFactory());
        }
        return chatRoomHandlerList;
    }

    @Bean(value = "bossGroup", destroyMethod = "shutdownGracefully")
    @Scope("prototype")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }

    @Bean(value = "workerGroup", destroyMethod = "shutdownGracefully")
    @Scope("prototype")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup();
    }

    @Bean("serverBootStrap")
    @Scope("prototype")
    public ServerBootstrap serverBootstrap(ChatRoomHandler chatRoomHandler) {
        return new ServerBootstrap()
                .group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pip = ch.pipeline();
                        pip.addLast(new HttpServerCodec());

                        pip.addLast(new ChunkedWriteHandler());
                        pip.addLast(new HttpObjectAggregator(1048576));
                        pip.addLast(new WebSocketServerProtocolHandler("/websocket"));
                        pip.addLast(chatRoomHandler);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @Bean("serverBootstrapList")
    public ArrayList<ServerBootstrap> serverBootstrapList() {
        ArrayList<ServerBootstrap> serverBootstrapList = new ArrayList<>();
        ArrayList<ChatRoomHandler> chatRoomHandlerList = chatRoomHandlerListFactory();
        for(int i = 0;i < 10;++i) {
            serverBootstrapList.add(serverBootstrap(chatRoomHandlerList.get(i)));
        }
        return serverBootstrapList;
    }
}

