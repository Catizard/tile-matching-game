package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameMessageEndHandler extends MessageHandler {
    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        return ((String) messages.get("message"));
    }
}
