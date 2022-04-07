package com.dreamtea.Game.Configurer;

import com.dreamtea.Game.MessagePipeline.MessageHandlers.Game.*;
import com.dreamtea.Game.MessagePipeline.MessageHandlers.MessagePlayerNameDecoder;
import com.dreamtea.Game.MessagePipeline.MessagePipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GamePipelineConfigurer {

    @Autowired
    private MessagePlayerNameDecoder messagePlayerNameDecoder;

    @Autowired
    private GameMessageLoginHandler gameMessageLoginHandler;

    @Autowired
    private GameMessageLogoutHandler gameMessageLogoutHandler;

    @Autowired
    private GameMessageReadyHandler gameMessageReadyHandler;

    @Autowired
    private GameMessageDecoder gameMessageDecoder;

    @Autowired
    private GameMessageWrapHandler gameMessageWrapHandler;

    @Autowired
    private GameMessageGameOverHandler gameMessageGameOverHandler;

    @Autowired
    private GameMessageEndHandler gameMessageEndHandler;

    @Bean("GamePipeline")
    public MessagePipeline roomPipeline() {
        return new MessagePipeline("game pipeline")
                .addFirst(messagePlayerNameDecoder)
                .addLast(gameMessageReadyHandler)
                .addLast(gameMessageLoginHandler)
                .addLast(gameMessageLogoutHandler)
                .addLast(gameMessageDecoder)
                .addLast(gameMessageWrapHandler)
                .addLast(gameMessageGameOverHandler)
                .setEnd(gameMessageEndHandler);
    }
    /*
    GamePipeline:
        MessagePlayerNameDecoder -> decode the player name
        GameMessageReadyHandler -> handle the ready message
                                   write the info into the message key
                                   and terminate the message handle chain
        GameMessageLoginHandler -> handle the login message
                                   add player into the list
        GameMessageLogoutHandler -> handle logout message
                                    change the room's status
                                    and cast the message type to GAMEOVER when needed
                                    so must be forward as GameMessageGameOverHandler
        GameMessageDecoder -> cast login/logout/message type message to message type
                              and modify the key message's value
        GameMessageWrapHandler -> handle the MESSAGE type message
                                  wrap it to json type

        GameMessageGameOverHandler -> handle the game over message
                                      write the info into the message key
        GameMessageEndHandler -> return the message
                                 as the end of the chain
     */
}
