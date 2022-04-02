package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Game.GroundServer.Service.GameService;
import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class GameMessageReadyHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"READY"};

    @Autowired
    private GameService gameService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));
        if(isSupported(SUPPORTEDTYPES, type)) {
            int roomId = ((int) messages.get("roomId"));
            //TODO 加入随机选图系统
            ArrayList<Integer> initialMap = gameService.genMap("test.txt");

            ArrayList<String> tokens = roomService.getRoomList().get(roomId);
            for(String token : tokens) {
                String keyToken = "map-" + token;
                redisService.set(keyToken, initialMap);
            }

            Map<String, String> map = new HashMap<>();
            map.put("type", "MAP");
            map.put("message", objectMapper.writeValueAsString(initialMap));

            return objectMapper.writeValueAsString(map);
        }

        return nextHandler.handle(messages);
    }
}
