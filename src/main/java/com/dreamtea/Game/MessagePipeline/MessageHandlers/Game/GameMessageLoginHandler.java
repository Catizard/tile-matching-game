package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_SPLIT_SYMBOL;

@Component
public class GameMessageLoginHandler extends MessageHandler {
    private final String[] SUPPORTEDTYPES = {"LOGIN"};

    @Autowired
    private RoomService roomService;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));
        if(isSupported(SUPPORTEDTYPES, type)) {
            String remoteToken = ((String) messages.get("remoteToken")).split(REDIS_SPLIT_SYMBOL)[1];
            int roomId = ((int) messages.get("roomId"));
            roomService.addMemberInRoom(remoteToken, roomId);
        }

        return nextHandler.handle(messages);
    }
}
