package com.dreamtea.Game.GameServer.Handler;

import com.dreamtea.Boot.Service.RedisService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Component
@ChannelHandler.Sharable
public class ChatRoomHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String json = frame.text();
        Channel curChannel = ctx.channel();
        JsonNode jsonNode = objectMapper.readTree(json);

        String remoteToken = jsonNode.get("remoteToken").asText();
        String type = jsonNode.get("type").asText();
        String comment = jsonNode.get("message").asText();

        if(remoteToken == null || type == null || comment == null) {
            throw new IllegalArgumentException("message is not exist");
        }

        String name = ((String) redisService.get(remoteToken)).split("-")[1];

        if("MESSAGE".equals(type)) {
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
        } else if("LOGIN".equals(type)) {
            for (Channel channel : channels) {
                channel.writeAndFlush(new TextWebSocketFrame(name + " in"));
            }
        } else if("LOGOUT".equals(type)) {
            for (Channel channel : channels) {
                channel.writeAndFlush(new TextWebSocketFrame(name + " out"));
            }
        }
    }

    //TODO type 信息放回到 read0 中去分发了
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        String name = extractName(ctx);
//        for (Channel channel : channels) {
//            channel.writeAndFlush(new TextWebSocketFrame(name + " in"));
//        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        String name = extractName(ctx);
        channels.remove(ctx.channel());
//        for(Channel channel : channels) {
//            channel.writeAndFlush(new TextWebSocketFrame(name + " out"));
//        }
    }
}
