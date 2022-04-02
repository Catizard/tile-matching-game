package com.dreamtea.Game.MessagePipeline.MessageHandlers.Game;

import com.dreamtea.Game.MessagePipeline.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameMessageWrapHandler extends MessageHandler {
    private static final String[] SUPPORTEDTYPES = {"MESSAGE"};

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String handle(Map<String, Object> messages) throws Exception {
        String type = ((String) messages.get("type"));

        if(isSupported(SUPPORTEDTYPES, type)) {
            String o_message = ((String) messages.get("message"));
            Map<String, String> map = new HashMap<>();

            //write key type
            map.put("type", "MESSAGE");
            map.put("message", o_message);

            //change it to json type
            messages.put("message", objectMapper.writeValueAsString(map));
        }

        return nextHandler.handle(messages);
    }
}
