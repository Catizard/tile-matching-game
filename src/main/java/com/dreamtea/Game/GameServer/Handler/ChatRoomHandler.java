package com.dreamtea.Game.GameServer.Handler;

import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Game.GroundServer.Service.GameService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@ChannelHandler.Sharable
public class ChatRoomHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private GameService gameService;

    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        String json = frame.text();
        System.out.println(json);
        JsonNode jsonNode = objectMapper.readTree(json);

        String remoteToken = jsonNode.get("remoteToken").asText();
        String type = jsonNode.get("type").asText();
        String message = jsonNode.get("message").asText();

        if(remoteToken == null || type == null || message == null) {
            throw new IllegalArgumentException("message is not exist");
        }

        String name = ((String) redisService.get(remoteToken)).split("-")[1];
        Map<String, String> mapMessage = new HashMap<>();

        //In default, type is MESSAGE
        mapMessage.put("type","MESSAGE");

        switch (type) {
            case "MESSAGE":
                mapMessage.put("message", name + ":" + message);
                break;
            case "LOGIN":
                mapMessage.put("message", name + " in");
                break;
            case "LOGOUT":
                mapMessage.put("message", name + " out");
                int roomId = Integer.parseInt(jsonNode.get("message").asText());
                int isReady = Integer.parseInt(jsonNode.get("isReady").asText());
                if(isReady != 0) {
                    roomService.delReadyPlayer(roomId);
                }
                break;
            case "READY":
                mapMessage.put("type", "MAP");
                //TODO 加入地图功能
                ArrayList<Integer> initialMap = gameService.genMap("test.txt");
                String keyToken = "map-" + remoteToken;
                redisService.set(keyToken, initialMap);
                mapMessage.put("message", objectMapper.writeValueAsString(initialMap));
                break;
            case "GAMEOVER":
                roomId = Integer.parseInt(jsonNode.get("message").asText());
                --roomId;
                long timestamp = Long.parseLong(jsonNode.get("timestamp").asText());
                roomService.delReadyAll(roomId);
                mapMessage.put("type","GAMEOVER");
                mapMessage.put("message",((String) redisService.get(remoteToken)).split("-")[1]);
                break;
        }

        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(mapMessage)));
        }
    }

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
