package com.dreamtea.Game.GroundServer.Handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class GroundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        System.out.println(frame.text());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        
    }
}
