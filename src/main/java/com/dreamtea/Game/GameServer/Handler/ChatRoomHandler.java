package com.dreamtea.Game.GameServer.Handler;

import com.dreamtea.Boot.Service.RedisService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@ChannelHandler.Sharable
public class ChatRoomHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private RedisService redisService;

    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private String extractName(ChannelHandlerContext ctx) {
        String addr = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString();
        return ((String) redisService.get("addr-" + addr)).split("-")[1];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String comment = frame.text();
        Channel curChannel = ctx.channel();
        String name = extractName(ctx);

        for (Channel channel : channels) {
            if (channel != curChannel) {
//                InetSocketAddress addr = (InetSocketAddress) curChannel.remoteAddress();
//                System.out.println("-----");
//                System.out.println(addr.getAddress());
//                System.out.println(addr.getHostName());
//                System.out.println(addr.getHostString());
//                System.out.println(addr.getPort());
//                System.out.println("-----");
                channel.writeAndFlush(new TextWebSocketFrame(name + ":" + comment));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame(comment));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String name = extractName(ctx);
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(name + " in"));
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String name = extractName(ctx);
        channels.remove(ctx.channel());
        for(Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(name + " out"));
        }
    }
}
