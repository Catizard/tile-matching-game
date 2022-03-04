package com.dreamtea.Game.Utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChannelUtil {

    private ChannelUtil() {

    }

    public static void podCast(String message) {
        ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(message));
        }
    }
}
