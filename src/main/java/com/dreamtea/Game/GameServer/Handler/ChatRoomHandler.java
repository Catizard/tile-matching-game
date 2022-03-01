package com.dreamtea.Game.GameServer.Handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

@ChannelHandler.Sharable
public class ChatRoomHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String comment = frame.text();
        Channel curChannel = ctx.channel();
        for (Channel channel : channels) {
            if (channel != curChannel) {
                channel.writeAndFlush(new TextWebSocketFrame(curChannel.remoteAddress() + ":" + comment));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame(comment));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(ctx.channel().remoteAddress() + " in"));
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        for(Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(ctx.channel().remoteAddress() + " out"));
        }
    }
}
