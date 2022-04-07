package com.dreamtea.Game.MessagePipeline.MessageHandlers;

import com.dreamtea.Boot.Service.RedisService;
import com.dreamtea.Game.MessagePipeline.MessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.dreamtea.Boot.Configurer.WebConfigurer.REDIS_SPLIT_SYMBOL;

@Component
@Scope("prototype")
public class MessagePlayerNameDecoder extends MessageHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public String handle(String... messages) throws Exception {
        if(messages.length != 1) {
            throw new IllegalArgumentException("wrong message counts");
        }

        String message = messages[0];
        Map<String, Object> decodedResult = objectMapper.readValue(message, Map.class);

        String remoteToken = (String) decodedResult.get("remoteToken");
        String playerName = ((String) redisService.get(remoteToken)).split(REDIS_SPLIT_SYMBOL)[1];

        decodedResult.put("playerName", playerName);

        return nextHandler.handle(decodedResult);
    }
}
