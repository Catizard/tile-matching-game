package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameMessageDecoder extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"LOGIN", "LOGOUT", "MESSAGE"};

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));

        if(isSupported(SUPPORTEDTYPES, type)) {
            String playerName = ((String) messages.get("playerName"));
            messages.put("type", "MESSAGE");
            if("LOGIN".equals(type)) {
                messages.put("message", playerName + " in");
            } else if("LOGOUT".equals(type)) {
                messages.put("message", playerName + " out");
            } else {
                String message = ((String) messages.get("message"));
                messages.put("message", playerName + ": " + message);
            }
        }

        return nextHandler.handle(messages);
    }
}
