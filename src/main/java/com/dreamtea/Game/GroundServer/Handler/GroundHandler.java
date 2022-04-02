package com.dreamtea.Game.GroundServer.Handler;

import com.dreamtea.Game.MessagePipeline.MessagePipeline;
import com.dreamtea.Game.Utils.AdaptableMessageFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class GroundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Resource(name = "GroundPipeline")
    private MessagePipeline messagePipeline;

    @Autowired
    private AdaptableMessageFactory messageFactory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String json = frame.text();
        System.out.println(json);
        String result = messagePipeline.readMessage(json);
        //send a refresh message to everyone at ground page
        broadcast(result);
    }

    public void broadcast(String message) {
        channels.forEach(channel -> channel.writeAndFlush(messageFactory.castAdaptMessage(message)));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
    }
}
