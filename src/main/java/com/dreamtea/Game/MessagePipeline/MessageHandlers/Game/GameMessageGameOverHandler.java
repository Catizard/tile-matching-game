package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameMessageGameOverHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"GAMEOVER"};

    @Autowired
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));

        if(isSupported(SUPPORTEDTYPES, type)) {
            int roomId = ((int) messages.get("roomId"));
            long timestamp = ((long) messages.get("timestamp"));
            String playerName = ((String) messages.get("playerName"));

            roomService.delReadyAll(roomId);
            Map<String, String> map = new HashMap<>();
            map.put("type", "GAMEOVER");
            map.put("message", playerName);

            return objectMapper.writeValueAsString(map);
        }

        return nextHandler.handle(messages);
    }
}
