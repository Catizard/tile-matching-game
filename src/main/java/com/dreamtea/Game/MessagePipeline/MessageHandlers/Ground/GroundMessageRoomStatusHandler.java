package com.dreamtea.Game.MessagePipeline.MessageHandlers.Ground;

import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GroundMessageRoomStatusHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"READY", "GAMEOVER"};

    @Autowired
    private RoomService roomService;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = (String) messages.get("type");

        //TODO delete it temp
//        if(isSupported(SUPPORTEDTYPES, type)) {
//            Integer roomId = (Integer) messages.get("roomId");
//            if("READY".equals(type)) {
//                roomService.setRunning(roomId);
//            } else if("GAMEOVER".equals(type)) {
//                roomService.setOver(roomId);
//            }
//        }
        return nextHandler.handle(messages);
    }
}
