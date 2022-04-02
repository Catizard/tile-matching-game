package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameMessageLogoutHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"LOGOUT"};

    @Autowired
    private RoomService roomservice;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));

        if(isSupported(SUPPORTEDTYPES, type)) {
            Integer roomId = ((Integer) messages.get("roomId"));
            Integer isReady = ((Integer) messages.get("isReady"));

            if(isReady != 0) {
                roomservice.delReadyPlayer(roomId);
            }
        }

        return nextHandler.handle(messages);
    }
}
