package com.dreamtea.Game.MessagePipeline.MessageHandlers.Ground;

import com.dreamtea.Game.GroundServer.Service.RoomService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GroundMessageLogHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"LOGIN", "LOGOUT"};

    @Autowired
    private RoomService roomService;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = (String) messages.get("type");

        String remoteToken = ((String) messages.get("remoteToken"));
        Integer roomId = (Integer) messages.get("roomId");

        if(isSupported(SUPPORTEDTYPES, type)) {
            if("LOGIN".equals(type)) {
//                roomService.addMemberInRoom(remoteToken, roomId);
            } else if("LOGOUT".equals(type)) {
//                roomService.delMemberInRoom(remoteToken, roomId);
            }
        }

        return nextHandler.handle(messages);
    }
}
