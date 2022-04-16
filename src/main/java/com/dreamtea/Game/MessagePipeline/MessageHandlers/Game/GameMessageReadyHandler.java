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

import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_MAP_PREFIX;
import static com.dreamtea.Game.Configurer.GameConfig.*;

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
            int mapId = (int) (Math.random() * CHOOSEABLE_MAP_COUNT);
            String loadMapName = FILE_GAMEMAP_PREFIX + mapId + FILE_GAMEMAP_SUFFIX;
            ArrayList<Integer> initialMap = gameService.genMap(loadMapName);

            ArrayList<String> tokens = roomService.getRoomMemberTokenListInRoom(roomId);
            for (String token : tokens) {
                String keyToken = REDIS_MAP_PREFIX + token;
                redisService.set(keyToken, initialMap);
            }

            Map<String, String> map = new HashMap<>();
            map.put("type", "MAP");
            map.put("message", objectMapper.writeValueAsString(initialMap));

            roomService.setRunning(roomId);

            return objectMapper.writeValueAsString(map);
        }

        return nextHandler.handle(messages);
    }
}
