package com.dreamtea.Game.Configurer;

import com.dreamtea.Game.MessagePipeline.MessageHandlers.Ground.GroundMessageLogHandler;
import com.dreamtea.Game.MessagePipeline.MessageHandlers.Ground.GroundMessageRefreshHandler;
import com.dreamtea.Game.MessagePipeline.MessageHandlers.Ground.GroundMessageRoomStatusHandler;
import com.dreamtea.Game.MessagePipeline.MessageHandlers.MessagePlayerNameDecoder;
import com.dreamtea.Game.MessagePipeline.MessagePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroundPipelineConfigurer {
    @Autowired
    private MessagePlayerNameDecoder messagePlayerNameDecoder;

    @Autowired
    private GroundMessageLogHandler groundMessageLogHandler;

    @Autowired
    private GroundMessageRoomStatusHandler groundMessageRoomStatusHandler;

    @Autowired
    private GroundMessageRefreshHandler groundMessageRefreshHandler;

    @Bean("GroundPipeline")
    public MessagePipeline messagePipeline() {
        return new MessagePipeline("ground pipeline")
                .addFirst(messagePlayerNameDecoder)
                .addLast(groundMessageLogHandler)
                .addLast(groundMessageRoomStatusHandler)
                .setEnd(groundMessageRefreshHandler);
    }

    /*
    GroundPipeline
        MessagePlayerNameDecoder -> decode the player name
        GroundMessageLogHandler -> handle the player log message
                                   change the room's status
        GroundMessageRoomStatusHandler -> handle the room status change message
                                          change the room's status
        GroundMessageRefreshHandler -> return a "refresh" string
                                       as an end of the chain
     */
}

