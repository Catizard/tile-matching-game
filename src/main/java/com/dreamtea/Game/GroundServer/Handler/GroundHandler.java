package com.dreamtea.Game.GroundServer.Handler;

import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Game.GroundServer.Service.RoomService;
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
public class GroundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String json = frame.text();
        System.out.println(json);
        if(json == null) {
            throw new IllegalArgumentException("message is not exist");
        }

        JsonNode jsonNode = objectMapper.readTree(json);
        String remoteToken = jsonNode.get("remoteToken").asText();
        String type = jsonNode.get("type").asText();
        String roomId = jsonNode.get("roomId").asText();

        if(remoteToken == null || type == null || roomId == null) {
            throw new IllegalArgumentException("message is not exist");
        }

        int numRoomId = Integer.parseInt(roomId);

        //TODO api 对于 roomId 的定义需要统一
        --numRoomId;

        //TODO 此处需要验证 remoteToken 的合法性
        String name = ((String) redisService.get(remoteToken)).split("-")[1];
        if("LOGIN".equals(type)) {
            roomService.add(name, numRoomId);
        } else {
            roomService.del(name, numRoomId);
        }

        for(Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("refresh"));
        }
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
