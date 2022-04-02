package com.dreamtea.Game.MessagePipeline.MessageHandlers.Ground;

import com.dreamtea.Game.MessagePipeline.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GroundMessageRefreshHandler extends MessageHandler {

    @Override
    public String handle(String... messages) throws Exception {
        return "refresh";
    }

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        return "refresh";
    }
}
